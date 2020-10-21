package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.mod.entity.File;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.repository.FileRepo;

import javax.persistence.Access;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private FileRepo fileRepo;

    public List<File>getFileByFavourite(){
        return fileRepo.findAllByIsFavouriteTrueOrderByCreatedAtDesc();
    }
    public Boolean setFavourite(UUID id){
        File file = fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("this file does not exist"));
        file.setIsFavourite(!file.getIsFavourite());
        fileRepo.save(file);
        return true;
    }

    public File editFile(UUID fileId,File file){
        File fileByRepo = fileRepo.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("This file does not exist"));
        fileByRepo.setGivenName(file.getGivenName());
        return fileRepo.save(fileByRepo);
    }
}
