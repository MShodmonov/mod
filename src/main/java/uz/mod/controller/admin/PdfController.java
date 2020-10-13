package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mod.entity.Image;
import uz.mod.entity.Pdf;
import uz.mod.payload.Result;
import uz.mod.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/pdf")


public class PdfController {

    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping("/")
    public Pdf postPdf(@RequestBody(required = true) MultipartFile multipartFile) {
        return fileStorageService.savePdfFile(multipartFile);
    }

    @GetMapping("/{id}")
    public Pdf getPdf(@PathVariable UUID id) {
        return fileStorageService.getPdf(id);
    }

    @GetMapping("/")
    public Page<Pdf> getImagePage(@RequestParam int page, @RequestParam int size) {
        return fileStorageService.findAllPdf(page, size);
    }

    @PutMapping("/edit/{id}")
    public Pdf editImage(@PathVariable UUID id, @RequestBody(required = true) MultipartFile multipartFile) {

        ////////////////////////////
        Pdf pdf = fileStorageService.savePdfFile(multipartFile);
        return fileStorageService.editPdf(id, pdf);
    }

    @DeleteMapping("/delete/{id}")////////////////////
    public Result deletePostBook(@PathVariable UUID id) {
        Pdf pdf = fileStorageService.getPdf(id);
        return new Result(fileStorageService.deleteFile(pdf.getFileName()));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadPdfById(@PathVariable UUID id, HttpServletResponse response) throws IOException {
//        Pdf pdf = fileStorageService.getPdf(id);
//        return fileStorageService.downloadFile(pdf.getFileName(),httpServletRequest);
        return fileStorageService.downloadToServer(id, response);
    }

    @GetMapping("/download/app/{id}")
    public ResponseEntity<Resource> downloadPdfByIdToApp(@PathVariable UUID id, HttpServletRequest httpServletRequest) {
        Pdf pdf = fileStorageService.getPdf(id);
        return fileStorageService.downloadFileToApp(pdf.getFileName(), httpServletRequest);
    }


}
