package uz.mod.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mod.entity.Image;
import uz.mod.payload.Result;
import uz.mod.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/image")
public class ImageController {

    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping("/")
    public Image postImage(@RequestBody(required = true) MultipartFile multipartFile) {
        return fileStorageService.saveImageFile(multipartFile);
    }

    @GetMapping("/{id}")
    public Image getImage(@PathVariable UUID id) {
        return fileStorageService.getImage(id);
    }

    @GetMapping("/")
    public Page<Image> getImagePage(@RequestParam int page, @RequestParam int size) {
        return fileStorageService.findAllImage(page, size);
    }

    @PutMapping("/edit/{id}")
    public Image editImage(@PathVariable UUID id, @RequestBody(required = true) MultipartFile multipartFile) {

        ////////////////////////////
        Image image = fileStorageService.saveImageFile(multipartFile);
        return fileStorageService.editImage(id, image);
    }

    @DeleteMapping("/delete/{id}")////////////////////
    public Result deletePostBook(@PathVariable UUID id) {
        Image pdf = fileStorageService.getImage(id);
        fileStorageService.deleteImage(id);
        return new Result(fileStorageService.deleteFile(pdf.getFileName()));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadImageById(@PathVariable UUID id, HttpServletRequest httpServletRequest) {
        Image image = fileStorageService.getImage(id);
        return fileStorageService.downloadFileToApp(image.getFileName(), httpServletRequest);
    }
    @GetMapping("/download/fileName/{fileName:.+}")
    public ResponseEntity<Resource> downloadImageByName(@PathVariable String fileName, HttpServletRequest response
    ) {
        Image image = fileStorageService.getImageByName(fileName);
        return fileStorageService.downloadFileToApp(image.getFileName(), response);
    }

}
