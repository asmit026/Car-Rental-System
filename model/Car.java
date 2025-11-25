package com.carrental.model;

public class Car {
    private int id;
    private String model;
    private String brand;
    private CarType type;
    private double dailyRate;
    private boolean available;

    public Car() {}

    public Car(int id, String model, String brand, CarType type, double dailyRate, boolean available) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.dailyRate = dailyRate;
        this.available = available;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public CarType getType() { return type; }
    public void setType(CarType type) { this.type = type; }

    public double getDailyRate() { return dailyRate; }
    public void setDailyRate(double dailyRate) { this.dailyRate = dailyRate; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", type=" + type +
                ", dailyRate=" + dailyRate +
                ", available=" + available +
                '}';
    }
}
