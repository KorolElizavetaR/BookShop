package com.bookshop.oz.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.LocationReport;
import com.bookshop.oz.model.Order;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PdfGenerator {
	public byte[] generatePdf(List<LocationReport> reports) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// Create a Document
			Document document = new Document();

			// Initialize PdfWriter
			PdfWriter.getInstance(document, out);

			// Open the Document
			document.open();
			BaseFont baseFont = BaseFont.createFont("src/main/resources/fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(baseFont, 12, Font.NORMAL);

			// Add Cyrillic content

			// Add content to the PDF
			for (LocationReport report : reports) {
				// Add location details
				document.add(new Paragraph("Location ID: " + report.getLocationPoint().getLocationId(), font));
				document.add(new Paragraph("City: " + report.getLocationPoint().getCity(), font));
				document.add(new Paragraph("Address: " + report.getLocationPoint().getAddress(), font));
				document.add(new Paragraph("Orders:"));

				// Create a table for orders
				PdfPTable table = new PdfPTable(2); // Two columns: Order ID, Cost
				table.setWidthPercentage(100);
				table.setSpacingBefore(10f);
				table.setSpacingAfter(10f);

				// Add table headers
				PdfPCell header1 = new PdfPCell(new Paragraph("Order ID"));
				header1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(header1);

				PdfPCell header2 = new PdfPCell(new Paragraph("Cost"));
				header2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(header2);

				// Add table rows
				for (Order order : report.getOrders()) {
					table.addCell(order.getOrderId().toString());
					double cost = order.getBookProduct().getPrice().doubleValue() * order.getQuantity();
					table.addCell(String.format("%.2f", cost));
				}

				// Add the table to the document
				document.add(table);

				// Add total profit
				document.add(new Paragraph("Total Profit: " + String.format("%.2f", report.getTotalProfit())));
				document.add(new Paragraph("\n"));
			}

			// Close the document
			document.close();

			return out.toByteArray();
		} catch (DocumentException e) {
			throw new RuntimeException("Error generating PDF", e);
		} catch (IOException e1) {
			throw new RuntimeException(e1.getLocalizedMessage());
		}
	}

}
