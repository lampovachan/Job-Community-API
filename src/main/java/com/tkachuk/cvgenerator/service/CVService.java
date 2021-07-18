package com.tkachuk.cvgenerator.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.lowagie.text.DocumentException;
import com.tkachuk.cvgenerator.model.Employee;

import java.io.IOException;

/**
 * Interface representing operations with employee's CV.
 */

public interface CVService {
    /**
     * Ths method creates employee's CV.
     * @param employee
     * @param s3 is storage for keeping CV files.
     * @return filename of created CV.
     * @throws IOException
     * @throws DocumentException
     */
    String createCV (Employee employee, AmazonS3 s3) throws IOException, DocumentException;

    /**
     * This method gets employee's CV.
     * @param s3 is storage for keeping CV files.
     * @return S3Object
     */
    S3Object getCV (AmazonS3 s3, String fileName);

    /**
     * This method updates employee's CV.
     * @param employee
     * @param s3 is storage for keeping updated CV files.
     * @return filename of updated CV.
     * @throws IOException
     * @throws DocumentException
     */
    void updateCV (Employee employee, AmazonS3 s3, String fileName) throws IOException, DocumentException;

    /**
     * This method deletes employee's CV.
     * @param s3 is storage where the files are keeping.
     */
    void deleteCV (AmazonS3 s3, String fileName);
}
