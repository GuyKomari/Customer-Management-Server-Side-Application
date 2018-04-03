package com.CustomerManagement.ServerApp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.CustomerManagement.ServerApp.Customer.Customer;
import com.CustomerManagement.ServerApp.Customer.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class CustomerManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(CustomerManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementApplication.class, args);
	}

	@Bean 	
	CommandLineRunner init(CustomerRepository customerRepository) {
		return (args) ->{
			customerRepository.save(new Customer("Rothschild 20 Tel Aviv","Alon","052-9331122"));
			customerRepository.save(new Customer("Avshalom Gissin 15 Petah Tikva","David","050-6453322"));
			customerRepository.save(new Customer("Derech Hameshi 3 Ganei Tikva","Revital","054-5518866"));
			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : customerRepository.findAll()) {
				log.info(customer.toString()+'\n');
			}
			log.info("");

			// fetch an individual customer by ID
			customerRepository.findById(1L)
			.ifPresent(customer -> {
				log.info("Customer found with findById(1L):");
				log.info("--------------------------------");
				log.info(customer.toString());
				log.info("");
			});
		};
	}
}
