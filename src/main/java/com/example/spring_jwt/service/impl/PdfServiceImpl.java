package com.example.spring_jwt.service.impl;


import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.model.MedicationDetail;
import com.example.spring_jwt.model.TestResult;
import com.example.spring_jwt.model.request.CreateMedicalRecord;
import com.example.spring_jwt.service.PatientService;
import com.example.spring_jwt.service.PdfService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
public class PdfServiceImpl implements PdfService {
    @Autowired
    PatientService patientService;

    public String generatePdf(CreateMedicalRecord createMedicalRecord, String imageString) throws IOException, DocumentException {
        Patient patient = patientService.getPatientById(createMedicalRecord.getPatientId());
        String outputFile = "src/main/resources/medical_record.pdf";

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();

        BaseFont unicodeFont = BaseFont.createFont("src/main/resources/font/NotoSans-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        // Add content to the PDF
        Font titleFont = new Font(unicodeFont, 18, Font.BOLD);
        Font normalFont = new Font(unicodeFont, 12);
        Font boldFont = new Font(unicodeFont, 12, Font.BOLD);

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingAfter(10f);

        Paragraph title = new Paragraph("HỒ SƠ BỆNH ÁN", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = today.format(formatter);
        Paragraph date = new Paragraph("Ngày: " + formattedDate + "\n(DD - MM - YYYY)", normalFont);
        date.setAlignment(Element.ALIGN_CENTER);
        document.add(date);

        document.add(Chunk.NEWLINE);

        PdfPTable nameTable = createTable(normalFont, "1. Họ tên:", patient.getUser().getFullName(), boldFont);
        document.add(nameTable);

        PdfPTable addressTable = createTable(normalFont, "2. Địa chỉ:", patient.getAddress(), boldFont);
        document.add(addressTable);

        PdfPTable phoneTable = createTable(normalFont, "3. Số điện thoại:", patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "", boldFont);
        document.add(phoneTable);

        PdfPTable birthDayTable = createTable(normalFont, "4. Ngày sinh:", patient.getDateOfBirth() != null ? patient.getDateOfBirth().toString() : null, boldFont);
        document.add(birthDayTable);

        String gender = "Nam";
        if(gender.equals(patient.getUser().getGender())){
            gender = "Nữ";
        }
        document.add(new Paragraph("5. Giới tính: " + gender, boldFont));

        document.add(new Paragraph("6. Hình ảnh xét nghiệm:", boldFont));
        PdfPTable imageTable = imageTable(imageString);
        document.add(imageTable);


        //String testResultJson = createMedicalRecord.getTestResult();
        document.add(new Paragraph("7. Kết quả xét nghiệm:", boldFont));
        PdfPTable testResultsTable = createTestResultsTable(normalFont, createMedicalRecord, boldFont);
        document.add(testResultsTable);


        document.add(new Paragraph("9. Tình trạng hiện tại:", boldFont));
        PdfPTable currentConditionTable = createCommonListTable(normalFont, createMedicalRecord.getCurrentCondition());
        document.add(currentConditionTable);


        document.add(new Paragraph("10. Tiến triển của bệnh: ", boldFont));
        PdfPTable diseaseProgressionTable = createCommonListTable(normalFont, createMedicalRecord.getDiseaseProgression());
        document.add(diseaseProgressionTable);

        Gson gson = new Gson();
        String medicalDetailsString = gson.toJson(createMedicalRecord.getMedicationDetails());
        document.add(new Paragraph("8. Kê đơn thuốc:", boldFont));
        PdfPTable medicalDetailsTable = createMedicalDetailsTable(normalFont, medicalDetailsString);
        document.add(medicalDetailsTable);

        PdfPTable doctorNameTable = createTable(normalFont, "9. Tên bác sĩ:", createMedicalRecord.getDoctorName(), boldFont);
        document.add(doctorNameTable);

        document.add(new Paragraph("11. Ghi chú từ bác sĩ:", boldFont));
        document.add(new Paragraph(createMedicalRecord.getNoteFromDoctor(), normalFont));

        document.close();

        System.out.println("PDF created successfully!");
        return outputFile;
    }


    private static String getCheckmarkSymbol() {
        return String.valueOf((char) 0x2713);
    }

    private static void addCell2Collumn(PdfPTable table, String label, String value, Font font, Font labelFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
        valueCell.setBorder(Rectangle.NO_BORDER);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private static void addCell(PdfPTable table, String value, Font font) {
        PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
        valueCell.setPadding(5);
        valueCell.setBorder(Rectangle.NO_BORDER);

        table.addCell(valueCell);
    }

    private static PdfPTable createTable(Font font, String label, String value, Font labelFont) throws JsonProcessingException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingAfter(10f);
        table.setSpacingBefore(5f);

        addCell2Collumn(table, label, value, font, labelFont);
        return table;
    }

    private static PdfPTable imageTable(String listImageUrls) throws IOException, BadElementException {
        String images = listImageUrls;

        images = images.replace("[", "").replace("]", "").replace("\"", "");
        ArrayList<String> imageUrlList = new ArrayList<>();
        imageUrlList.add(images);
        if (images.contains(",")) {
            String[] imageArray = images.split(",");
            imageUrlList = new ArrayList<>(Arrays.asList(imageArray));
        }

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        for (String imageUrl : imageUrlList) {
            System.out.println(imageUrl);
            if (imageUrl != null) {
                Image image = Image.getInstance(imageUrl);
                PdfPCell cell = new PdfPCell(image, true);
                cell.setPadding(10);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

        }
        return table;
    }

    private static PdfPTable createCommonListTable(Font font, String jsonString) throws JsonProcessingException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingAfter(10f);

        String[] elements = jsonString.split("\\\\n");

        List<String> resultList = new ArrayList<>();

        for (String element : elements) {
            resultList.add(element.trim());
        }

        for (String result : resultList) {
            addCell(table, result, font);
        }
        return table;
    }

    private static PdfPTable createMedicalDetailsTable(Font font, String mediationDetailsString) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<MedicationDetail> medicationDetailList = objectMapper.readValue(mediationDetailsString, new TypeReference<List<MedicationDetail>>() {
        });

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingAfter(10f);


        for (MedicationDetail detail : medicationDetailList) {
            addCell2Collumn(table, "Tên thuốc:", detail.getMedicineName(), font, font);
            addCell2Collumn(table, "Tần suất sử dụng:", detail.getFrequency(), font, font);
            addCell2Collumn(table, "Thời gian sử dụng:", detail.getDuration(), font, font);
            addSeparator(table);
        }
        return table;
    }

    private static void addSeparator(PdfPTable table) {
        PdfPCell separatorCell = new PdfPCell();
        separatorCell.setBorder(Rectangle.BOTTOM);
        separatorCell.setColspan(2);
        separatorCell.setFixedHeight(10f);
        table.addCell(separatorCell);
    }

    private static PdfPTable createTestResultsTable(Font font, CreateMedicalRecord createMedicalRecord, Font boldFont) throws JsonProcessingException {

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingAfter(10f);
        table.setSpacingBefore(10f);

        PdfPCell cell1 = new PdfPCell(new Phrase("Xét nghiệm hóa sinh: ", boldFont));
        cell1.setPadding(5);
        cell1.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);

        String bioChemicalTestString = createMedicalRecord.getBiochemicalTests();
        String[] bioChemicalTestStrings = bioChemicalTestString.split("\\\\n");
        List<String> bioChemicalTestResult = new ArrayList<>();

        for (String element : bioChemicalTestStrings) {
            bioChemicalTestResult.add(element.trim());
        }


        Paragraph testResultsParagraph1 = new Paragraph(createMedicalRecord.getBiochemicalTests(), font);
        testResultsParagraph1.setIndentationLeft(20f);
        for (String result : bioChemicalTestResult) {
            addCell(table, result, font);
        }


        PdfPCell cell2 = new PdfPCell(new Phrase("Chẩn đoán hình ảnh: ", boldFont));
        cell2.setPadding(5);
        cell2.setBorder(Rectangle.NO_BORDER);

        table.addCell(cell2);

        String imageAnalysString = createMedicalRecord.getImageAnalysation();
        String[] iamgeAnalysStringArr = imageAnalysString.split("\\\\n");
        List<String> imagesAnalystations = new ArrayList<>();

        for (String element : iamgeAnalysStringArr) {
            imagesAnalystations.add(element.trim());
        }

        Paragraph testResultsParagraph2 = new Paragraph(createMedicalRecord.getBiochemicalTests(), font);
        testResultsParagraph2.setIndentationLeft(20f);
        for (String result : imagesAnalystations) {
            addCell(table, result, font);
        }
        //addCell(table, createMedicalRecord.getBiochemicalTests(), font);
        addSeparator(table);

        return table;
    }


//    public static void main(String[] args) throws DocumentException, IOException {
//        Patient patient = new Patient();
//        patient.setAge(25);
//        patient.setEmail("linhdqth2108046@fpt.com.vn");
//        patient.setFullName("Đào Quang Linh");
//        patient.setGeder("Male");
//        patient.setAddress("123 Đường ABC, Quận XYZ, Thành phố HCM");
//        patient.setPhoneNumber("0901 234 567");
//        patient.setDateOfBirth("15/05/1980");
//        String jsonString = "{\"data\":[{\"Xét nghiệm hóa sinh\":[\"Máu: Glucose: 90 mg/dL, Cholesterol: 180 mg/dL\",\"Nước tiểu: Glucose: âm tính, Protein: âm tính\"]},{\"Chẩn đoán hình ảnh:\":[\"X-quang phổi: Không có bất thường\",\"Siêu âm bụng: Thận trái bình thường, gan không có vấn đề gì đáng ngại\"]}]}";
//        String mediationDetailsString = "[{\"medicineName\":\"me1\",\"frequency\":\"fre1\",\"duration\":\"dura1\"},{\"medicineName\":\"me1\",\"frequency\":\"fre1\",\"duration\":\"dura1\"}]";
//        String currentConditonString = "- Triệu chứng: Ho nhẹ, sốt nhẹ\n- Dấu hiệu: Hơi mệt mỏi sau khi ho\n- Tình trạng sức khỏe: Khá";
//        String diseaseProgression = "-Tình hình tiến triển bệnh: Ho đã giảm sau khi dùng thuốc\n- Phản ứng với điều trị: Tốt\n- không có phản ứng phụ\n- Sự kiện quan trọng: Không có";
//        CreateMedicalRecord createMedicalRecord = new CreateMedicalRecord();
//        createMedicalRecord.setPatient(patient);
//        createMedicalRecord.setDoctorName("Trần Văn A");
//        createMedicalRecord.setNoteFromDoctor("Bệnh nhân cần tiếp tục sử dụng thuốc theo đúng hướng dẫn và tái khám sau 1 tuần");
//        createMedicalRecord.setTestResult(jsonString);
//        createMedicalRecord.setCurrentCondition(currentConditonString);
//        createMedicalRecord.setDiseaseProgression(diseaseProgression);
//        createMedicalRecord.setMedicationDetails(mediationDetailsString);
//        generatePdf(createMedicalRecord);
//
//    }
}
