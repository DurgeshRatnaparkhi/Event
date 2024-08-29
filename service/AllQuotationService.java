package com.rsl.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rsl.event.dao.AllQuotationRepository;
import com.rsl.event.entity.AllQuotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Component
public class AllQuotationService {

    @Autowired
    private AllQuotationRepository allQuotationRepository;

    private static final Logger logger = LoggerFactory.getLogger(AllQuotationService.class);

    // Get all quotations
    public List<AllQuotation> getAllQuotations() {
        logger.info("Fetching all quotations");
        return (List<AllQuotation>) allQuotationRepository.findAll();
    }

    // Get a single quotation by id
    public Optional<AllQuotation> getQuotationById(int id) {
        logger.info("Fetching quotation with ID: {}", id);
        return allQuotationRepository.findById(id);
    }

    // Get quotations by customer name
    public List<AllQuotation> getQuotationsByCustomerName(String customerName) {
        logger.info("Fetching quotations for customer: {}", customerName);
        return allQuotationRepository.findByCustomerName(customerName);
    }

    // Add a new quotation
    public AllQuotation createQuotation(AllQuotation allQuotation) {
        logger.info("Creating new quotation: {}", allQuotation);
        return allQuotationRepository.save(allQuotation);
    }

    // Update an existing quotation
    public AllQuotation updateQuotation(int id, AllQuotation allQuotationDetails) {
        logger.info("Updating quotation with ID: {}", id);
        AllQuotation allQuotation = allQuotationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Quotation not found with ID: {}", id);
                    return new RuntimeException("Quotation not found");
                });

        allQuotation.setStatus(allQuotationDetails.getStatus());
        allQuotation.setCustomerName(allQuotationDetails.getCustomerName());
        allQuotation.setSaleNumber(allQuotationDetails.getSaleNumber());
        allQuotation.setSaleDate(allQuotationDetails.getSaleDate());
        allQuotation.setTotalAmount(allQuotationDetails.getTotalAmount());
        allQuotation.setBalance(allQuotationDetails.getBalance());

        logger.debug("Updated quotation: {}", allQuotation);
        return allQuotationRepository.save(allQuotation);
    }

    // Delete a quotation by id
    public void deleteQuotation(int id) {
        logger.info("Deleting quotation with ID: {}", id);
        AllQuotation allQuotation = allQuotationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Quotation not found with ID: {}", id);
                    return new RuntimeException("Quotation not found");
                });

        allQuotationRepository.delete(allQuotation);
        logger.debug("Deleted quotation with ID: {}", id);
    }
}
