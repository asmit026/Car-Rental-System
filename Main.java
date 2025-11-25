package com.carrental;

import com.carrental.dao.BookingDao;
import com.carrental.dao.CarDao;
import com.carrental.dao.CustomerDao;
import com.carrental.dao.jdbc.BookingDaoJdbc;
import com.carrental.dao.jdbc.CarDaoJdbc;
import com.carrental.dao.jdbc.CustomerDaoJdbc;
import com.carrental.model.Booking;
import com.carrental.model.Car;
import com.carrental.model.Customer;
import com.carrental.service.BookingProcessor;
import com.carrental.service.CarRentalService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        CarDao carDao = new CarDaoJdbc();
        CustomerDao customerDao = new CustomerDaoJdbc();
        BookingDao bookingDao = new BookingDaoJdbc();

        CarRentalService service = new CarRentalService(carDao, customerDao, bookingDao);

        BlockingQueue<Booking> queue = new LinkedBlockingQueue<>();
        Thread bookingThread = new Thread(new BookingProcessor(queue, service));
        bookingThread.start();

        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== CAR RENTAL SYSTEM ===");
            System.out.println("1. List available cars");
            System.out.println("2. Register customer");
            System.out.println("3. Make booking");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    List<Car> cars = service.listAvailableCars();
                    cars.forEach(System.out::println);
                }
                case 2 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    Customer c = service.registerCustomer(name, email, phone);
                    System.out.println("Registered: " + c);
                }
                case 3 -> {
                    System.out.print("Customer ID: ");
                    int custId = sc.nextInt();
                    System.out.print("Car ID: ");
                    int carId = sc.nextInt();
                    System.out.print("Start date (YYYY-MM-DD): ");
                    String start = sc.next();
                    System.out.print("End date (YYYY-MM-DD): ");
                    String end = sc.next();

                    Customer customer = new Customer();
                    customer.setId(custId);

                    Car car = new Car();
                    car.setId(carId);

                    Booking booking = new Booking();
                    booking.setCustomer(customer);
                    booking.setCar(car);
                    booking.setStartDate(LocalDate.parse(start));
                    booking.setEndDate(LocalDate.parse(end));
                    booking.setStatus("PENDING");

                    try {
                        queue.put(booking);
                        System.out.println("Booking request queued. It will be processed shortly.");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                case 0 -> {
                    exit = true;
                    bookingThread.interrupt();
                }
                default -> System.out.println("Invalid choice.");
            }
        }

        sc.close();
        System.out.println("System exited.");
    }
}
