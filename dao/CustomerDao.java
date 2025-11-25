package com.carrental.dao;

import com.carrental.model.Customer;
import java.util.Optional;

public interface CustomerDao extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
}
