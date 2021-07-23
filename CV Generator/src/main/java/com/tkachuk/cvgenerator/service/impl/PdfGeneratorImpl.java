package com.tkachuk.cvgenerator.service.impl;

import com.lowagie.text.DocumentException;
import com.tkachuk.common.dto.UserDto;
import com.tkachuk.cvgenerator.service.PdfGenerator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * Service implementing methods for parsing HTML to PDF using XHTMLRenderer Flying Saucer.
 * @author Svitlana Tkachuk
 */

@Service
public class PdfGeneratorImpl implements PdfGenerator {

    @Override
    public File generatePdfFromHtml(String html) throws IOException, DocumentException {
        File file = new File("thymeleaf.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        return file;
    }

    @Override
    public String parseThymeleafTemplate(UserDto userDto) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("firstName", userDto.getFirstName());
        context.setVariable("lastName", userDto.getLastName());
        context.setVariable("experience", userDto.getExperiences());
        context.setVariable("photo", userDto.getPhoto());
        return templateEngine.process("thymeleaf_template", context);
    }

}
