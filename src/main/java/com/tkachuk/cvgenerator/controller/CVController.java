package com.tkachuk.cvgenerator.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.lowagie.text.DocumentException;
import com.tkachuk.cvgenerator.config.S3Config;
import com.tkachuk.cvgenerator.model.Employee;
import com.tkachuk.cvgenerator.service.impl.CVServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CVServiceImpl cvService;

    @Autowired
    public CVController(CVServiceImpl cvService) {
        this.cvService = cvService;
    }

    /**
     * This method provides endpoint for creating new employee's CV.
     * @param employee
     * @return fileName
     * @throws IOException
     * @throws DocumentException
     */
    @PostMapping("/create")
    public String createCV(@RequestBody Employee employee) throws IOException, DocumentException {
        return cvService.createCV(employee);
    }

    /**
     * This method provides endpoint for downloading CV by its id.
     * @param id is filename that is needed
     * @return downloaded file
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> getDocument(@PathVariable String id) {
        S3Object object = cvService.getCV(id);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + id + ".pdf")
                .body(new InputStreamResource(object.getObjectContent()));
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
        cvService.updateCV(employee, id);
        S3Object object = cvService.getCV(id);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + id + ".pdf")
                .body(new InputStreamResource(object.getObjectContent()));
    }

    /**
     * This method provides endpoint for deleting CV by its id.
     * @param id is filename that is needed
     * @return information about successful operation.
     */
    @DeleteMapping("/{id}")
    public String deleteDocument(@PathVariable String id) {
        cvService.deleteCV(id);
        return "Deleted Successfully";
    }
}
