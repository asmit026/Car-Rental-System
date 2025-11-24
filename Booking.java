package model;

import java.time.LocalDate;

public class Booking {

    private int id;
    private Customer customer;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalAmount;
    private BookingStatus status;

    public Booking(int id, Customer customer, Car car,
                   LocalDate startDate, LocalDate endDate,
                   double totalAmount, BookingStatus status) {
        this.id = id;
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // getters & setters
}
