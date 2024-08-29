//package com.rsl.event.controller;
//
//import java.io.ByteArrayInputStream;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.rsl.event.service.PdfService;
//
//@RestController
//@RequestMapping("/api/pdf")
//public class PdfController {
//
//    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);
//
//    @Autowired
//    private PdfService pdfService;
//
//    /**
//     * Endpoint to download a PDF document containing entity data.
//     * 
//     * @return ResponseEntity containing the PDF file.
//     */
//    @GetMapping("/download")
//    public ResponseEntity<InputStreamResource> downloadPdf() {
//        logger.info("Received request to download PDF");
//
//        // Generate the PDF
//        ByteArrayInputStream bis = pdfService.generatePdf();
//
//        // Set headers for the response
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=entities.pdf");
//
//        logger.info("Returning PDF document as response");
//        // Return the PDF as a response
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(bis));
//    }
//}
