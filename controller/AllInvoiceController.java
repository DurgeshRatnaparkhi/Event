package com.rsl.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.rsl.event.entity.AllInvoice;
import com.rsl.event.service.AllInvoiceService;
import com.rsl.event.service.InvoicePdfService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AllInvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(AllInvoiceController.class);

    @Autowired
    private AllInvoiceService invoiceService;

    @Autowired
    private InvoicePdfService pdfService;

    // Retrieves all invoices.
    @GetMapping("/allinvoices")
    public ResponseEntity<List<AllInvoice>> getAllInvoices() {
        logger.info("Fetching all invoices.");
        List<AllInvoice> invoices = invoiceService.getAllInvoices();
        if (invoices.isEmpty()) {
            logger.warn("No invoices found.");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(invoices);
    }

    // Retrieves an invoice by its ID.
    @GetMapping("/allinvoices/{id}")
    public ResponseEntity<AllInvoice> getInvoiceById(@PathVariable Long id) {
        logger.info("Fetching invoice with ID: {}", id);
        return ResponseEntity.of(Optional.ofNullable(invoiceService.getInvoiceById(id)));
    }

    // Creates a new invoice.
    @PostMapping("/allinvoices")
    public ResponseEntity<AllInvoice> createInvoice(@Valid @RequestBody AllInvoice invoice) {
        logger.info("Creating new invoice with details: {}", invoice);
        try {
            AllInvoice createdInvoice = invoiceService.saveInvoice(invoice);
            logger.info("Invoice created successfully with ID: {}", createdInvoice.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);
        } catch (Exception e) {
            logger.error("Error creating invoice: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Updates an existing invoice.
    @PutMapping("/allinvoices/{id}")
    public ResponseEntity<AllInvoice> updateInvoice(@PathVariable Long id, @Valid @RequestBody AllInvoice invoiceDetails) {
        logger.info("Updating invoice with ID: {}", id);
        try {
            AllInvoice existingInvoice = invoiceService.getInvoiceById(id);
            if (existingInvoice == null) {
                logger.warn("Invoice with ID: {} not found for update.", id);
                return ResponseEntity.notFound().build();
            }

            // Update fields
            existingInvoice.setName(invoiceDetails.getName());
            existingInvoice.setDate(invoiceDetails.getDate());
            existingInvoice.setAmount(invoiceDetails.getAmount());
            existingInvoice.setBalance(invoiceDetails.getBalance());
            existingInvoice.setStatus(invoiceDetails.getStatus());

            AllInvoice updatedInvoice = invoiceService.saveInvoice(existingInvoice);
            logger.info("Invoice with ID: {} updated successfully.", id);
            return ResponseEntity.ok(updatedInvoice);
        } catch (Exception e) {
            logger.error("Error updating invoice with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Deletes an invoice by its ID.
    @DeleteMapping("/allinvoices/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        logger.info("Deleting invoice with ID: {}", id);
        try {
            if (invoiceService.getInvoiceById(id) == null) {
                logger.warn("Invoice with ID: {} not found for deletion.", id);
                return ResponseEntity.notFound().build();
            }
            invoiceService.deleteInvoice(id);
            logger.info("Invoice with ID: {} deleted successfully.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting invoice with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
}
