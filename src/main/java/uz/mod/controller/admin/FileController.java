package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mod.entity.File;
import uz.mod.payload.Result;
import uz.mod.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping("/")
    public File postFile(@RequestBody(required = true) MultipartFile multipartFile) {
        return fileStorageService.saveFile(multipartFile);
    }

    @GetMapping("/{id}")
    public File getFile(@PathVariable UUID id) {
        return fileStorageService.getFile(id);
    }

    @GetMapping("/")
    public Page<File> getFilePage(@RequestParam int page, @RequestParam int size) {
        return fileStorageService.findAllFile(page, size);
    }

    @PutMapping("/edit/{id}")
    public File editFile(@PathVariable UUID id, @RequestBody(required = true) MultipartFile multipartFile) {

        ////////////////////////////
        File file = fileStorageService.saveFile(multipartFile);
        return fileStorageService.editFile(id, file);
    }

    @DeleteMapping("/delete/{id}")////////////////////
    public Result deleteFile(@PathVariable UUID id) {
        File file = fileStorageService.getFile(id);
        return new Result(fileStorageService.deleteFile(file.getFileName()));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFileById(@PathVariable UUID id, HttpServletRequest httpServletRequest) {
        File file = fileStorageService.getFile(id);
        return fileStorageService.downloadFileToApp(file.getFileName(), httpServletRequest);
    }

}
