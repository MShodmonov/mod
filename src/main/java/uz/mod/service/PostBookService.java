package uz.mod.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mod.entity.Book;
import uz.mod.entity.PostBook;
import uz.mod.entity.PostConception;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.payload.PostBookModel;
import uz.mod.repository.*;

import java.util.List;
import java.util.UUID;

@Service
public class PostBookService {

    @Autowired
    private PostBookRepo postBookRepo;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private RegionRepo regionRepo;


    public PostBookModel save(PostBookModel model) {
        try {

            PostBook postBook = new PostBook();
            postBook.setIsFavourite(false);
            postBook.setIsEnabled(false);
            postBook.setDescription(model.getDescription());
            postBook.setFullName(model.getFullName());
            postBook.setDistrict(districtRepo.findById(model.getDistrictId()).orElseThrow(() -> new ResourceNotFoundException("This Post for Book does not exist with this district")));
            postBook.setSchoolName(model.getSchoolName());
            postBook.setRegion(regionRepo.findById(model.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("This Book for Book does not exist with the region")));
            postBook.setBook(bookRepo.findById(model.getBookId()).orElseThrow(() -> new ResourceNotFoundException("This Book for Book does not exist with the book id")));
            return new PostBookModel(postBookRepo.save(postBook));
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public PostBookModel edit(UUID id, PostBookModel model) {
        PostBook postBook = postBookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for Book does not exist"));
        postBook.setDescription(model.getDescription());
        postBook.setFullName(model.getFullName());
        postBook.setSchoolName(model.getSchoolName());
        postBook.setDistrict(districtRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for Book does not exist with this district")));
        postBook.setRegion(regionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This Post for Book does not exist with the region")));
        postBook.setBook(bookRepo.findById(model.getBookId()).orElseThrow(() -> new ResourceNotFoundException("This Book for Book does not exist with the book id")));
        ;


        return new PostBookModel(postBookRepo.save(postBook));
    }

    public Page<PostBook> findAll(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return postBookRepo.findAll(of);
    }

    public Page<PostBook> findAllEnabled(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postBookRepo.findAllByIsEnabledTrue(pageRequest);
    }

    public PostBook findById(UUID id) {
        return postBookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This post for book does not exist"));
    }

    public Boolean delete(UUID uuid) {
        try {
            postBookRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for book does not exist", e.getCause());
        }
    }

    public boolean setEnable(UUID uuid) {
        try {
            PostBook postBook = postBookRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This post for book does not exist"));
            postBook.setIsEnabled(true);
            postBookRepo.save(postBook);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for book does not exist", e.getCause());
        }
    }

    public boolean setFavourite(UUID uuid) {
        try {
            PostBook postBook = postBookRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This post for book does not exist"));
            postBook.setIsFavourite(true);
            postBookRepo.save(postBook);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for book does not exist", e.getCause());
        }
    }

    public boolean unSetFavourite(UUID uuid) {
        try {
            PostBook postBook = postBookRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This post for book does not exist"));
            postBook.setIsFavourite(false);
            postBookRepo.save(postBook);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This post for book does not exist", e.getCause());
        }
    }

    public List<PostBook> findAllByIsFavourite() {
        return postBookRepo.getAllByIsFavouriteTrue();
    }

    public Page<PostBook> getNewPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postBookRepo.findAllByIsEnabledFalse(pageRequest);
    }

    public Long getPostCount() {
        return postBookRepo.count();
    }

    public Long getPostCountByBook(UUID bookId){
        return postBookRepo.countByBook_Id(bookId);
    }
    public List<PostBook> getPostBookByBookId(UUID bookId){
        return postBookRepo.findAllByBook_Id(bookId);
    }

    @Transactional
    public Boolean makeEnableAll() {
        List<PostBook> allByIsEnabled = postBookRepo.findAllByIsEnabled(Boolean.FALSE);
        for (PostBook postBook : allByIsEnabled) {
            postBook.setIsEnabled(true);
            postBookRepo.save(postBook);
        }
        return true;
    }
}
