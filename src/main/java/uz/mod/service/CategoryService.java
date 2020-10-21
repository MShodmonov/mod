package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.mod.entity.Category;
import uz.mod.entity.Conception;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.repository.CategoryRepo;
import uz.mod.repository.ConceptionRepo;
import uz.mod.repository.SubjectRepo;

import java.net.PortUnreachableException;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    public Category save(Category category) {
        try {
            return categoryRepo.save(category);
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public Category edit(UUID id, Category category) {
        Category categoryByRepo = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This category does not exist"));
        categoryByRepo.setNameRu(category.getNameRu());
        categoryByRepo.setNameUz(category.getNameUz());
        return categoryRepo.save(categoryByRepo);
    }

    public Page<Category> findAll(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return categoryRepo.findAll(of);
    }

    public Category findById(UUID id) {
        return categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This category does not exist"));
    }

    public Boolean delete(UUID uuid) {
        try {
            categoryRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This conception does not exist", e.getCause());
        }
    }
}
