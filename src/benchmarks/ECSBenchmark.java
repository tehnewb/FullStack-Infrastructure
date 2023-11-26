package benchmarks;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.systems.IteratingSystem;
import infrastructure.collections.TypeArray;
import infrastructure.ecsprototype.Component;
import infrastructure.ecsprototype.ECS;
import infrastructure.ecsprototype.Entity;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(value = 2)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
@Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 1000)
public class ECSBenchmark {

    private ECS ecs;
    private World world;


    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(ECSBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

    @Setup
    public void setup() {
        this.ecs = new ECS(new infrastructure.ecsprototype.EntitySystem(TestComponent.class) {

            TypeArray<TestComponent> typeArr;

            @Override
            public void process(int entityID) {
                TestComponent component = typeArr.get(entityID);
                component.x = 1;
            }
        });
        this.world = new World(new WorldConfiguration().setSystem(new IteratingSystem(Aspect.all()) {

            ComponentMapper<ArtemisComponent> mapper;

            @Override
            protected void process(int entityID) {
                ArtemisComponent component = mapper.get(entityID);
                component.x = 1;
            }
        }));

        for (int i = 0; i < 262144; i++) {
            Entity entity = ecs.create();
            ecs.addComponent(entity.getIndex(), new TestComponent());

            world.createEntity().edit().add(new ArtemisComponent());
        }
    }

    @Benchmark
    public void benchmarkProcesses(Blackhole blackhole) {
        ecs.process();
    }

    @Benchmark
    public void benchmarkArtemisProcesses(Blackhole blackhole) {
        world.process();
    }

    public static class TestComponent extends Component {
        byte x = 0;
    }

    public static class ArtemisComponent extends com.artemis.Component {
        byte x = 0;
    }

}
