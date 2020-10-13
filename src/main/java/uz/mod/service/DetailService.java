package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.mod.entity.Details;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.repository.DetailRepo;

import java.util.UUID;

@Service
public class DetailService {
    @Autowired
    private DetailRepo detailRepo;

    public Details save(Details details) {
        try {
            return detailRepo.save(details);
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public Details edit(UUID id, Details details) {
        Details detailsByRepo = detailRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This deatial does not exist"));
        detailsByRepo.setTitle(details.getTitle());
        detailsByRepo.setDescription(details.getDescription());
        return detailRepo.save(details);
    }

    public Page<Details> findAll(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return detailRepo.findAll(of);
    }

    public Details findById(UUID id) {
        return detailRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This detail does not exist"));
    }

    public Boolean delete(UUID uuid) {
        try {
            detailRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This detail does not exist", e.getCause());
        }
    }
}
