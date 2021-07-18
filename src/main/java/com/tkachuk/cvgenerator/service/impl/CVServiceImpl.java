package com.tkachuk.cvgenerator.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.lowagie.text.DocumentException;
import com.tkachuk.cvgenerator.model.Employee;
import com.tkachuk.cvgenerator.service.CVService;
import com.tkachuk.cvgenerator.service.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Service implementing methods for working with employee's CV.
 * @author Svitlana Tkachuk
 */

@Service
public class CVServiceImpl implements CVService {
    String BUCKET_NAME = "test";

    /**
     * Utility method which parses Thymeleaf and generates PDF for working with it.
     * @param employee
     * @return PDF file
     * @throws IOException
     * @throws DocumentException
     */
    private File parseAndGenerate(Employee employee) throws IOException, DocumentException {
        PdfGenerator thymeleaf2Pdf = new PdfGeneratorImpl();
        String html = thymeleaf2Pdf.parseThymeleafTemplate(employee);
        return thymeleaf2Pdf.generatePdfFromHtml(html);
    }

    @Override
    public String createCV(Employee employee, AmazonS3 s3) throws IOException, DocumentException {
        CVServiceImpl employeeService = new CVServiceImpl();
        String filename = UUID.randomUUID().toString();
        File file = employeeService.parseAndGenerate(employee);

        if (!s3.doesBucketExist(BUCKET_NAME)) {
            s3.createBucket(BUCKET_NAME);
        }
        s3.putObject(BUCKET_NAME, filename + ".pdf", file);
        return filename;
    }

    @Override
    public S3Object getCV(AmazonS3 s3, String fileName) {
        return s3.getObject(BUCKET_NAME, fileName);
    }

    @Override
    public void updateCV(Employee employee, AmazonS3 s3, String fileName) throws IOException, DocumentException {
        CVServiceImpl employeeService = new CVServiceImpl();
        File file = employeeService.parseAndGenerate(employee);
        s3.putObject(BUCKET_NAME, fileName, file);
    }

    @Override
    public void deleteCV (AmazonS3 s3, String fileName) {
        s3.deleteObject(BUCKET_NAME, fileName); //not working method due to https://github.com/localstack/localstack/issues/3635
    }

}
