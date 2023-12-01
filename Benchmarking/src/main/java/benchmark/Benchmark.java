/**
 * The `Benchmark` class is designed to facilitate the execution and comparison of different tasks
 * by measuring their performance metrics, such as throughput or average execution time.
 * <p>
 * It supports multiple tasks, each associated with a unique name, and provides flexibility in
 * configuring warm-up and measurement phases, as well as choosing between throughput and average
 * time measurement modes.
 */
package benchmark;

import utility.Text;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The `Benchmark` class is designed to facilitate the execution and comparison of different tasks
 * by measuring their performance metrics, such as throughput or average execution time.
 * It supports multiple tasks, each associated with a unique name, and provides flexibility in
 * configuring warm-up and measurement phases, as well as choosing between throughput and average
 * time measurement modes.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class Benchmark {
    private static final ExecutorService service = Executors.newSingleThreadExecutor();
    private final HashMap<String, Runnable> tasks;
    private Execution warmups, measurement;
    private BenchmarkMode mode;
    private TimeUnit output;
    private Benchmark() {
        this.mode = BenchmarkMode.Throughput;
        this.warmups = new Execution(TimeUnit.SECONDS, 5, 1);
        this.measurement = new Execution(TimeUnit.SECONDS, 5, 1);
        this.tasks = new HashMap<>();
        this.output = TimeUnit.SECONDS;
    }

    /**
     * Static factory method to create a new instance of the `Benchmark` class.
     *
     * @return A new `Benchmark` instance.
     */
    public static Benchmark of() {
        return new Benchmark();
    }

    // Helper method to convert TimeUnit to its abbreviation
    private static String unitToAbbrev(TimeUnit unit) {
        return switch (unit) {
            case NANOSECONDS -> "ns";
            case MICROSECONDS -> "µs";
            case MILLISECONDS -> "ms";
            case SECONDS -> "s";
            case MINUTES -> "min";
            case HOURS -> "h";
            case DAYS -> "d";
        };
    }

    /**
     * Starts the benchmark by printing configuration details and executing registered tasks.
     */
    public void begin() {
        System.out.println("# Beginning Benchmark");
        System.out.println("# Mode: " + mode.name());
        System.out.println("# Output: " + output.name());
        System.out.println("# Warmup Iterations: " + warmups.iterations);
        System.out.println("# Warmup Durations: " + warmups.duration + unitToAbbrev(warmups.unit));
        System.out.println("# Measurement Iterations: " + measurement.iterations);
        System.out.println("# Measurement Durations: " + measurement.duration + unitToAbbrev(measurement.unit));
        System.out.println();

        tasks.forEach((k, v) -> service.submit(() -> {
            execute(k, v, warmups);
            execute(k, v, measurement);
        }));
    }

    // Private method to execute a task with specified parameters and display results
    private void execute(String name, Runnable task, Execution exe) {
        final TimeUnit unit = exe.unit;
        final int iterations = exe.iterations;
        final long duration = exe.duration;
        final long nanos = unit.toNanos(duration);

        // Title text
        final Text mt = new Text("Benchmark").padToLength(10);
        final Text md = new Text("Mode").padToLength(12);
        final Text cnt = new Text("Count").padToLength(10);
        final Text scr = new Text("Score").padToLength(15);
        final Text err = new Text("Error").padToLength(10);
        final Text unt = new Text("Unit").padToLength(10);

        // Result text
        final Text mta = new Text(name).padToLength(10);
        final Text mda = new Text(mode.name()).padToLength(12);
        final Text cnta = new Text("" + iterations).padToLength(10);
        final Text scra = new Text("");
        final Text erra = new Text("");
        final Text unta = new Text("");

        boolean real = exe == measurement;

        switch (mode) {
            case Throughput -> {
                unta.set("ops/" + unitToAbbrev(output)).padToLength(10);

                long countAvg = 0, min = Integer.MAX_VALUE, max = -1;
                for (int i = 1; i <= iterations; i++) {
                    long count = 0;
                    long start = System.nanoTime();
                    while (System.nanoTime() - start < nanos) {
                        task.run();
                        count++;
                    }
                    min = Math.min(count, min);
                    max = Math.max(count, max);
                    countAvg += count;
                    System.out.printf("%s %s/%s:\t%s %s\n", !real ? "Warmup" : "Measurement", i, iterations, count, unta);
                }
                countAvg /= iterations;
                erra.set("" + (max - min)).padToLength(10);
                scra.set("" + countAvg).padToLength(15);
            }
            case AverageTime -> {
                unta.set(unitToAbbrev(output) + "/ops").padToLength(10);

                long timeTotal = 0, min = Long.MAX_VALUE, max = -1;
                long count = 0;
                for (int i = 1; i <= iterations; i++) {
                    long start = System.nanoTime();
                    while (System.nanoTime() - start < nanos) {
                        long s = System.nanoTime();
                        task.run();
                        long e = System.nanoTime() - s;
                        min = Math.min(e, min);
                        max = Math.max(e, max);
                        timeTotal += e;
                        count++;
                    }
                    double avg = (double) output.convert(timeTotal, TimeUnit.NANOSECONDS) / count;
                    System.out.printf("%s %s/%s:\t%s %s\n", !real ? "Warmup" : "Measurement", i, iterations, String.format("%.3f", avg), unta);
                }
                double err0 = (double) output.convert(max - min, TimeUnit.NANOSECONDS) / count;
                double avg0 = (double) output.convert(timeTotal, TimeUnit.NANOSECONDS) / count;
                erra.set(String.format("%.3f", err0)).padToLength(10);
                scra.set(String.format("%.3f", avg0)).padToLength(15);
            }
        }

        if (real) {
            System.out.println();
            System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n", mt, md, cnt, scr, err, unt);
            System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n", mta, mda, cnta, scra, erra, unta);
            System.out.println();
        }
    }

    /**
     * Configures the time unit for displaying benchmark results.
     *
     * @param unit The desired time unit for output.
     * @return The modified `Benchmark` instance.
     */
    public Benchmark output(TimeUnit unit) {
        this.output = unit;
        return this;
    }

    /**
     * Configures the benchmark mode (Throughput or AverageTime).
     *
     * @param mode The desired benchmark mode.
     * @return The modified `Benchmark` instance.
     */
    public Benchmark mode(BenchmarkMode mode) {
        this.mode = Objects.requireNonNull(mode, "BenchmarkMode cannot be null");
        return this;
    }

    /**
     * Configures the warm-up phase parameters.
     *
     * @param unit       The time unit for warm-up duration.
     * @param iterations The number of warm-up iterations.
     * @param duration   The duration of each warm-up iteration.
     * @return The modified `Benchmark` instance.
     */
    public Benchmark warmup(TimeUnit unit, int iterations, long duration) {
        Objects.requireNonNull(unit, "TimeUnit cannot be null for warmup");
        if (iterations < 1)
            throw new IllegalArgumentException("Warmup iterations must be >= 1");
        if (duration < 1)
            throw new IllegalArgumentException("Warmup duration must be >= 1");
        this.warmups = new Execution(unit, iterations, duration);
        return this;
    }

    /**
     * Configures the measurement phase parameters.
     *
     * @param unit       The time unit for measurement duration.
     * @param iterations The number of measurement iterations.
     * @param duration   The duration of each measurement iteration.
     * @return The modified `Benchmark` instance.
     */
    public Benchmark measurement(TimeUnit unit, int iterations, long duration) {
        Objects.requireNonNull(unit, "TimeUnit cannot be null for measurement");
        if (iterations < 1)
            throw new IllegalArgumentException("Measurement iterations must be >= 1");
        if (duration < 1)
            throw new IllegalArgumentException("Measurement duration must be >= 1");
        this.measurement = new Execution(unit, iterations, duration);
        return this;
    }

    /**
     * Registers a task with a specified name and implementation for benchmarking.
     *
     * @param name The name of the task.
     * @param task The `Runnable` implementation representing the task.
     * @return The modified `Benchmark` instance.
     */
    public Benchmark run(String name, Runnable task) {
        Objects.requireNonNull(name, "Task cannot have null name");
        Objects.requireNonNull(task, "Cannot run a null task");
        tasks.put(name, task);
        return this;
    }

    // Private record for encapsulating execution parameters
    private record Execution(TimeUnit unit, int iterations, long duration) {
    }
}