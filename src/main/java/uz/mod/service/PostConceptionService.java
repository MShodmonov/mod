package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mod.entity.Category;
import uz.mod.entity.PostBook;
import uz.mod.entity.PostConception;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.payload.PostConceptionModel;
import uz.mod.payload.PostCount;
import uz.mod.repository.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class PostConceptionService {

    @Autowired
    private PostConceptionRepo postConceptionRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private ConceptionRepo conceptionRepo;

    @Autowired
    private RegionRepo regionRepo;

    public PostConceptionModel save(PostConceptionModel model){
        try {
            PostConception postConception = new PostConception();
            postConception.setIsFavourite(model.getIsFavourite());
            postConception.setIsEnabled(model.getIsEnabled());
            postConception.setDescription(model.getDescription());
            postConception.setFullName(model.getFullName());
            postConception.setDistrict(districtRepo.findById(model.getDistrictId()).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with this district")));
            postConception.setSchoolName(model.getSchoolName());
            postConception.setRegion(regionRepo.findById(model.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with the region")));
            postConception.setConception(conceptionRepo.findById(model.getConceptionId()).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with the region")));
            return new PostConceptionModel(postConceptionRepo.save(postConception));
        }catch (Exception e){
            throw new PersistenceException("persistence failed",e.getCause());
        }
    }
    public PostConceptionModel edit(UUID id, PostConceptionModel model){
        PostConception postConceptionByRepo = postConceptionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist"));
        postConceptionByRepo.setDescription(model.getDescription());
        postConceptionByRepo.setIsFavourite(model.getIsFavourite());
        postConceptionByRepo.setFullName(model.getFullName());
        postConceptionByRepo.setIsEnabled(model.getIsEnabled());
        postConceptionByRepo.setSchoolName(model.getSchoolName());
        postConceptionByRepo.setDistrict(districtRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with this district")));
        postConceptionByRepo.setRegion(regionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with the region")));

        return new PostConceptionModel(postConceptionRepo.save(postConceptionByRepo));
    }

    public Page<PostConception> findAll(int page, int size){
        PageRequest of = PageRequest.of(page, size);
        return postConceptionRepo.findAll(of);
    }

    public PostConception findById(UUID id){
        return postConceptionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This post for conception does not exist"));
    }

    public Boolean delete(UUID uuid){
        try {
            postConceptionRepo.deleteById(uuid);
            return true;
        }catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist",e.getCause());
        }

    }
    public boolean setEnable(UUID uuid){
        try {
            PostConception postConception = postConceptionRepo.findById(uuid).get();
            postConception.setIsFavourite(true);
            postConceptionRepo.save(postConception);
            return true;
        }catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist",e.getCause());
        }
    }
    public List<PostCount> countByCategory(){
        List<Category> categoryList = categoryRepo.findAll();
        List<PostCount> countList = new LinkedList<>();
        for ( Category category : categoryList){
            countList.add(new PostCount(category.getNameUz(),category.getNameRu(),postConceptionRepo.countByConception_Category(category),category.getId()));
        }
        return countList;
    }
    public Page<PostConception>getNewPosts(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return postConceptionRepo.findAllByIsEnabled(false,pageRequest);
    }
    public Long getPostCount(){
        return postConceptionRepo.count();
    }

    public Page<PostConception>getAcceptedPosts(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return postConceptionRepo.findAllByIsEnabled(true,pageRequest);
    }
    public List<PostConception>getFavourite(){
        return postConceptionRepo.findAllByIsFavouriteTrue();
    }

    public boolean setFavourite(UUID uuid){
        try {
            PostConception postConception = postConceptionRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This post for conception does not exist"));
            postConception.setIsFavourite(true);
            postConceptionRepo.save(postConception);
            return true;
        }catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist",e.getCause());
        }
    }
    public boolean setUnFavourite(UUID uuid){
        try {
            PostConception postConception = postConceptionRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This post for conception does not exist"));
            postConception.setIsFavourite(false);
            postConceptionRepo.save(postConception);
            return true;
        }catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist",e.getCause());
        }
    }

    @Transactional
    public Boolean makeEnableAll(){
        List<PostConception> allByIsEnabled = postConceptionRepo.findAllByIsEnabledFalse();
        for (PostConception postConception : allByIsEnabled){
            postConception.setIsEnabled(true);
            postConceptionRepo.save(postConception);
        }
        return true;
    }



}
