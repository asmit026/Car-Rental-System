package main;

import dao.BookingDAO;
import dao.CarDAO;
import dao.CustomerDAO;
import model.Car;
import service.BookingService;

import java.time.LocalDate;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {

        CarDAO carDAO = new CarDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        BookingDAO bookingDAO = new BookingDAO();
        BookingService bookingService = new BookingService(bookingDAO, carDAO, customerDAO);

        // Example: Load all cars in a separate thread (Multithreading)
        Thread loadCarsThread = new Thread(() -> {
            List<Car> cars = carDAO.findAll();
            System.out.println("Available cars:");
            for (Car c : cars) {
                System.out.println(c);
            }
        });

        loadCarsThread.start();

        // Example: Two users trying to book same car → synchronized booking
        Thread user1 = new Thread(() -> {
            bookingService.bookCar(1, 1, LocalDate.now(), LocalDate.now().plusDays(3));
        });

        Thread user2 = new Thread(() -> {
            bookingService.bookCar(2, 1, LocalDate.now(), LocalDate.now().plusDays(2));
        });

        user1.start();
        user2.start();
    }
}
