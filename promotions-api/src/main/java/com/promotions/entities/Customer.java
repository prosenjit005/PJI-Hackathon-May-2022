package com.promotions.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="CUSTOMER_DETAILS")
@Data
public class Customer {

	@Id
	private Integer customerId;
	private String name;
	private String contactNumber;
	private String address;
	private String zipcode;
	
}
