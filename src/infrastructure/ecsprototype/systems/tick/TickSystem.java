package infrastructure.ecsprototype.systems.tick;

import infrastructure.collections.TypeArray;
import infrastructure.ecsprototype.Component;
import infrastructure.ecsprototype.EntitySystem;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * The `TickSystem` is an entity system within an Entity-Component-System (ECS) architecture
 * that is responsible for processing entities with a `TickComponent`.
 * It facilitates the execution of periodic actions for entities based on specified time intervals.
 *
 * @author Albert Beaupre
 */
public class TickSystem extends EntitySystem {

    private final int TickIndex = Component.getIndex(TickComponent.class);
    TypeArray<TickComponent> asd;

    /**
     * Constructs a new `TickSystem`. This system is designed to process entities that have a `TickComponent`.
     */
    public TickSystem() {
        super(TickComponent.class);
    }

    public static void main(String[] args) {
        TickSystem system = new TickSystem();
        system.asd.set(0, new TickComponent());

        System.out.println(system.asd.get(0));
    }

    /**
     * Process an entity with a `TickComponent`. This method checks if the entity's associated action
     * should be executed based on the configured delay and interval.
     *
     * @param entityID The ID of the entity to process.
     */
    @Override
    public void process(int entityID) {
        TickComponent component = ecs.getComponent(entityID, TickIndex, TickComponent.class);

        // Initialize the 'begin' timestamp if it is not set
        if (component.begin == null)
            component.begin = Instant.now().plusMillis(TimeUnit.MILLISECONDS.convert(component.delay, component.unit));

        // Calculate the time duration since the 'begin' timestamp
        Duration duration = Duration.between(component.begin, Instant.now());

        // Check if the configured interval has passed
        if (component.unit.convert(duration.toMillis(), TimeUnit.MILLISECONDS) >= component.interval) {
            component.begin = Instant.now();

            // Execute the associated action if it exists
            if (component.action != null) {
                component.action.accept(entityID);
            }
            component.occurrences++;
        }
    }
}