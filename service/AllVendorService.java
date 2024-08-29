package com.rsl.event.service;

import com.rsl.event.dao.AllVendorRepository;
import com.rsl.event.entity.AllVendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class AllVendorService {

    private static final Logger logger = LoggerFactory.getLogger(AllVendorService.class);

    @Autowired
    private AllVendorRepository allVendorRepository;

    // Retrieves all vendors from the repository.
    public Iterable<AllVendor> getAllVendors() {
        logger.info("Retrieving all vendors from repository.");
        return allVendorRepository.findAll();
    }

    // Retrieves a vendor by its ID. 
    public AllVendor getVendorById(Long id) {
        logger.info("Retrieving vendor with ID: {}", id);
        return allVendorRepository.findById(id).orElse(null);
    }

    // Saves a vendor to the repository.
    public AllVendor saveVendor(AllVendor allVendor) {
        logger.info("Saving vendor: {}", allVendor);
        return allVendorRepository.save(allVendor);
    }

    // Deletes a vendor by its ID.
    public void deleteVendor(Long id) {
        logger.info("Deleting vendor with ID: {}", id);
        allVendorRepository.deleteById(id);
    }
}
