package benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
@Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
public class ArrayResizeBenchmark {

    private final int SIZE = 1_000_000;
    private int[] arr;

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(ArrayResizeBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

    @Setup
    public void setup() {
        arr = new int[SIZE];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i;
    }

    @Benchmark
    public void arrayCopy(Blackhole blackhole) {
        int[] newArr = new int[SIZE];
        System.arraycopy(arr, 0, newArr, 0, SIZE);
        blackhole.consume(newArr);
    }

    @Benchmark
    public void createNew(Blackhole blackhole) {
        int[] newArr = new int[SIZE];
        for (int i = 0; i < arr.length; i++)
            newArr[i] = arr[i];
        blackhole.consume(newArr);
    }
}
