package com.tkachuk.cvgenerator.service;

import com.lowagie.text.DocumentException;
import com.tkachuk.common.dto.UserDto;

import java.io.File;
import java.io.IOException;

/**
 * Interface representing methods for parsing HTML to PDF file.
 */
public interface PdfGenerator {
    /**
     * This method generates PDF from HTML creating ITextRenderer object and using its methods for this goal.
     * @param html
     * @return PDF file
     * @throws IOException
     * @throws DocumentException
     */

    File generatePdfFromHtml(String html) throws IOException, DocumentException;


    /**
     * This method parses Thymeleaf so its objects can be accessible for further actions.
     * @return context of Thymeleaf
     */

    String parseThymeleafTemplate(UserDto user);
}
