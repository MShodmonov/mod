package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mod.entity.PostResource;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.payload.PostCount;
import uz.mod.payload.PostResourcePayload;
import uz.mod.repository.*;

import java.util.List;
import java.util.UUID;

@Service
public class PostResourceService {
    @Autowired
    private PostResourceRepo postResourceRepo;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private RegionRepo regionRepo;



    public PostResourcePayload save(PostResourcePayload model) {
        try {
            PostResource postResource = new PostResource();
            postResource.setIsFavourite(false);
            postResource.setIsEnabled(false);
            postResource.setDescription(model.getDescription());
            postResource.setFullName(model.getFullName());
            postResource.setDistrict(districtRepo.findById(model.getDistrictId()).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with this district")));
            postResource.setSchoolName(model.getSchoolName());
            postResource.setRegion(regionRepo.findById(model.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with the region")));
            postResource.setFile(fileRepo.findById(model.getFileId()).orElseThrow(() -> new ResourceNotFoundException("This Post for detail does not exist with the region")));
            return new PostResourcePayload(postResourceRepo.save(postResource));
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public PostResource edit(UUID id, PostResourcePayload model) {
        PostResource postResource = postResourceRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist"));
        postResource.setDescription(model.getDescription());
        postResource.setFullName(model.getFullName());
        postResource.setSchoolName(model.getSchoolName());
        postResource.setDistrict(districtRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with this district")));
        postResource.setRegion(regionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for conception does not exist with the region")));

        return postResourceRepo.save(postResource);
    }

    public Page<PostResource> findAll(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return postResourceRepo.findAll(of);
    }

    public PostResource findById(UUID id) {
        return postResourceRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This post for conception does not exist"));
    }

    public Boolean delete(UUID uuid) {
        try {
            postResourceRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist", e.getCause());
        }

    }

    public boolean setEnable(UUID uuid) {
        try {
            PostResource postResource = postResourceRepo.findById(uuid).get();
            postResource.setIsFavourite(true);
            postResourceRepo.save(postResource);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist", e.getCause());
        }
    }

    public Page<PostResource> getNewPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postResourceRepo.findAllByIsEnabled(false, pageRequest);
    }

    public Long getPostCount() {
        return postResourceRepo.count();
    }

    public Page<PostResource> getAcceptedPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postResourceRepo.findAllByIsEnabled(true, pageRequest);
    }

    public List<PostResource> getFavourite() {
        return postResourceRepo.findAllByIsFavouriteTrue();
    }

    public boolean setFavourite(UUID uuid) {
        try {
            PostResource postResource = postResourceRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This post for conception does not exist"));
            postResource.setIsFavourite(true);
            postResourceRepo.save(postResource);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist", e.getCause());
        }
    }

    public boolean setUnFavourite(UUID uuid) {
        try {
            PostResource postResource = postResourceRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This post for conception does not exist"));
            postResource.setIsFavourite(false);
            postResourceRepo.save(postResource);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for conception does not exist", e.getCause());
        }
    }

    @Transactional
    public Boolean makeEnableAll() {
        List<PostResource> allByIsEnabled = postResourceRepo.findAllByIsEnabledFalse();
        for (PostResource postResource : allByIsEnabled) {
            postResource.setIsEnabled(true);
            postResourceRepo.save(postResource);
        }
        return true;
    }

    @Transactional
    public List<PostResource> getPostCountByFileId(UUID fileId){

        return postResourceRepo.findAllByFile_Id(fileId);

    }

}
