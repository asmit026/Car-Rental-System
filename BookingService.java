package service;

import dao.BookingDAO;
import dao.CarDAO;
import dao.CustomerDAO;
import model.*;

import java.time.LocalDate;
import java.util.List;

public class BookingService {

    private final BookingDAO bookingDAO;
    private final CarDAO carDAO;
    private final CustomerDAO customerDAO;

    public BookingService(BookingDAO bookingDAO, CarDAO carDAO, CustomerDAO customerDAO) {
        this.bookingDAO = bookingDAO;
        this.carDAO = carDAO;
        this.customerDAO = customerDAO;
    }

    // synchronized to avoid race conditions on booking the same car
    public synchronized boolean bookCar(int customerId, int carId, LocalDate start, LocalDate end) {

        Car car = carDAO.findById(carId);
        if (car == null || !car.isAvailable()) {
            System.out.println("Car not available.");
            return false;
        }

        long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
        if (days <= 0) {
            System.out.println("Invalid date range.");
            return false;
        }

        double total = car.calculateRental((int) days);

        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return false;
        }

        Booking booking = new Booking(
                0,
                customer,
                car,
                start,
                end,
                total,
                BookingStatus.BOOKED
        );

        boolean saved = bookingDAO.save(booking);
        if (saved) {
            car.setAvailable(false);
            carDAO.update(car);
            System.out.println("Booking successful. Amount: " + total);
        }
        return saved;
    }

    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }
}
