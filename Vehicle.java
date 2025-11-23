package model;

/**
 * Vehicle is an abstract base class representing any rentable vehicle.
 * It contains common fields and abstract behaviors for child classes.
 */
public abstract class Vehicle {
    protected int id;
    protected String model;
    protected String type;
    protected double rentPerDay;

    // Constructor to initialize vehicle properties
    public Vehicle(int id, String model, String type, double rentPerDay) {
        this.id = id;
        this.model = model;
        this.type = type;
        this.rentPerDay = rentPerDay;
    }

    /**
     * Abstract method demonstrating polymorphism — each subclass
     * can implement rental calculation differently.
     */
    public abstract double calculateRental(int days);

    // Getters
    public int getId() { return id; }
    public String getModel() { return model; }
    public String getType() { return type; }
    public double getRentPerDay() { return rentPerDay; }

    // Used to display the object information
    @Override
    public String toString() {
        return "[" + id + "] " + model + " (" + type + ") - " + rentPerDay + "/day";
    }
}
