package com.rsl.event.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsl.event.dao.QuotationRepository;
import com.rsl.event.entity.Quotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    private static final Logger logger = LoggerFactory.getLogger(QuotationService.class);

    // Get all quotations
    public List<Quotation> getAllQuotationList() {
        return (List<Quotation>) this.quotationRepository.findAll();
    }
     
    // Get a single quotation by id
    public Optional<Quotation> getQuotationById(int id) {
        return this.quotationRepository.findById(id);
    }

    // Add a new quotation
    public Quotation addQuotation(Quotation quotation) {
        try {
            logger.info("Adding quotation: {}", quotation);
            Quotation savedQuotation = this.quotationRepository.save(quotation);
            logger.info("Quotation added successfully");
            return savedQuotation;
        } catch (Exception e) {
            logger.error("Error adding quotation: {}", e.getMessage(), e);
            throw e; // Rethrow the exception to be handled by the controller
        }
    }

    // Delete a quotation by id
    public void deleteQuotation(int id) {
        try {
            this.quotationRepository.deleteById(id);
            logger.info("Quotation with id {} deleted successfully.", id);
        } catch (Exception e) {
            logger.error("Error deleting quotation with id {}: {}", id, e.getMessage(), e);
            throw e; // Rethrow the exception to be handled by the controller
        }
    }

    // Update an existing quotation
    public Quotation updateQuotation(Quotation quotation, int id) {
        try {
            Optional<Quotation> existingQuotation = this.quotationRepository.findById(id);
            if (existingQuotation.isPresent()) {
                Quotation updatedQuotation = existingQuotation.get();
                updatedQuotation.setCustomerName(quotation.getCustomerName());
                updatedQuotation.setPhoneNumber(quotation.getPhoneNumber());
                updatedQuotation.setAddress(quotation.getAddress());
                updatedQuotation.setEmailId(quotation.getEmailId());
                updatedQuotation.setGstin(quotation.getGstin());
                updatedQuotation.setQuotationDate(quotation.getQuotationDate());
                updatedQuotation.setQuotationTime(quotation.getQuotationTime());
                updatedQuotation.setVenueDetails(quotation.getVenueDetails());
                updatedQuotation.setVenueDate(quotation.getVenueDate());
                updatedQuotation.setVenueTime(quotation.getVenueTime());
                updatedQuotation.setItems(quotation.getItems());
                logger.info("Updating quotation with id: {}", id);
                Quotation savedQuotation = this.quotationRepository.save(updatedQuotation);
                logger.info("Quotation updated successfully.");
                return savedQuotation;
            } else {
                logger.warn("Quotation with id {} not found for update.", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error updating quotation with id {}: {}", id, e.getMessage(), e);
            throw e; // Re throw the exception to be handled by the controller
        }
    }
}