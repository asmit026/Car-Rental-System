package model;

/**
 * Car class extends Vehicle and represents actual cars available for booking.
 * It provides its own implementation of calculateRental() (polymorphism).
 */
public class Car extends Vehicle {

    private boolean available;

    public Car(int id, String model, String type, double rentPerDay, boolean available) {
        super(id, model, type, rentPerDay); // Calling parent constructor
        this.available = available;
    }

    /**
     * Polymorphic method — calculates rental amount based on number of days.
     */
    @Override
    public double calculateRental(int days) {
        return days * rentPerDay;
    }

    // Availability getter/setter
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
