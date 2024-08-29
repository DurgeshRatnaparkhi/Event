package com.rsl.event.controller;

import com.rsl.event.entity.AllVendor;
import com.rsl.event.service.AllVendorService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AllVendorController {

    private static final Logger logger = LoggerFactory.getLogger(AllVendorController.class);

    @Autowired
    private AllVendorService allVendorService;

    // Retrieves all vendors.
    @GetMapping("/allvendors")
    public ResponseEntity<Iterable<AllVendor>> getAllVendors() {
        logger.info("Fetching all vendors.");
        Iterable<AllVendor> allVendors = allVendorService.getAllVendors();
        return ResponseEntity.ok(allVendors);
    }

    // Retrieves a vendor by its ID.
    @GetMapping("/allvendors/{id}")
    public ResponseEntity<AllVendor> getVendorById(@PathVariable Long id) {
        logger.info("Fetching vendor with ID: {}", id);
        AllVendor allVendor = allVendorService.getVendorById(id);
        return ResponseEntity.of(Optional.ofNullable(allVendor));
    }

    // Creates a new vendor.
    @PostMapping("/allvendors")
    public ResponseEntity<AllVendor> createVendor(@Valid @RequestBody AllVendor allVendor) {
        logger.info("Creating new vendor with details: {}", allVendor);
        try {
            AllVendor createdVendor = allVendorService.saveVendor(allVendor);
            logger.info("Vendor created successfully with ID: {}", createdVendor.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVendor);
        } catch (Exception e) {
            logger.error("Error creating vendor: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Updates an existing vendor.
    @PutMapping("/allvendors/{id}")
    public ResponseEntity<AllVendor> updateVendor(@PathVariable Long id, @RequestBody AllVendor vendorDetails) {
        logger.info("Updating vendor with ID: {}", id);
        try {
            AllVendor existingVendor = allVendorService.getVendorById(id);
            if (existingVendor != null) {
                existingVendor.setName(vendorDetails.getName());
                existingVendor.setPhone(vendorDetails.getPhone());
                existingVendor.setAddress(vendorDetails.getAddress());
                AllVendor updatedVendor = allVendorService.saveVendor(existingVendor);
                logger.info("Vendor with ID: {} updated successfully.", id);
                return ResponseEntity.ok(updatedVendor);
            } else {
                logger.warn("Vendor with ID: {} not found for update.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error updating vendor with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Deletes a vendor by its ID.
    @DeleteMapping("/allvendors/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        logger.info("Deleting vendor with ID: {}", id);
        try {
            if (allVendorService.getVendorById(id) != null) {
                allVendorService.deleteVendor(id);
                logger.info("Vendor with ID: {} deleted successfully.", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("Vendor with ID: {} not found for deletion.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error deleting vendor with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
