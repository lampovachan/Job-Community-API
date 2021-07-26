package com.tkachuk.cvgenerator.service;

import com.amazonaws.services.s3.model.S3Object;
import com.lowagie.text.DocumentException;
import com.tkachuk.common.dto.UserDto;

import java.io.IOException;

/**
 * Interface representing operations with employee's CV.
 *
 * @author Svitlana Tkachuk
 */

public interface CVService {
    /**
     * Ths method creates employee's CV.
     * @return filename of created CV.
     * @throws IOException
     * @throws DocumentException
     */
    String createCV (String userRequest) throws IOException, DocumentException;

    /**
     * This method gets employee's CV.
     * @return S3Object
     */
    S3Object getCV (String fileName);

    /**
     * This method updates employee's CV.
     * @return filename of updated CV.
     * @throws IOException
     * @throws DocumentException
     */
    void updateCV (UserDto user, String fileName) throws IOException, DocumentException;

    /**
     * This method deletes employee's CV.
     */
    void deleteCV (String fileName);
}
