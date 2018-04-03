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

import com.CustomerManagement.ServerApp.Exception.CustomerNotFoundException;

/**
 * Description: this class is the customer rest controller
 */
@RestController
public class CustomerRestController
{
	@Autowired
	private CustomerRepository customerRepository;
	
	/**
	 * retrieve all customers
	 * @return all the customers
	 */
	@GetMapping("/customers")
	public List<Customer> retrieveAllCustomers() {
		
		return customerRepository.findAll();
	}
	/**
	 * retrieve customer details by his id 
	 * @param id - customer id
	 * @return customer details
	 */
	@GetMapping("/customers/{id}")
	public Resource<Customer> retrieveUser(@PathVariable long id) 
	{
		Optional<Customer> customer = customerRepository.findById(id);

		if (!customer.isPresent())
			throw new CustomerNotFoundException("customer with id - " + id + " not found");

		Resource<Customer> resource = new Resource<Customer>(customer.get());

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllCustomers());

		resource.add(linkTo.withRel("all-Customers"));

		return resource;
	}
	/**
	 * delete a customer 
	 * @param id - customer id 
	 */
	@DeleteMapping("/customers/{id}")
	public void deleteUser(@PathVariable long id) 
	{
		customerRepository.deleteById(id);
	}

	/**
	 * for create or update customer
	 * @param customer - customer object
	 * @return customer details
	 */
	@PostMapping("/customers")
	public ResponseEntity<Object> createUser(@Valid @RequestBody Customer customer) 
	{
		Customer savedCustomer = customerRepository.save(customer);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedCustomer.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

}
