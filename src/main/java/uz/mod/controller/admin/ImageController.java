package uz.mod.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mod.entity.Image;
import uz.mod.payload.Result;
import uz.mod.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/image")
public class ImageController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileController fileController;

    @PostMapping("/")
    public Image postImage(@RequestBody MultipartFile multipartFile){
       return fileStorageService.saveImageFile(multipartFile);
    }
    @GetMapping("/{id}")
    public Image getImage(@PathVariable UUID id){
        return fileStorageService.getImage(id);
    }

    @GetMapping("/")
    public Page<Image> getImagePage(@RequestParam int page, @RequestParam int size){
        return fileStorageService.findAllImage(page, size);
    }

    @PutMapping("/edit/{id}")
    public Image editImage(@PathVariable UUID id,@RequestBody MultipartFile multipartFile){

        ////////////////////////////
        Image image = fileStorageService.saveImageFile(multipartFile);
        return fileStorageService.editImage(id,image);
    }
    @DeleteMapping("/delete/{id}")////////////////////
    public Result deletePostBook(@PathVariable UUID id){
        Image pdf = fileStorageService.getImage(id);
        fileStorageService.deleteFile(pdf.getFileName());
        return new Result(fileStorageService.deleteImage(id));
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadImageById(@PathVariable UUID id, HttpServletRequest httpServletRequest){
        Image image = fileStorageService.getImage(id);
        return fileController.downloadFile(image.getFileName(),httpServletRequest);
    }


}