package com.assignment.PDFGeneration.controller;

import java.io.IOException;
import java.net.BindException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.PDFGeneration.model.InvoiceRequest;
import com.assignment.PDFGeneration.service.PdfService;
import com.itextpdf.text.DocumentException;


@RestController
@RequestMapping("/api/pdf")
public class PdfController {
	@Autowired
	private PdfService pdfService;

	@PostMapping("/generate")
	public ResponseEntity<byte[]> generatePDF(@RequestBody InvoiceRequest invoiceRequest)
			throws DocumentException, IOException {
		String pdfPath = pdfService.generatePDF(invoiceRequest);

		Path path = Paths.get(pdfPath);
		byte[] pdfBytes = Files.readAllBytes(path);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=invoice.pdf");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}
}
