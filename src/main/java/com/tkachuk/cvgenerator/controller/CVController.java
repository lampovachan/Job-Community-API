package com.tkachuk.cvgenerator.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.lowagie.text.DocumentException;
import com.tkachuk.cvgenerator.config.S3Config;
import com.tkachuk.cvgenerator.model.Employee;
import com.tkachuk.cvgenerator.service.CVService;
import com.tkachuk.cvgenerator.service.impl.CVServiceImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Rest controller representing endpoints for creating, getting, updating and deleting employee's CV files.
 * @author Svitlana Tkachuk
 */

@RestController
@RequestMapping(value = "/cv")
public class CVController {

    /**
     * This method provides endpoint for creating new employee's CV.
     * @param employee
     * @return fileName
     * @throws IOException
     * @throws DocumentException
     */
    @PostMapping("/create")
    public String createCV(@RequestBody Employee employee) throws IOException, DocumentException {
        S3Config config = new S3Config();
        AmazonS3 s3 = config.configure();
        CVService service = new CVServiceImpl();
        String fileName = service.createCV(employee, s3);
        return fileName;
    }

    /**
     * This method provides endpoint for downloading CV by its id.
     * @param id is filename that is needed
     * @return downloaded file
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> getDocument(@PathVariable String id) {
        S3Config config = new S3Config();
        AmazonS3 s3 = config.configure();
        CVService service = new CVServiceImpl();
        String filename = id + ".pdf";
        S3Object object = service.getCV(s3, filename);
        S3ObjectInputStream s3is = object.getObjectContent();

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + filename)
                .body(new InputStreamResource(s3is));
    }

    /**
     * This method provides endpoint for updating CV by its id.
     * @param id is filename that is needed
     * @param employee is updated data
     * @return updated file
     * @throws IOException
     * @throws DocumentException
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<InputStreamResource> updateDocument (@PathVariable String id, @RequestBody Employee employee) throws IOException, DocumentException {
        S3Config config = new S3Config();
        AmazonS3 s3 = config.configure();
        CVService service = new CVServiceImpl();
        String fileName = id + ".pdf";
        service.updateCV(employee, s3, fileName);
        S3Object object = service.getCV(s3, fileName);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .body(new InputStreamResource(object.getObjectContent()));
    }

    /**
     * This method provides endpoint for deleting CV by its id.
     * @param id is filename that is needed
     * @return information about successful operation.
     */
    @DeleteMapping("/{id}")
    public String deleteDocument(@PathVariable String id) {
        S3Config config = new S3Config();
        AmazonS3 s3 = config.configure();
        CVService service = new CVServiceImpl();
        String fileName = id + ".pdf";
        service.deleteCV(s3, fileName);
        return "Deleted Successfully";
    }
}