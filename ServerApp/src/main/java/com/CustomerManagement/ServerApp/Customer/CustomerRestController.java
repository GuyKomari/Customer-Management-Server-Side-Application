package com.CustomerManagement.ServerApp.Customer;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.CustomerManagement.ServerApp.Exception.*;


/**
 * Description: This class is the customer rest controller - 
 * 				Implements the functionality for customer and transaction entities
 */
@RestController
public class CustomerRestController
{
	@Autowired
	private CustomerRepository CustomerRepository;
	
	@Autowired
	private TransactionRepository TransactionRepository;
	
	
	/**
	 * Retrieve all the customers
	 * @return all the customers
	 */
	@GetMapping("/customers")
	public List<Customer> retrieveAllCustomers() {
		
		return CustomerRepository.findAll();
	}
	/**
	 * Retrieve specific customer details by given id 
	 * @param id - customer id
	 * @return customer details
	 */
	@GetMapping("/customers/{id}")
	public Resource<Customer> retrieveCustomer(@PathVariable long id) 
	{
		Optional<Customer> customer = CustomerRepository.findById(id);

		if (!customer.isPresent())
			throw new CustomerNotFoundException("customer with id - " + id + " not found");

		Resource<Customer> resource = new Resource<Customer>(customer.get());

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllCustomers());

		resource.add(linkTo.withRel("all-Customers"));

		return resource;
	}
	/**
	 * Delete customer 
	 * @param id - customer id 
	 */
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable long id) 
	{
		CustomerRepository.deleteById(id);
	}

	/**
	 * Create new customer
	 * @param customer - customer object
	 * @return customer details
	 */
	@PostMapping("/customers")
	public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer) 
	{
		Customer savedCustomer = CustomerRepository.save(customer);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedCustomer.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/customers/{id}/transactions")
	public List<Transaction> retrieveCustomerTransactions(@PathVariable long id)
	{
		Optional<Customer> optionalCustomer = CustomerRepository.findById(id);
		if (!optionalCustomer.isPresent())
			throw new CustomerNotFoundException("customer with id - " + id + " not found");
		
		return optionalCustomer.get().getTransactions();
		
	}
	
	@PostMapping("/customers/{id}/transactions")
	public ResponseEntity<Object> createTransaction(@PathVariable long id, @RequestBody Transaction transaction)
	{
		Optional<Customer> optionalCustomer = CustomerRepository.findById(id);
		if (!optionalCustomer.isPresent())
			throw new CustomerNotFoundException("customer with id - " + id + " not found");
		
		Customer customer = optionalCustomer.get();
		
		transaction.setCustomer(customer);
		
		TransactionRepository.save(transaction);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(transaction.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/transactions/{id}")
	public void deleteTransaction(@PathVariable long id) 
	{
		TransactionRepository.deleteById(id);
	}
	
}
