package benchmarks;

import infrastructure.collections.BitList;
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
@Warmup(iterations = 3, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
@Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
public class FindArrayIndexBenchmark {

    private final int SIZE = 1_000_000;
    private final long[] array = new long[SIZE];
    private final BitList bits = new BitList(SIZE);
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
        for (int i = 0; i < array.length; i++) {
            if (Math.random() < .5) {
                array[i] = -1;
                bits.set(i);
                list.push(i);
            }
        }
    }

    @Benchmark
    public void testList(Blackhole blackhole) {
        blackhole.consume(list.pop());
    }

    @Benchmark
    public Object testQueue(Blackhole blackhole) {
        blackhole.consume(queue.pop());
        return queue;
    }


}
