package com.rsl.event.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rsl.event.entity.Invoice;
import com.rsl.event.service.InvoiceService;
import com.rsl.event.service.InvoicePdfService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/invoice") // Common base path for all endpoints
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoicePdfService pdfService;

    // Fetch all invoices from the database
    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        logger.trace("Entering getAllInvoices method.");
        List<Invoice> list = invoiceService.getAllInvoiceList();

        if (list.isEmpty()) {
            logger.info("No invoices found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no invoices are found
        }
        logger.info("Invoices retrieved successfully.");
        return ResponseEntity.ok(list); // Return 200 with the list of invoices
    }

    // Add a new invoice to the database
    @PostMapping
    public ResponseEntity<Invoice> addInvoice(@Valid @RequestBody Invoice invoice) {
        logger.trace("Entering addInvoice method.");
        try {
            Invoice addedInvoice = invoiceService.addInvoice(invoice);
            logger.info("Invoice added successfully with ID: {}", addedInvoice.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(addedInvoice); // Return 201 with the created invoice
        } catch (Exception e) {
            logger.error("Error adding invoice: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 if an error occurs
        }
    }

    // Retrieve an invoice by its ID
    @GetMapping("/{incId}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("incId") int incId) {
        logger.trace("Entering getInvoice method with ID: {}", incId);
        Optional<Invoice> invoiceOpt = invoiceService.getInvoiceById(incId);

        return invoiceOpt
                .map(invoice -> {
                    logger.info("Invoice with ID: {} retrieved successfully.", incId);
                    return ResponseEntity.ok(invoice); // Return 200 with the invoice
                })
                .orElseGet(() -> {
                    logger.warn("Invoice with ID: {} not found.", incId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if invoice is not found
                });
    }

    // Delete an invoice by its ID
    @DeleteMapping("/{incId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("incId") int incId) {
        logger.trace("Entering deleteInvoice method with ID: {}", incId);
        try {
            invoiceService.deleteInvoice(incId);
            logger.info("Invoice with ID: {} deleted successfully.", incId);
            return ResponseEntity.noContent().build(); // Return 204 if deletion is successful
        } catch (Exception e) {
            logger.error("Error deleting invoice with ID: {}", incId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 if an error occurs
        }
    }

    // Update an existing invoice
    @PutMapping("/{incId}")
    public ResponseEntity<Invoice> updateInvoice(@RequestBody Invoice invoice, @PathVariable("incId") int incId) {
        logger.trace("Entering updateInvoice method with ID: {}", incId);
        try {
            Invoice updatedInvoice = invoiceService.updateInvoice(invoice, incId);
            logger.info("Invoice with ID: {} updated successfully.", incId);
            return ResponseEntity.ok(updatedInvoice); // Return 200 with the updated invoice
        } catch (Exception e) {
            logger.error("Error updating invoice with ID: {}", incId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 if an error occurs
        }
    }
    
    // Generates a PDF for a specific invoice by its ID
    @GetMapping("/invoice/{id}/pdf")
    public ResponseEntity<byte[]> generateInvoicePdf(@PathVariable("id") int id) {
        logger.info("Request to generate PDF for Invoice ID: {}", id);
        Optional<Invoice> invoiceOpt = invoiceService.getInvoiceById(id);
        if (invoiceOpt.isEmpty()) {
            logger.warn("Invoice with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Invoice invoice = invoiceOpt.get();
        byte[] pdfBytes = pdfService.createInvoicePdf(invoice);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + id + ".pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        logger.info("PDF successfully generated for Invoice ID: {}", id);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
