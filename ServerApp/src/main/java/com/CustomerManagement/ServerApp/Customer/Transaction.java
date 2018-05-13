package com.CustomerManagement.ServerApp.Customer;

import io.swagger.annotations.ApiModel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.CustomerManagement.ServerApp.Customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@ApiModel(description = "transaction class")
public class Transaction 
{
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	private Customer customer;
	
	private String name;
	
	private Date date;
	
	private Long cost;
	
	private String description;

	/**
	 * Default constructor for JPA
	 */
	protected Transaction() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", name=" + name + ", date=" + date
				+ ", cost=" + cost + ", description=" + description + "]";
	}
}
