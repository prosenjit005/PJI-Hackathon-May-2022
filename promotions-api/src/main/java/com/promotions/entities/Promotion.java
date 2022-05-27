package com.promotions.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="PROMOTIONS_DATA")
@Data
public class Promotion {

	@Id
	private Integer promoId;
	private String promoCode;
	private String startDate;
	private String endDate;
	private String promoMessage;
}
