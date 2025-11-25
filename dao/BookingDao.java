package com.carrental.dao;

import com.carrental.model.Booking;
import java.util.List;

public interface BookingDao extends CrudRepository<Booking, Integer> {
    List<Booking> findByCustomerId(int customerId);
}
