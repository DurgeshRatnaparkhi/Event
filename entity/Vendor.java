package com.rsl.event.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "vendor")
public class Vendor {

    // Primary key ID for vendor
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vnd_id")
    private int id;
    
    // Name of the vendor
    @NotBlank(message = "Vendor name is required")
    @Column(name = "vnd_name")
    private String vendorName;
    
    // Contact number of the vendor
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(name = "vnd_contactNo")
    private String phoneNumber;

    // Address of the vendor
    @NotBlank(message = "Address is required")
    @Column(name = "vnd_address")
    private String address;

    // GST number of the vendor
    @NotBlank(message = "GST number is required")
    @Pattern(regexp = "^\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}$", message = "Invalid GST number format")
    @Column(name = "vnd_gstn")
    private String gstNumber;
    
    // List of items associated with the vendor
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> item;

    // Getters and setters for Vendor properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
