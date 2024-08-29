package com.rsl.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsl.event.dao.AllInvoiceRepository;
import com.rsl.event.entity.AllInvoice;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AllInvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(AllInvoiceService.class);

    @Autowired
    private AllInvoiceRepository invoiceRepository;

    // Retrieves all invoices from the repository.
    public List<AllInvoice> getAllInvoices() {
        logger.info("Retrieving all invoices from repository.");
        return invoiceRepository.findAll();
    }

    // Retrieves an invoice by its ID.
    public AllInvoice getInvoiceById(Long id) {
        logger.info("Retrieving invoice with ID: {}", id);
        return invoiceRepository.findById(id).orElse(null);
    }

    // Saves an invoice to the repository.
    public AllInvoice saveInvoice(AllInvoice invoice) {
        logger.info("Saving invoice: {}", invoice);
        return invoiceRepository.save(invoice);
    }

    // Deletes an invoice by its ID.
    public void deleteInvoice(Long id) {
        logger.info("Deleting invoice with ID: {}", id);
        invoiceRepository.deleteById(id);
    }

//    // Calculates the total sales amount from all invoices.
//    public double getTotalSales() {
//        logger.info("Calculating total sales from invoices.");
//        return invoiceRepository.findAll().stream()
//                .mapToDouble(AllInvoice::getAmount)
//                .sum();
//    }
}
