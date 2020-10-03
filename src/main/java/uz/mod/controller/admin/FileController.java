package uz.mod.controller.admin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.mod.payload.UploadFileResponse;
import uz.mod.service.FileStorageService;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService storageService;

    @PostMapping("/upload")
    public UploadFileResponse uploadFile(@RequestParam(value = "file") MultipartFile file){
        String fileName = storageService.storeFile(file);
        UUID saveFileId = storageService.saveFile(file);
        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/download/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(saveFileId,fileName,fileDownloadUrl,file.getContentType(),file.getSize());
    }
    @PostMapping("/upload/many")
    private List<UploadFileResponse> uploadFiles(@RequestParam(value = "files") MultipartFile[] files){
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request)
    {
        Resource resource = storageService.loadFileAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException e){
            logger.info("Could not determine file extension");
        }
        if (contentType == null)
        {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                                .contentType(MediaType.parseMediaType(contentType))
//                                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment: filename =\"" +resource.getFilename() +"\"")
                                   .header(HttpHeaders.CONTENT_DISPOSITION,"inline")
                                .body(resource);

    }
}
