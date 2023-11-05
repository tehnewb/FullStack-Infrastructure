package benchmarks;

import infrastructure.ecs.ECS;
import infrastructure.ecs.Entity;
import infrastructure.ecs.systems.tick.TickComponent;
import infrastructure.ecs.systems.tick.TickSystem;
import org.openjdk.jmh.annotations.*;
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
public class ECSBenchmark {

    private ECS ecs;


    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(ECSBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

    @Setup
    public void setup() {
        this.ecs = new ECS(new TickSystem());

        for (int i = 0; i < 262144; i++) {
            Entity entity = ecs.create();
            ecs.addComponent(entity.getIndex(), new TickComponent());
        }
    }

    @Benchmark
    public void benchmarkProcesses() {
        ecs.process();
    }

}
