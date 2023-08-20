package com.example.spring_jwt.service;

import com.example.spring_jwt.model.request.CreateMedicalRecord;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface PdfService {
     String generatePdf(CreateMedicalRecord createMedicalRecord, String imageString) throws IOException, DocumentException;
}
