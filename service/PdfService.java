package com.rsl.event.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.rsl.event.dao.QuotationRepository;
import com.rsl.event.entity.AllInvoice;
import com.rsl.event.entity.Item;
import com.rsl.event.entity.Quotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PdfService {

    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);

    @Autowired
    private QuotationRepository quotationRepository;

    // Method to create a PDF for a given quotation ID
    public byte[] createQuotationPdf(int quoId) {
        Optional<Quotation> quotationOptional = quotationRepository.findById(quoId);
        if (!quotationOptional.isPresent()) {
            logger.warn("Quotation with id {} not found for PDF generation.", quoId);
            return null;
        }

        Quotation quotation = quotationOptional.get();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new DualBorderPageEvent());
            document.open();

            // Add Quotation details table
            PdfPTable quotationTable = new PdfPTable(2);
            quotationTable.setWidthPercentage(100);
            addQuotationDetails(quotationTable, quotation);
            document.add(quotationTable);

            document.add(new Paragraph("\n"));

            // Add Item details table
            PdfPTable itemTable = new PdfPTable(7); // 7 columns
            itemTable.setWidthPercentage(100);
            addItemTableHeader(itemTable);
            addItemRows(itemTable, quotation.getItems());
            document.add(itemTable);

            document.close();

            logger.info("PDF successfully generated for Quotation ID: {}", quoId);
            return out.toByteArray();
        } catch (DocumentException | IOException e) {
            logger.error("Error generating PDF for Quotation ID: {}", quoId, e);
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    // Method to add quotation details to the table
    private void addQuotationDetails(PdfPTable table, Quotation quotation) {
        table.addCell("Quotation ID:");
        table.addCell(String.valueOf(quotation.getId()));
        table.addCell("Customer Name:");
        table.addCell(quotation.getCustomerName());
        table.addCell("Phone Number:");
        table.addCell(quotation.getPhoneNumber());
        table.addCell("Address:");
        table.addCell(quotation.getAddress());
        table.addCell("Email ID:");
        table.addCell(quotation.getEmailId());
        table.addCell("GSTIN:");
        table.addCell(quotation.getGstin());
        table.addCell("Quotation Date:");
        table.addCell(String.valueOf(quotation.getQuotationDate()));
        table.addCell("Quotation Time:");
        table.addCell(quotation.getQuotationTime());
        table.addCell("Venue Details:");
        table.addCell(quotation.getVenueDetails());
        table.addCell("Venue Date:");
        table.addCell(String.valueOf(quotation.getVenueDate()));
        table.addCell("Venue Time:");
        table.addCell(quotation.getVenueTime());
    }

    // Method to add headers to the item details table
    private void addItemTableHeader(PdfPTable table) {
        Stream.of("Item Name", "Quantity", "Price", "Total Amount", "Discount", "Payable Amount", "Miscellaneous")
              .forEach(columnTitle -> {
                  PdfPCell header = new PdfPCell();
                  header.setPhrase(new Phrase(columnTitle));
                  table.addCell(header);
              });
    }

    // Method to add item rows to the table
    private void addItemRows(PdfPTable table, List<Item> items) {
        for (Item item : items) {
            table.addCell(item.getItemName());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getPrice()));
            table.addCell(String.valueOf(item.getTotalAmount()));
            table.addCell(String.valueOf(item.getDiscount()));
            table.addCell(String.valueOf(item.getPayableAmount()));
            table.addCell(item.getMiscellaneous());
        }
    }

    // Custom page event to add dual borders to the PDF pages
    private static class DualBorderPageEvent extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = new Rectangle(document.getPageSize());
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(5);
            rect.setBorderColor(BaseColor.BLACK);
            rect.setUseVariableBorders(true);

            // Outer border
            canvas.rectangle(rect);

            // Inner border
            rect.setBorderWidth(2);
            rect.setBorderColor(BaseColor.GRAY);
            rect.setLeft(rect.getLeft() + 10);
            rect.setRight(rect.getRight() - 10);
            rect.setTop(rect.getTop() - 10);
            rect.setBottom(rect.getBottom() + 10);
            canvas.rectangle(rect);
        }
    }

	public byte[] createInvoicePdf(AllInvoice invoice) {
		// TODO Auto-generated method stub
		return null;
	}
}