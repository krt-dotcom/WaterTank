package water_Tank;

abstract class WaterTank {
    protected float waterAmount;
    protected final float maxCapacity;

    public WaterTank(float maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.waterAmount = 0;
    }

    // Single method for adding/removing
    public void updateLevel(float liters) {
        waterAmount += liters;
        if (waterAmount < 0) waterAmount = 0;
        if (waterAmount > maxCapacity) waterAmount = maxCapacity;
    }

    // Extra helper method
    public float getAvailableSpace() {
        return maxCapacity - waterAmount;
    }

    public float getWaterAmount() {
        return waterAmount;
    }

    public float getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isFull() {
        return waterAmount >= maxCapacity;
    }

    public boolean isEmpty() {
        return waterAmount <= 0;
    }

    public String Status() {
        if (isFull()) return "Full Tank";
        if (isEmpty()) return "Empty Tank";
        return "Partially Filled (" + waterAmount + " L inside)";
    }
}
