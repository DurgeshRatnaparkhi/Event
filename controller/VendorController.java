package com.rsl.event.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rsl.event.entity.Vendor;
import com.rsl.event.service.VendorService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class VendorController {
    
    @Autowired
    private VendorService vendorService;

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);
    
    // Retrieve all vendors
    @GetMapping("/vendor")
    public ResponseEntity<List<Vendor>> getAllVendors() {
        logger.trace("Entering getAllVendors method.");
        try {
            List<Vendor> vendors = vendorService.getAllVendors();
            if (vendors.isEmpty()) {
                logger.info("No vendors found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            logger.info("Vendors retrieved successfully.");
            return ResponseEntity.ok(vendors); // Return 200 with the list of vendors
        } catch (Exception e) {
            logger.error("Error retrieving vendors: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Add a new vendor
    @PostMapping("/vendor")
    public ResponseEntity<Vendor> addVendor(@Valid @RequestBody Vendor vendor) {
        logger.trace("Entering addVendor method with vendor: {}", vendor);
        try {
            Vendor addedVendor = vendorService.addVendor(vendor);
            logger.info("Vendor added successfully with ID: {}", addedVendor.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(addedVendor);
        } catch (Exception e) {
            logger.error("Error adding vendor: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Retrieve a vendor by ID
    @GetMapping("/vendor/{id}")
    public ResponseEntity<Vendor> getVendor(@PathVariable("id") int id) {
        logger.trace("Entering getVendor method with ID: {}", id);
        try {
            Vendor vendor = vendorService.getVendorById(id);
            return ResponseEntity.of(Optional.ofNullable(vendor)); // Simplified null check
        } catch (Exception e) {
            logger.error("Error retrieving vendor with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Delete a vendor by ID
    @DeleteMapping("/vendor/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable("id") int id) {
        logger.trace("Entering deleteVendor method with ID: {}", id);
        try {
            vendorService.deleteVendor(id);
            logger.info("Vendor with ID {} deleted successfully.", id);
            return ResponseEntity.noContent().build(); // Return 204 if deletion is successful
        } catch (Exception e) {
            logger.error("Error deleting vendor with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Update a vendor by ID
    @PutMapping("/vendor/{id}")
    public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor vendor, @PathVariable("id") int id) {
        logger.trace("Entering updateVendor method with ID: {}", id);
        try {
            vendorService.updateVendor(vendor, id);
            logger.info("Vendor with ID {} updated successfully.", id);
            return ResponseEntity.ok(vendor); // Return 200 with the updated vendor
        } catch (Exception e) {
            logger.error("Error updating vendor with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
