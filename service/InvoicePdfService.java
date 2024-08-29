package com.rsl.event.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.rsl.event.entity.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class InvoicePdfService {
    private static final Logger logger = LoggerFactory.getLogger(InvoicePdfService.class);

    /**
     * Creates a PDF document for the given invoice.
     * 
     * @param invoice the invoice for which the PDF document is to be created.
     * @return a byte array containing the PDF data.
     */
    public byte[] createInvoicePdf(Invoice invoice) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            document.add(new Paragraph("Invoice Details"));
            document.add(new Paragraph("Invoice ID: " + invoice.getId()));
            document.add(new Paragraph("Customer Name: " + invoice.getCustomer()));
            document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
            document.add(new Paragraph("Phone Number: " + invoice.getPhoneNumber()));
            document.add(new Paragraph("Address: " + invoice.getAddress()));
            document.add(new Paragraph("Email: " + invoice.getEmailId()));
            document.add(new Paragraph("GST Number: " + invoice.getGstinNumber()));
            document.add(new Paragraph("Date and Time: " + invoice.getDateTime()));
            document.add(new Paragraph("Venue Details: " + invoice.getVenueDetails()));

            // You might want to add items details if needed
            // for (Item item : invoice.getItems()) {
            //     document.add(new Paragraph("Item: " + item.getName() + ", Price: " + item.getPrice()));
            // }

            document.close();
            logger.info("PDF generated successfully for Invoice ID: {}", invoice.getId());
        } catch (Exception e) {
            logger.error("Error generating PDF for Invoice ID: {}", invoice.getId(), e);
        }

        return byteArrayOutputStream.toByteArray();
    }
}
