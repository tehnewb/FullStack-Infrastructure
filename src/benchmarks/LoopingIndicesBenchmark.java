package benchmarks;

import com.artemis.utils.BitVector;
import infrastructure.collections.BitList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 10/13/2023
 * <p>
 * This class is testing the most efficient way to loop through a set of random indices.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
@Measurement(iterations = 10, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
public class LoopingIndicesBenchmark {

    private static final int SIZE = 10_000_000;
    private final BitList bitList = new BitList(SIZE);
    private final BitVector vector = new BitVector(SIZE);

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(LoopingIndicesBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    @Setup(Level.Iteration)
    public void setup() {
        bitList.set(SIZE - 1);
        vector.set(SIZE - 1);
    }


    @Benchmark
    public void testBitVector(Blackhole blackhole) {
        blackhole.consume(vector.nextSetBit(0));
    }

    @Benchmark
    public void testBitArray(Blackhole blackhole) {
        blackhole.consume(bitList.nextSetBit(0));
    }
}
