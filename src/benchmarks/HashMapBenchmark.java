package benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 3, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
@Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
public class HashMapBenchmark {

    private final int SIZE = 1_000_000;
    private HashMap<String, Integer> map1;
    private IdentityHashMap<String, Integer> map2;

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(HashMapBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

    @Setup(Level.Iteration)
    public void setup() {
        this.map1 = new HashMap<>();
        this.map2 = new IdentityHashMap<>();

        for (int i = 0; i < SIZE; i++) {
            map1.put("" + i, i);
            map2.put("" + i, i);
        }
    }

    @Benchmark
    public void testHashMap(Blackhole blackhole) {
        blackhole.consume(map1.get("" + (SIZE - 1)));
    }

    @Benchmark
    public void testIdentityMap(Blackhole blackhole) {
        blackhole.consume(map2.get("" + (SIZE - 1)));
    }

    @Benchmark
    public void testIdentityMapCompute(Blackhole blackhole) {
        blackhole.consume(map2.computeIfAbsent("" + (SIZE - 1), v -> -1));
    }
}
