package com.tkachuk.cvgenerator.service.impl;

import com.lowagie.text.DocumentException;
import com.tkachuk.cvgenerator.model.Employee;
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
    public String parseThymeleafTemplate(Employee employee) {
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
