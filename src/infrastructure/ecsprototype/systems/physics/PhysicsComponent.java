package infrastructure.ecsprototype.systems.physics;

import infrastructure.ecsprototype.Component;

public class PhysicsComponent extends Component {
    MassUnit unit;
    double x;  // X-coordinate
    double y;  // Y-coordinate
    double mass;
    double velocityX;
    double velocityY;
    double forceX;
    double forceY;

    public PhysicsComponent(double x, double y, double mass, MassUnit unit) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.unit = unit;
    }

    public PhysicsComponent(double mass, MassUnit unit) {
        this(0, 0, mass, unit);
    }

    public double getMass() {
        return mass;
    }

}