package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.mod.entity.Category;
import uz.mod.entity.Image;
import uz.mod.entity.Pdf;
import uz.mod.entity.PostResource;
import uz.mod.exceptions.FileStorageException;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.repository.FileRepo;
import uz.mod.repository.ImageRepo;
import uz.mod.repository.PdfRepo;
import uz.mod.repository.PostResourceRepo;
import uz.mod.utils.FileStorageProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
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
    private FileRepo fileRepo;

    @Autowired
    private PostResourceRepo postResourceRepo;

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


    public String storeFile(MultipartFile multipartFile) {
        if (!getContentType(multipartFile.getContentType()).isEmpty()) {
            String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + "." + getContentType(multipartFile.getContentType()));
            try {
                if (fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }
                Path targetLocation = this.fileStorageLocation.resolve(fileName);

                Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            } catch (IOException e) {
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
            }
        } else throw new FileStorageException("Could not store file. Please try again!");
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName);
            UrlResource urlResource = new UrlResource(filePath.toUri());
            if (urlResource.exists()) {
                return urlResource;
            } else throw new ResourceNotFoundException("File not found " + fileName);
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("File not found " + fileName, e);
        }
    }

    public Boolean deleteFile(String name) {
        try {
            Path filePath = this.fileStorageLocation.resolve(name);
            File file = new File(filePath.toUri());
            return file.delete();
        } catch (Exception e) {
            throw new ResourceNotFoundException("File not found " + name, e);
        }
    }

    public Image saveImageFile(MultipartFile file) {
        String storeFileName = storeFile(file);

        String filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/image/download/fileName/")
                .path(storeFileName)
                .toUriString();
        return imageRepo.save(new Image(storeFileName, file.getSize(), file.getContentType(), filepath));
    }


    public Pdf savePdfFile(MultipartFile file) {
        String storeFileName = storeFile(file);

        String filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/pdf/download/")
                .path(storeFileName)
                .toUriString();
        return pdfRepo.save(new Pdf(storeFileName, file.getSize(), file.getContentType(), filepath));
    }

    public Image getImage(UUID id) {
        return imageRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("this image does not exist"));

    }

    public Image getImageByName(String fileName){
        return  imageRepo.findByFileName(fileName).orElseThrow(() -> new ResourceNotFoundException("this image does not exist"));
    }

    public Image editImage(UUID id, Image image) {
        Image imageByRepo = imageRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This image does not exist"));
        imageByRepo.setDownloadUrl(image.getDownloadUrl());
        imageByRepo.setFileName(image.getFileName());
        imageByRepo.setFileSize(image.getFileSize());
        imageByRepo.setFileType(image.getFileType());

        return imageRepo.save(imageByRepo);
    }

    public Page<Image> findAllImage(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return imageRepo.findAll(of);
    }


    public Boolean deleteImage(UUID uuid) {
        try {
            imageRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This image does not exist", e.getCause());
        }

    }


    public Pdf getPdf(UUID id) {
        return pdfRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("this pdf does not exist"));
    }


    public Pdf editPdf(UUID id, Pdf pdf) {
        Pdf pdfByRepo = pdfRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This pdf does not exist"));
        pdfByRepo.setDownloadUrl(pdf.getDownloadUrl());
        pdfByRepo.setFileName(pdf.getFileName());
        pdfByRepo.setFileSize(pdf.getFileSize());
        pdfByRepo.setFileType(pdf.getFileType());

        return pdfRepo.save(pdfByRepo);
    }

    public Page<Pdf> findAllPdf(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return pdfRepo.findAll(of);
    }


    public Boolean deletePdf(UUID uuid) {
        try {
            pdfRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This pdf does not exist", e.getCause());
        }

    }

    public ResponseEntity<Resource> downloadFile(String filename, HttpServletRequest request) {
        Resource resource = loadFileAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .contentLength(resource.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("not found");
        }


    }

    public ResponseEntity<Resource> downloadFileToApp(String filename, HttpServletRequest request) {
        Resource resource = loadFileAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                //   .header(HttpHeaders.CONTENT_DISPOSITION,"attachment: filename =\"" +resource.getFilename() +"\"")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(resource);


    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ResponseEntity<InputStreamResource> downloadToServer(UUID id, HttpServletResponse response) {
        String fromDb = getFromDb(id);
        Pdf pdf = null;
        uz.mod.entity.File file = null;
        try {
            if (fromDb.equals("pdf")) {
                pdf = pdfRepo.findById(id).get();
                return getFileInputStream(pdf.getFileName());
            }
            else if (fromDb.equals("file")) {
                file = fileRepo.findById(id).get();
                return getFileInputStream(file.getFileName());
            }
            else throw new FileNotFoundException("this file with this id does not exit");

        }catch (IOException e){
            throw new FileStorageException("Could not store file " +id+ ". Please try again!", e);
        }
    }

    public String getContentType(String mimeType) {
        switch (mimeType) {
            case "application/pdf":
                return "pdf";
            case "image/png":
                return "png";
            case "audio/mpeg":
            case "video/mpeg":
                return "mpeg";
            case "video/mp4":
                return "mp4";
            case "image/jpeg":
                return "jpeg";
            case "application/octet-stream":
                return "octet-stream";
            default:
                return "";
        }
    }

    public String getFromDb(UUID uuid) {
        Optional<Pdf> optionalPdf = pdfRepo.findById(uuid);
        Optional<uz.mod.entity.File> optionalFile = fileRepo.findById(uuid);
        if (optionalPdf.isPresent())
            return "pdf";
        else if (optionalFile.isPresent())
            return "file";
        else return "";
    }

    public ResponseEntity<InputStreamResource> getFileInputStream(String fileName) throws FileNotFoundException {
        String path = this.fileStorageLocation.resolve(fileName).toString();
        File file = new File(path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    public uz.mod.entity.File saveFile(MultipartFile file) {
        String storeFileName = storeFile(file);

        String filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/file/download/")
                .path(storeFileName)
                .toUriString();
        return fileRepo.save(new uz.mod.entity.File(false,storeFileName,file.getOriginalFilename(),file.getSize(), file.getContentType(), filepath));
    }

    public uz.mod.entity.File getFile(UUID id) {
        return fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("this file does not exist"));

    }

    public uz.mod.entity.File saveFile(uz.mod.entity.File file) {
        try {
            return fileRepo.save(file);
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public uz.mod.entity.File editFile(UUID id, uz.mod.entity.File file) {
        uz.mod.entity.File fileByRepo = fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This file does not exist"));
        file.setDownloadUrl(file.getDownloadUrl());
        fileByRepo.setFileName(file.getFileName());
        fileByRepo.setFileSize(file.getFileSize());
        fileByRepo.setFileType(file.getFileType());
        fileByRepo.setGivenName(file.getGivenName());

        return fileRepo.save(fileByRepo);
    }

    public Page<uz.mod.entity.File> findAllFile(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return fileRepo.findAll(of);
    }
    public Boolean deleteFile(UUID uuid) {
        try {
            uz.mod.entity.File file = getFile(uuid);
            List<PostResource> postResourceList = postResourceRepo.findAllByFileOrderByCreatedAtDesc(file);
            for (PostResource postResource : postResourceList){
                postResourceRepo.delete(postResource);
            }
            fileRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This file does not exist", e.getCause());
        }

    }


}
