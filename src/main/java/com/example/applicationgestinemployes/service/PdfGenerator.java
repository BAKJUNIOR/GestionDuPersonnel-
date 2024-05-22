package com.example.applicationgestinemployes.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.ByteArrayOutputStream;

@RequestScoped
@Named
public class PdfGenerator {

    public byte[] generateLeaveApprovalPdf(String message, String motif, String status) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Bonjour Monsieur Statut : " ));
        document.add(new Paragraph("Statut : " + status));
        document.add(new Paragraph("Motif du rejet : " + motif));
        document.add(new Paragraph(message));

        document.close();
        return baos.toByteArray();

    }



}
