package com.CustomerManagement.ServerApp.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: this class persists data in JPA H2 DB
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
}