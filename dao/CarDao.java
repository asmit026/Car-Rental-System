package com.carrental.dao;

import com.carrental.model.Car;
import java.util.List;

public interface CarDao extends CrudRepository<Car, Integer> {
    List<Car> findAvailableCars();
}
