package com.rsl.event.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rsl.event.entity.Quotation;
import com.rsl.event.service.QuotationService;

import jakarta.validation.Valid;

import com.rsl.event.service.PdfService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class QuotationController {

    private static final Logger logger = LoggerFactory.getLogger(QuotationController.class);

    @Autowired
    private QuotationService quotationService;

    @Autowired
    private PdfService pdfService;

    // Get all quotations
    @GetMapping("/quotation")
    public ResponseEntity<List<Quotation>> getAllQuotations() {
        try {
            List<Quotation> list = quotationService.getAllQuotationList();
            if (list.isEmpty()) {
                logger.info("No quotations found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            logger.info("Quotations retrieved successfully.");
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            logger.error("Error retrieving all quotations: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Add a new quotation
    @PostMapping("/quotation")
    public ResponseEntity<Quotation> addQuotation(@Valid @RequestBody Quotation quotation) {
        try {
            logger.info("Received quotation: {}", quotation);
            Quotation quo = quotationService.addQuotation(quotation);
            logger.info("Quotation added successfully with ID: {}", quo.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(quo);
        } catch (Exception e) {
            logger.error("Error adding quotation: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get a single quotation by id
    @GetMapping("/quotation/{quoId}")
    public ResponseEntity<Quotation> getQuotation(@PathVariable("quoId") int quoId) {
        try {
            Optional<Quotation> quo = quotationService.getQuotationById(quoId);
            return quo
                    .map(quotation -> {
                        logger.info("Quotation with id {} retrieved successfully.", quoId);
                        return ResponseEntity.ok(quotation);
                    })
                    .orElseGet(() -> {
                        logger.warn("Quotation with id {} not found.", quoId);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    });
        } catch (Exception e) {
            logger.error("Error retrieving quotation with id {}: {}", quoId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete a quotation by id
    @DeleteMapping("/quotation/{quoId}")
    public ResponseEntity<Void> deleteQuotation(@PathVariable("quoId") int quoId) {
        try {
            quotationService.deleteQuotation(quoId);
            logger.info("Quotation with id {} deleted successfully.", quoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting quotation with id {}: {}", quoId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update an existing quotation
    @PutMapping("/quotation/{quoId}")
    public ResponseEntity<Quotation> updateQuotation(@RequestBody Quotation quotation, @PathVariable("quoId") int quoId) {
        try {
            Quotation updatedQuotation = quotationService.updateQuotation(quotation, quoId);
            logger.info("Quotation with id {} updated successfully.", quoId);
            return ResponseEntity.ok(updatedQuotation);
        } catch (Exception e) {
            logger.error("Error updating quotation with id {}: {}", quoId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Generate PDF for a quotation
    @GetMapping("/quotation/{quoId}/pdf")
    public ResponseEntity<byte[]> generateQuotationPdf(@PathVariable("quoId") int quoId) {
        try {
            byte[] pdfBytes = pdfService.createQuotationPdf(quoId);
            if (pdfBytes == null) {
                logger.warn("Quotation with id {} not found for PDF generation.", quoId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quotation_" + quoId + ".pdf");
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
            logger.info("PDF successfully generated for Quotation ID: {}", quoId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error generating PDF for Quotation ID: {}", quoId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
