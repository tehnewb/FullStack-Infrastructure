package infrastructure.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
@Measurement(iterations = 5, timeUnit = TimeUnit.NANOSECONDS, time = 5000)
public class MyBenchmarks {

    private static final long[] array = new long[10000];
    private static final BitSet bits = new BitSet(array.length);
    private static final LinkedList<Integer> list = new LinkedList<>();


    public static void main(String[] args) throws Exception {
        for (int i = 0; i < array.length / 2; i++) {
            array[i] = -1;
            bits.set(i);
            list.add(array.length / 2 + i);
        }

        Options options = new OptionsBuilder()
                .include(MyBenchmarks.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    @Warmup
    @Benchmark
    public int testBitset() {
        return bits.nextClearBit(0);
    }

    @Warmup
    @Benchmark
    public int testLoop() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != -1) {
                return i;
            }
        }
        return -1;
    }

    @Warmup
    @Benchmark
    public int testList() {
        return !list.isEmpty() ? list.pop() : -1;
    }

}
