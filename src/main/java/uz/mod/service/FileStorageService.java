package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.mod.entity.Category;
import uz.mod.entity.Image;
import uz.mod.entity.Pdf;
import uz.mod.exceptions.FileStorageException;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.repository.ImageRepo;
import uz.mod.repository.PdfRepo;
import uz.mod.utils.FileStorageProperties;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private PdfRepo pdfRepo;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageLocation) {
        this.fileStorageLocation = Paths.get(fileStorageLocation.getUploadDir()).toAbsolutePath().normalize();
        try {
            if (!Files.exists(this.fileStorageLocation))
                Files.createDirectory(this.fileStorageLocation);


            // Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public String storeImage(MultipartFile multipartFile){

        String fileName = StringUtils.cleanPath(UUID.randomUUID().toString()+"."+multipartFile.getContentType().substring(6));
        try {
        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        Path targetLocation = this.fileStorageLocation.resolve(fileName);

        Files.copy(multipartFile.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }public String storePdf(MultipartFile multipartFile){

        String fileName = StringUtils.cleanPath(UUID.randomUUID().toString()+"."+multipartFile.getContentType().substring(12));
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            Files.copy(multipartFile.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName);
            UrlResource urlResource = new UrlResource(filePath.toUri());
            if (urlResource.exists()){
                return urlResource;
            }else throw  new ResourceNotFoundException("File not found " + fileName);
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("File not found " + fileName, e);
        }
    }
    public Boolean deleteFile(String name){
        try {
            Path filePath = this.fileStorageLocation.resolve(name);
            File file = new File(filePath.toUri());
            return file.delete();
        }catch (Exception e) {
            throw new ResourceNotFoundException("File not found " + name, e);
        }
    }

    public Image saveImageFile(MultipartFile file){
        String storeFileName = storeImage(file);

        String filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/image/download/")
                .path(storeFileName)
                .toUriString();
            return imageRepo.save(new Image(storeFileName,file.getSize(),file.getContentType(),filepath));
    }
    public Pdf savePdfFile(MultipartFile file){
        String storeFileName = storePdf(file);

        String filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/pdf/download/")
                .path(storeFileName)
                .toUriString();
        return pdfRepo.save(new Pdf(storeFileName,file.getSize(),file.getContentType(),filepath));
    }

    public Image getImage(UUID id){
       return imageRepo.findById(id).orElseThrow(() ->new ResourceNotFoundException("this image does not exist"));

    }
    public Image saveImage(Image image){
        try {
            return imageRepo.save(image);
        }catch (Exception e){
            throw new PersistenceException("persistence failed",e.getCause());
        }
    }
    public Image editImage(UUID id, Image image){
        Image imageByRepo  = imageRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This image does not exist"));
        imageByRepo.setDownloadUrl(image.getDownloadUrl());
        imageByRepo.setFileName(image.getFileName());
        imageByRepo.setFileSize(image.getFileSize());
        imageByRepo.setFileType(image.getFileType());

        return imageRepo.save(imageByRepo);
    }

    public Page<Image> findAllImage(int page, int size){
        PageRequest of = PageRequest.of(page, size);
        return imageRepo.findAll(of);
    }


    public Boolean deleteImage(UUID uuid){
        try {
            imageRepo.deleteById(uuid);
            return true;
        }catch (Exception e) {
            throw new ResourceNotFoundException("This image does not exist",e.getCause());
        }

    }




    public Pdf getPdf(UUID id){
        return pdfRepo.findById(id).orElseThrow(() ->new ResourceNotFoundException("this pdf does not exist"));
    }
    public Pdf savePdf(Pdf pdf){
        try {
            return pdfRepo.save(pdf);
        }catch (Exception e){
            throw new PersistenceException("persistence failed",e.getCause());
        }
    }
    public Pdf editPdf(UUID id, Pdf pdf){
        Pdf pdfByRepo  = pdfRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This pdf does not exist"));
        pdfByRepo.setDownloadUrl(pdf.getDownloadUrl());
        pdfByRepo.setFileName(pdf.getFileName());
        pdfByRepo.setFileSize(pdf.getFileSize());
        pdfByRepo.setFileType(pdf.getFileType());

        return pdfRepo.save(pdfByRepo);
    }

    public Page<Pdf> findAllPdf(int page, int size){
        PageRequest of = PageRequest.of(page, size);
        return pdfRepo.findAll(of);
    }


    public Boolean deletePdf(UUID uuid){
        try {
            pdfRepo.deleteById(uuid);
            return true;
        }catch (Exception e) {
            throw new ResourceNotFoundException("This pdf does not exist",e.getCause());
        }

    }

    public ResponseEntity<Resource> downloadFile( String filename, HttpServletRequest request)
    {
        Resource resource = loadFileAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException e){
            e.printStackTrace();
        }
        if (contentType == null)
        {
            contentType = "application/octet-stream";
        }

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .contentLength(resource.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("not found");
        }


    }
    public ResponseEntity<Resource> downloadFileToApp(String filename, HttpServletRequest request)
    {
        Resource resource = loadFileAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException e){
            e.printStackTrace();
        }
        if (contentType == null)
        {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                //   .header(HttpHeaders.CONTENT_DISPOSITION,"attachment: filename =\"" +resource.getFilename() +"\"")
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline")
                .body(resource);


    }


    public UUID saveFile(MultipartFile file) {
        return null;
    }
}
