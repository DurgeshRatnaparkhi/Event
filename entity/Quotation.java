package com.rsl.event.entity;

import java.util.List;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Quotation")
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quot_id")
    private int id; // Unique identifier for Quotation

    @NotBlank(message = "Customer name is required")
    @Column(name = "quot_name")
    private String customerName; // Name of Customer on the Quotation

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(name = "quot_PhoneNo")
    private String phoneNumber; // Contact number associated with the Quotation

    @NotBlank(message = "Address is required")
    @Column(name = "quot_address")
    private String address; // Address associated with the Quotation

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "quot_mail")
    private String emailId; // Email associated with the Quotation

   
    @Column(name = "quot_gstn")
    private String gstin; // GST number on the Quotation

    @NotBlank(message = "Quotation date is required")
    @Column(name = "quot_Date")
    private String quotationDate; // Date associated with the Quotation

    @NotBlank(message = "Quotation time is required")
    @Column(name = "quot_time")
    private String quotationTime; // Time associated with the Quotation

    @NotBlank(message = "Venue details are required")
    @Column(name = "quot_venue")
    private String venueDetails; // Venue associated with the Quotation

    @NotBlank(message = "Venue date is required")
    @Column(name = "ven_date")
    private String venueDate; // Venue Date

    @NotBlank(message = "Venue time is required")
    @Column(name = "ven_time")
    private String venueTime; // Venue Time

    @OneToMany(cascade = CascadeType.ALL)
    private List< Item> items; // List of items in the Quotation

	// Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getQuotationDate() {
        return quotationDate;
    }

    public void setQuotationDate(String quotationDate) {
        this.quotationDate = quotationDate;
    }

    public String getQuotationTime() {
        return quotationTime;
    }

    public void setQuotationTime(String quotationTime) {
        this.quotationTime = quotationTime;
    }

    public String getVenueDetails() {
        return venueDetails;
    }

    public void setVenueDetails(String venueDetails) {
        this.venueDetails = venueDetails;
    }

    public String getVenueDate() {
        return venueDate;
    }

    public void setVenueDate(String venueDate) {
        this.venueDate = venueDate;
    }

    public String getVenueTime() {
        return venueTime;
    }

    public void setVenueTime(String venueTime) {
        this.venueTime = venueTime;
    }
    

    public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}


    
}
