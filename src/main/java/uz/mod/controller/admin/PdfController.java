package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/pdf")


public class PdfController {

    @Autowired
    private FileStorageService fileStorageService;



    @PostMapping("/")
    public Pdf postPdf(@RequestBody(required = true) MultipartFile multipartFile){
        return fileStorageService.savePdfFile(multipartFile);
    }
    @GetMapping("/{id}")
    public Pdf getPdf(@PathVariable UUID id){
        return fileStorageService.getPdf(id);
    }
    @GetMapping("/")
    public Page<Pdf> getImagePage(@RequestParam int page, @RequestParam int size){
        return fileStorageService.findAllPdf(page, size);
    }
    @PutMapping("/edit/{id}")
    public Pdf editImage(@PathVariable UUID id,@RequestBody(required = true) MultipartFile multipartFile){

        ////////////////////////////
         Pdf pdf = fileStorageService.savePdfFile(multipartFile);
        return fileStorageService.editPdf(id,pdf);
    }
    @DeleteMapping("/delete/{id}")////////////////////
    public Result deletePostBook(@PathVariable UUID id){
        Pdf pdf = fileStorageService.getPdf(id);
        fileStorageService.deleteFile(pdf.getFileName());
        return new Result(fileStorageService.deletePdf(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadPdfById(@PathVariable UUID id, HttpServletRequest httpServletRequest){
        Pdf pdf = fileStorageService.getPdf(id);
        return fileStorageService.downloadFile(pdf.getFileName(),httpServletRequest);
    }


}
