package com.rsl.event.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsl.event.dao.VendorRepository;
import com.rsl.event.entity.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    private static final Logger logger = LoggerFactory.getLogger(VendorService.class);

    // Retrieve all vendors
    public List<Vendor> getAllVendors() {
        logger.trace("Entering getAllVendors method.");
        return (List<Vendor>) this.vendorRepository.findAll();
    }

    // Retrieve a vendor by ID
    public Vendor getVendorById(int id) {
        logger.trace("Entering getVendorById method with ID: {}", id);
        Vendor vendor = null;
        try {
            vendor = this.vendorRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error finding vendor by ID {}: {}", id, e.getMessage(), e);
        }
        return vendor;
    }

    // Add a new vendor
    public Vendor addVendor(Vendor vendor) {
        logger.trace("Entering addVendor method with vendor: {}", vendor);
        return this.vendorRepository.save(vendor);
    }

    // Delete a vendor
    public void deleteVendor(int id) {
        logger.trace("Entering deleteVendor method with ID: {}", id);
        this.vendorRepository.deleteById(id);
    }

    // Update a vendor
    public void updateVendor(Vendor vendor, int id) {
        logger.trace("Entering updateVendor method with ID: {}", id);
        vendor.setId(id);
        this.vendorRepository.save(vendor);
    }
}
