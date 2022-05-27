package com.promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promotions.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
