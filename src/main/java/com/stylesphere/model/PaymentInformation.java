package com.stylesphere.model;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class PaymentInformation {
	@Column(name="cardholder_name")
	private String cardHolderName;
	@Column(name="card_Number")
	private String cardNumber;
	@Column(name="expiration_Date")
	private LocalDate expirationDate;
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	@Column(name="cvv")
	private String cvv;
	public PaymentInformation(String cardHolderName, String cardNumber, LocalDate expirationDate, String cvv) {
		super();
		this.cardHolderName = cardHolderName;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		this.cvv = cvv;
	}
}
