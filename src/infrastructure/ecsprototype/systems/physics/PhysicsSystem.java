package infrastructure.ecsprototype.systems.physics;

import infrastructure.ecsprototype.Component;
import infrastructure.ecsprototype.EntitySystem;

public class PhysicsSystem extends EntitySystem {
    private static final double DefaultGravity = 6.67430e-11; // Gravitational constant
    private final int PhysicsIndex = Component.getIndex(PhysicsComponent.class);
    private double gravity;

    public PhysicsSystem(double gravity) {
        super(PhysicsComponent.class);
        this.gravity = gravity;
    }

    public PhysicsSystem() {
        this(DefaultGravity);
    }

    @Override
    public void process(int entityID) {
        PhysicsComponent component = ecs.getComponent(entityID, PhysicsIndex, PhysicsComponent.class);


    }
}
