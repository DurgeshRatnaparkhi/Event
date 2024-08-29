package com.rsl.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rsl.event.dao.InvoiceRepository;
import com.rsl.event.entity.Invoice;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Get all invoices
    public List<Invoice> getAllInvoiceList() {
        logger.debug("Fetching all invoices.");
        List<Invoice> invoices = (List<Invoice>) this.invoiceRepository.findAll();
        logger.info("Retrieved {} invoices.", invoices.size());
        return invoices;
    }
     
    // Get invoice by ID
    public Optional<Invoice> getInvoiceById(int id) {
        logger.debug("Fetching invoice with ID: {}", id);
        Optional<Invoice> invoice = this.invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            logger.info("Invoice with ID: {} found.", id);
        } else {
            logger.warn("Invoice with ID: {} not found.", id);
        }
        return invoice;
    }

    // Add a new invoice
    public Invoice addInvoice(Invoice invoice) {
        logger.debug("Adding new invoice: {}", invoice);
        try {
            Invoice addedInvoice = this.invoiceRepository.save(invoice);
            logger.info("Invoice added successfully with ID: {}", addedInvoice.getId());
            return addedInvoice;
        } catch (Exception e) {
            logger.error("Error adding invoice: ", e);
            throw e; // Rethrow exception to handle it at a higher level
        }
    }

    // Delete invoice by ID
    public void deleteInvoice(int id) {
        logger.debug("Deleting invoice with ID: {}", id);
        try {
            this.invoiceRepository.deleteById(id);
            logger.info("Invoice with ID: {} deleted successfully.", id);
        } catch (Exception e) {
            logger.error("Error deleting invoice with ID: {}", id, e);
            throw e; // Rethrow exception to handle it at a higher level
        }
    }

    // Update an existing invoice
    public Invoice updateInvoice(Invoice invoice, int id) {
        logger.debug("Updating invoice with ID: {} and details: {}", id, invoice);
        try {
            invoice.setId(id);
            Invoice updatedInvoice = this.invoiceRepository.save(invoice);
            logger.info("Invoice with ID: {} updated successfully.", id);
            return updatedInvoice;
        } catch (Exception e) {
            logger.error("Error updating invoice with ID: {}", id, e);
            throw e; // Rethrow exception to handle it at a higher level
        }
    }
}
