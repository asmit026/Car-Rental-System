package com.carrental.service;

import com.carrental.model.Booking;

import java.util.concurrent.BlockingQueue;

public class BookingProcessor implements Runnable {

    private final BlockingQueue<Booking> queue;
    private final CarRentalService service;

    public BookingProcessor(BlockingQueue<Booking> queue, CarRentalService service) {
        this.queue = queue;
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Booking booking = queue.take();
                service.processBooking(booking);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
