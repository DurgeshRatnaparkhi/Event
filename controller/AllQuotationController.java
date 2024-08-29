package com.rsl.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rsl.event.entity.AllQuotation;
import com.rsl.event.service.AllQuotationService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class AllQuotationController {

    private static final Logger LOGGER = Logger.getLogger(AllQuotationController.class.getName());

    @Autowired
    private AllQuotationService allQuotationService;

    // Get all quotations
    @GetMapping("/allquotations")
    public ResponseEntity<List<AllQuotation>> getAllQuotations() {
        LOGGER.info("Received request to retrieve all quotations");
        List<AllQuotation> quotations = allQuotationService.getAllQuotations();
        LOGGER.info("Returning " + quotations.size() + " quotations");
        return ResponseEntity.ok(quotations);
    }

    // Create a new quotation
    @PostMapping("/allquotations")
    public ResponseEntity<AllQuotation> createQuotation(@Valid @RequestBody AllQuotation allQuotation) {
        if (allQuotation == null) {
            LOGGER.warning("Received null data for quotation creation");
            return ResponseEntity.badRequest().build();
        }
        LOGGER.info("Received request to create a new quotation with details: " + allQuotation);
        try {
            AllQuotation createdQuotation = allQuotationService.createQuotation(allQuotation);
            LOGGER.info("Created new quotation with ID: " + createdQuotation.getId());
            return ResponseEntity.status(201).body(createdQuotation);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while creating a new quotation", e);
            return ResponseEntity.status(500).build();
        }
    }

    // Update an existing quotation
    @PutMapping("/{id}")
    public ResponseEntity<AllQuotation> updateQuotation(@PathVariable Integer id, @RequestBody AllQuotation allQuotation) {
        if (allQuotation == null || id == null) {
            LOGGER.warning("Received null data for updating quotation with ID: " + id);
            return ResponseEntity.badRequest().build();
        }
        LOGGER.info("Received request to update quotation with ID: " + id + " with new details: " + allQuotation);
        try {
            AllQuotation updatedQuotation = allQuotationService.updateQuotation(id, allQuotation);
            LOGGER.info("Updated quotation with ID: " + id);
            return ResponseEntity.ok(updatedQuotation);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while updating quotation with ID: " + id, e);
            return ResponseEntity.status(500).build();
        }
    }

    // Delete a quotation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuotation(@PathVariable Integer id) {
        if (id == null) {
            LOGGER.warning("Received null ID for deletion: {}");
            return ResponseEntity.badRequest().build();
        }
        LOGGER.info("Received request to delete quotation with ID: " + id);
        try {
            allQuotationService.deleteQuotation(id);
            LOGGER.info("Deleted quotation with ID: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while deleting quotation with ID: " + id, e);
            return ResponseEntity.status(500).build();
        }
    }
}
