package com.springboot.fileupload.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice", schema = "public")
public class Invoice {

	@Id
	//@GeneratedValue
	private Long id;
	private String name;
	private Double amount;
	private Long number;
	@Column(name ="received_date")
	private Date receivedDate;
	public Invoice() {
	}
	public Invoice(Long id, String name, Double amount, Long number, Date receivedDate) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.number = number;
		this.receivedDate = receivedDate;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	@Override
	public String toString() {
		return "Invoice [id=" + id + ", name=" + name + ", amount=" + amount + ", number=" + number + ", receivedDate="
				+ receivedDate + "]";
	}
	
	
}
