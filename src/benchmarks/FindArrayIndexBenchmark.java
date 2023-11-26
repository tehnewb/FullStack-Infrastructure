package benchmarks;

import com.artemis.utils.BitVector;
import infrastructure.collections.Bits;
import infrastructure.collections.faststack.FastStack;
import infrastructure.collections.index.IndexQueue;
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
public class FindArrayIndexBenchmark {

    private final int SIZE = 1_000_000;
    private final long[] array = new long[SIZE];
    private final Bits bits = new Bits(SIZE);
    private final BitVector bitVector = new BitVector(SIZE);
    private final FastStack<Integer> list = new FastStack<>();
    private IndexQueue queue = new IndexQueue(SIZE);

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(FindArrayIndexBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    @Setup
    public void setup() {
        for (int i = 0; i < SIZE - 1; i++) {
            bits.set(i);
            bitVector.set(i);
        }
    }

    @Benchmark
    public int testVector(Blackhole blackhole) {
        return bitVector.nextClearBit(0);
    }

    @Benchmark
    public int testBits(Blackhole blackhole) {
        return bits.nextClearBit(0);
    }


}
