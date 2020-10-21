package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mod.entity.Category;
import uz.mod.entity.Conception;
import uz.mod.entity.PostConception;
import uz.mod.entity.Subject;
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
    private DetailRepo detailRepo;

    @Autowired
    private RegionRepo regionRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ConceptionRepo conceptionRepo;

    @Autowired
    private ConnectorRepo connectorRepo;

    public PostConceptionModel save(PostConceptionModel model) {
        try {
            PostConception postConception = new PostConception();
            postConception.setIsFavourite(false);
            postConception.setIsEnabled(false);
            postConception.setDescription(model.getDescription());
            postConception.setFullName(model.getFullName());
            postConception.setDistrict(districtRepo.findById(model.getDistrictId()).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with this district")));
            postConception.setSchoolName(model.getSchoolName());
            postConception.setRegion(regionRepo.findById(model.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with the region")));
            postConception.setDetails(detailRepo.findById(model.getDetailId()).orElseThrow(() -> new ResourceNotFoundException("This Post for detail does not exist with the region")));
            return new PostConceptionModel(postConceptionRepo.save(postConception));
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public PostConceptionModel edit(UUID id, PostConceptionModel model) {
        PostConception postConceptionByRepo = postConceptionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist"));
        postConceptionByRepo.setDescription(model.getDescription());
        postConceptionByRepo.setFullName(model.getFullName());
        postConceptionByRepo.setSchoolName(model.getSchoolName());
        postConceptionByRepo.setDistrict(districtRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with this district")));
        postConceptionByRepo.setRegion(regionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with the region")));

        return new PostConceptionModel(postConceptionRepo.save(postConceptionByRepo));
    }

    public Page<PostConception> findAll(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return postConceptionRepo.findAll(of);
    }

    public PostConception findById(UUID id) {
        return postConceptionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This post for conception does not exist"));
    }

    public Boolean delete(UUID uuid) {
        try {
            postConceptionRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist", e.getCause());
        }

    }

    public boolean setEnable(UUID uuid) {
        try {
            PostConception postConception = postConceptionRepo.findById(uuid).get();
            postConception.setIsEnabled(true);
            postConceptionRepo.save(postConception);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist", e.getCause());
        }
    }

    public Page<PostConception> getNewPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postConceptionRepo.findAllByIsEnabledOrderByCreatedAtDesc(false, pageRequest);
    }

    public Long getPostCount() {
        return postConceptionRepo.count();
    }

    public Page<PostConception> getAcceptedPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postConceptionRepo.findAllByIsEnabledOrderByCreatedAtDesc(true, pageRequest);
    }

    public List<PostConception> getFavourite() {
        return postConceptionRepo.findAllByIsFavouriteTrueOrderByCreatedAtDesc();
    }

    public List<PostConception> getFavourite(UUID detailsId) {
        return postConceptionRepo.findAllByIsFavouriteTrueAndDetails_IdOrderByCreatedAtDesc(detailsId);
    }

    public boolean setFavourite(UUID uuid) {
        try {
            PostConception postConception = postConceptionRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This post for conception does not exist"));
            postConception.setIsFavourite(true);
            postConceptionRepo.save(postConception);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist", e.getCause());
        }
    }

    public boolean setUnFavourite(UUID uuid) {
        try {
            PostConception postConception = postConceptionRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This post for conception does not exist"));
            postConception.setIsFavourite(false);
            postConceptionRepo.save(postConception);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist", e.getCause());
        }
    }

    @Transactional
    public Boolean makeEnableAll() {
        List<PostConception> allByIsEnabled = postConceptionRepo.findAllByIsEnabledFalseOrderByCreatedAtDesc();
        for (PostConception postConception : allByIsEnabled) {
            postConception.setIsEnabled(true);
            postConceptionRepo.save(postConception);
        }
        return true;
    }

    @Transactional
    public List<PostCount> getPostCountByCategory(){
        List<Category> categoryList = categoryRepo.findAll();
        List<PostCount> postCountList = new LinkedList<PostCount>();
        for (Category category: categoryList){
            Long count = postConceptionRepo.countByDetailsAAndCategory(category.getId());
            postCountList.add(new PostCount(category.getNameUz(),category.getNameRu(),Math.toIntExact(count),category.getId()));
        }
        return postCountList;
    }

    public List<PostConception> getPostConceptionByDetailsId (UUID detailsId){
        return postConceptionRepo.findAllByDetails_IdOrderByCreatedAtDesc(detailsId);
    }


}
