package com.tkachuk.cvgenerator.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.itextpdf.text.DocumentException;
import com.tkachuk.cvgenerator.model.Employee;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.UUID;

@Service
public class PdfGeneratorImpl {
    public String createEmployeeCv(Employee employee, AmazonS3 s3) throws IOException, DocumentException, com.lowagie.text.DocumentException {
        String bucketName = "test";
        String fileName = java.util.UUID.randomUUID().toString();

        PdfGeneratorImpl thymeleaf2Pdf = new PdfGeneratorImpl();
        String html = thymeleaf2Pdf.parseThymeleafTemplate(employee);
        File file = thymeleaf2Pdf.generatePdfFromHtml(html);

        if (!s3.doesBucketExist(bucketName)) {
            s3.createBucket(bucketName);
        }
        s3.putObject(bucketName, fileName + ".pdf", file);
        return fileName;
    }

    public String updateEmployeeCV(Employee employee, AmazonS3 s3, String fileName) throws IOException, com.lowagie.text.DocumentException {
        String bucketName = "test";

        PdfGeneratorImpl thymeleaf2Pdf = new PdfGeneratorImpl();
        String html = thymeleaf2Pdf.parseThymeleafTemplate(employee);
        File file = thymeleaf2Pdf.generatePdfFromHtml(html);
        //s3.deleteObject(bucketName, fileName);
        s3.putObject(bucketName, fileName, file);
        return fileName;
    }

    public void deleteEmployeeCV(AmazonS3 s3, String fileName) throws IOException, com.lowagie.text.DocumentException {
        String bucketName = "test";

    }

    public File generatePdfFromHtml(String html) throws IOException, com.lowagie.text.DocumentException {
        File file = new File("thymeleaf.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        return file;
    }

    private String parseThymeleafTemplate(Employee employee) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("firstName", employee.getFirstName());
        context.setVariable("lastName", employee.getLastName());
        context.setVariable("age", employee.getAge());
        context.setVariable("goal", employee.getGoal());
        context.setVariable("experiences", employee.getExperiences());
        return templateEngine.process("thymeleaf_template", context);
    }

}
