package benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(value = 5)
@Warmup(iterations = 10, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
@Measurement(iterations = 10, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
public class ArrayResizeBenchmark {

    private final int SIZE = 10000;
    private final int RESIZE = 20000;
    private int[] arr;

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(ArrayResizeBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

    @Setup(Level.Iteration)
    public void setup() {
        arr = new int[SIZE];
    }

    @Benchmark
    public int[] createNewArray() {
        int[] newArr = new int[RESIZE];
        System.arraycopy(arr, 0, newArr, 0, SIZE);
        arr = newArr;
        return arr;
    }

    @Benchmark
    public int[] copyOf() {
        return arr = Arrays.copyOf(arr, RESIZE);
    }

    @Benchmark
    public int[] resizeCurrentArray() {
        int[] newArr = new int[RESIZE];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        arr = newArr;
        return arr;
    }
}
