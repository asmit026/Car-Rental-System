package com.carrental.service;

import com.carrental.dao.BookingDao;
import com.carrental.dao.CarDao;
import com.carrental.dao.CustomerDao;
import com.carrental.model.Booking;
import com.carrental.model.Car;
import com.carrental.model.Customer;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class CarRentalService {

    private final CarDao carDao;
    private final CustomerDao customerDao;
    private final BookingDao bookingDao;

    public CarRentalService(CarDao carDao, CustomerDao customerDao, BookingDao bookingDao) {
        this.carDao = carDao;
        this.customerDao = customerDao;
        this.bookingDao = bookingDao;
    }

    public List<Car> listAvailableCars() {
        return carDao.findAvailableCars();
    }

    public Customer registerCustomer(String name, String email, String phone) {
        Customer c = new Customer(0, name, email, phone);
        return customerDao.save(c);
    }

    // Synchronized to avoid race conditions on availability
    public synchronized void processBooking(Booking booking) {
        Optional<Car> carOpt = carDao.findById(booking.getCar().getId());
        if (carOpt.isEmpty()) {
            System.out.println("Car not found, booking failed.");
            booking.setStatus("FAILED");
            bookingDao.save(booking);
            return;
        }

        Car car = carOpt.get();
        if (!car.isAvailable()) {
            System.out.println("Car already booked, booking failed.");
            booking.setStatus("FAILED");
            bookingDao.save(booking);
            return;
        }

        long days = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        if (days <= 0) days = 1;
        double total = days * car.getDailyRate();
        booking.setTotalAmount(total);
        booking.setStatus("CONFIRMED");

        car.setAvailable(false);
        carDao.save(car);
        bookingDao.save(booking);

        System.out.println("Booking confirmed: " + booking);
    }
}
