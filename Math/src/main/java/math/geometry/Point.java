package math.geometry;

public class Point implements Cloneable {

    private float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point normalize() {
        double magnitude = Math.sqrt(x * x + y * y); // Calculate the magnitude of the vector

        if (magnitude != 0) { // Avoid division by zero
            // Normalize the point to create a unit vector
            double normalizedX = x / magnitude;
            double normalizedY = y / magnitude;

            // Create a new Point with normalized coordinates
            return new Point((int) normalizedX, (int) normalizedY);
        } else { // If the point is already at the origin, return a copy of the input point
            return new Point(x, y);
        }
    }

    public float distance(Point other) {
        // Calculate the Euclidean distance between two points
        float deltaX = this.x - other.x;
        float deltaY = this.y - other.y;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }









































    public Point set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Point setX(float x) {
        this.x = x;
        return this;
    }

    public Point setY(float y) {
        this.y = y;
        return this;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    @Override
    public Point clone() {
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return "Point[x=" + x + ", y=" + y + "]";
    }
}
