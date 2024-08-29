package com.rsl.event.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Entity
public class AllVendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vendor name is mandatory")
    @Size(max = 100, message = "Vendor name should not exceed 100 characters")
    private String name;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Phone number must be between 10 and 15 digits")
    private String phone;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 255, message = "Address should not exceed 255 characters")
    private String address;

    // Getters and setters
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
}
