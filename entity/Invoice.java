package com.rsl.event.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inv_id")
    private int id;  // Unique identifier for Invoice

    @NotBlank(message = "Customer name is required")
    @Column(name = "inv_name")
    private String customer;  // Name on the Invoice

    //@NotBlank(message = "Invoice date is required")
    
    @Column(name = "invoiceNumber")
    private String invoiceNumber;  // Date of the Invoice

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(name = "inv_no")
    private String phoneNumber;  // Contact number associated with the Invoice

    @NotBlank(message = "Address is required")
    @Column(name = "inv_address")
    private String address;  // Address associated with the Invoice

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "inv_mail")
    private String emailId;  // Email associated with the Invoice

   // @Pattern(regexp = "^\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}$", message = "GST number should be valid")
    @Column(name = "inv_gstn")
    private String gstinNumber;  // GST number on the Invoice

    @NotBlank(message = "Invoice time is required")
    @Column(name = "dateTime")
    private String dateTime;  // Time and date associated with the Invoice

    @NotBlank(message = "Venue details are required")
    @Column(name = "venueDetails")
    private String venueDetails;  // Venue associated with the Invoice

    @OneToMany(cascade = CascadeType.ALL)
    private List<@Valid Item> items;  // List of items in the Invoice

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getGstinNumber() {
		return gstinNumber;
	}

	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getVenueDetails() {
		return venueDetails;
	}

	public void setVenueDetails(String venueDetails) {
		this.venueDetails = venueDetails;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

    

}
