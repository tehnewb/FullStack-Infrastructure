package infrastructure.ecsprototype.systems.physics;

public enum MassUnit {
    KILOGRAMS(1.0),
    GRAMS(1000.0),
    POUNDS(2.20462),
    OUNCES(35.274),
    MILLIGRAMS(1e6),
    TONS(0.001),
    STONES(0.157473),
    METRIC_TONS(0.001),
    CARATS(5000.0),
    GRAINS(15432.4),
    TROY_POUNDS(2.67923);

    private final double kilogramsPerUnit;

    MassUnit(double kilogramsPerUnit) {
        this.kilogramsPerUnit = kilogramsPerUnit;
    }

    public double convert(double sourceValue, MassUnit sourceUnit) {
        double sourceKilograms = sourceUnit.convertToKilograms(sourceValue);
        return convertFromKilograms(sourceKilograms);
    }

    public double convertToKilograms(double amount) {
        return amount * kilogramsPerUnit;
    }

    public double convertFromKilograms(double kilograms) {
        return kilograms / kilogramsPerUnit;
    }
}