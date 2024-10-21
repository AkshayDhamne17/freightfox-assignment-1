package com.assignment.PDFGeneration.service;

import java.io.FileOutputStream;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.assignment.PDFGeneration.model.InvoiceRequest;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {
	  public String generatePDF(InvoiceRequest invoiceRequest) throws DocumentException, IOException {
	        String pdfPath = "invoice.pdf";
	        Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
	        document.open();

	        // Add seller and buyer details
	        PdfPTable table = new PdfPTable(2);
	        table.setWidthPercentage(100);

	        PdfPCell cell = new PdfPCell(new Paragraph("Seller:\n" + invoiceRequest.getSeller() + "\n" + invoiceRequest.getSellerAddress() + "\nGSTIN: " + invoiceRequest.getSellerGstin()));
	        cell.setBorder(PdfPCell.NO_BORDER);
	        table.addCell(cell);

	        cell = new PdfPCell(new Paragraph("Buyer:\n" + invoiceRequest.getBuyer() + "\n" + invoiceRequest.getBuyerAddress() + "\nGSTIN: " + invoiceRequest.getBuyerGstin()));
	        cell.setBorder(PdfPCell.NO_BORDER);
	        table.addCell(cell);

	        document.add(table);

	        // Add item details
	        final PdfPTable itemtable = new PdfPTable(4);
	        itemtable.setWidthPercentage(100);
	        itemtable.setSpacingBefore(20f);
	        itemtable.addCell("Item");
	        itemtable.addCell("Quantity");
	        itemtable.addCell("Rate");
	        itemtable.addCell("Amount");

	        invoiceRequest.getItems().forEach(item -> {
	        	itemtable.addCell(item.getName() != null ? item.getName() : "N/A"); // Handle nulls if necessary
	        	itemtable.addCell(String.valueOf(item.getQuantity())); // Ensure this is a String
	        	itemtable.addCell(item.getRate() != null ? item.getRate().toString() : "0.0"); // Handle nulls
	        	itemtable.addCell(item.getAmount() != null ? item.getAmount().toString() : "0.0"); // Handle nulls
	        });

	        document.add(itemtable);
	        document.close();

	        return pdfPath;
	    }
}
