package com.CustomerManagement.ServerApp.Customer;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description: this class represents the Customer object
 */

@Entity
@ApiModel(description = "All the details about a customer")
public class Customer 
{
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=2, message="The name should ccontains at least 2 characters")
	@ApiModelProperty(notes="The name should contains at least 2 characters")
	private String name;
	
	@Size(min=9, message="The phone number should contains at least 9 characters")
	private String phone;
	
	private String address;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;
	
	/**
	 * Default constructor for JPA
	 */
	protected Customer() {

	}
	
	public Customer(String name, String phone, String address) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", phone=" + phone
				+ ", address=" + address + "]";
	}
	
	
}

