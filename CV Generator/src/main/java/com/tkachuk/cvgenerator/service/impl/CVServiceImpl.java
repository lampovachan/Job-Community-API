package com.tkachuk.cvgenerator.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.lowagie.text.DocumentException;
import com.tkachuk.cvgenerator.model.Employee;
import com.tkachuk.cvgenerator.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${localstack.s3.bucketName}")
    private String bucketName;
    private final String EXTENSION = ".pdf";

    private AmazonS3 configure;
    private PdfGeneratorImpl pdfGenerator;

    @Autowired
    public CVServiceImpl(PdfGeneratorImpl pdfGenerator, AmazonS3 configure) {
        this.pdfGenerator = pdfGenerator;
        this.configure = configure;
    }

    /**
     * Utility method which parses Thymeleaf and generates PDF for working with it.
     * @param employee
     * @return PDF file
     * @throws IOException
     * @throws DocumentException
     */
    private File parseAndGenerate(Employee employee) throws IOException, DocumentException {
        String html = pdfGenerator.parseThymeleafTemplate(employee);
        return pdfGenerator.generatePdfFromHtml(html);
    }

    @Override
    public String createCV(Employee employee) throws IOException, DocumentException {
        String filename = UUID.randomUUID().toString();
        File file = parseAndGenerate(employee);

        if (!configure.doesBucketExist(bucketName)) {
            configure.createBucket(bucketName);
        }
        configure.putObject(bucketName, filename + EXTENSION, file);
        return filename;
    }

    @Override
    public S3Object getCV(String fileName) {
        return configure.getObject(bucketName, fileName + EXTENSION);
    }

    @Override
    public void updateCV(Employee employee, String fileName) throws IOException, DocumentException {
        File file = parseAndGenerate(employee);
        configure.putObject(bucketName, fileName + EXTENSION, file);
    }

    @Override
    public void deleteCV (String fileName) {
        configure.deleteObject(bucketName, fileName + EXTENSION); //not working method due to https://github.com/localstack/localstack/issues/3635
    }

}