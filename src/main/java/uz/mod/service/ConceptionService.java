package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mod.entity.Book;
import uz.mod.entity.Category;
import uz.mod.entity.Conception;
import uz.mod.entity.Subject;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.repository.BookRepo;
import uz.mod.repository.ConceptionRepo;
import uz.mod.repository.SubjectRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConceptionService {

    @Autowired
    private ConceptionRepo conceptionRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    public Conception save(Conception conception) {
        try {
            return conceptionRepo.save(conception);
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public Conception edit(UUID id, Conception conception) {
        Conception conceptionByRepo = conceptionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This conception does not exist"));
        conceptionByRepo.setConceptName(conception.getConceptName());
        conceptionByRepo.setConceptionNameRu(conception.getConceptionNameRu());


        return conceptionRepo.save(conceptionByRepo);
    }

    public Page<Conception> findAll(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return conceptionRepo.findAll(of);
    }

    public Conception findById(UUID id) {
        return conceptionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This conception does not exist"));
    }

    @Transactional
    public Boolean delete(UUID uuid) {
        try {
            Conception conception = conceptionRepo.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("This conception does not exist"));
            conceptionRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This conception does not exist", e.getCause());
        }
    }


}
