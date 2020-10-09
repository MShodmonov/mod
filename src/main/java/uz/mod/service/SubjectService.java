package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.mod.entity.Category;
import uz.mod.entity.PostBook;
import uz.mod.entity.Subject;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.repository.PostBookRepo;
import uz.mod.repository.SubjectRepo;

import java.util.List;
import java.util.UUID;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepo subjectRepo;

    public Subject save(Subject subject){
        try {
            return subjectRepo.save(subject);
        }catch (Exception e){
            throw new PersistenceException("persistence failed",e.getCause());
        }
    }
    public Subject edit(UUID id, Subject subject){
        Subject subjectByRepo = subjectRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This subject does not exist"));
       subjectByRepo.setSubjectName(subject.getSubjectName());
       subjectByRepo.setSubjectNameRu(subject.getSubjectNameRu());
      // subjectByRepo.setConceptionList(subject.getConceptionList());
        return subjectRepo.save(subjectByRepo);
    }

    public Page<Subject> findAll(int page, int size){
        PageRequest of = PageRequest.of(page, size);
        return subjectRepo.findAll(of);
    }

    public Subject findById(UUID id){
        return subjectRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This subject does not exist"));
    }

    public Boolean delete(UUID uuid){
        try {
            subjectRepo.deleteById(uuid);
            return true;
        }catch (Exception e) {
            throw new ResourceNotFoundException("This subject does not exist",e.getCause());
        }
    }
    public List<Subject> getSubjectsByCategory(Category category){
        return subjectRepo.getAllByCategories(category);
    }

    public List<Subject> getAllSubject(){
       return subjectRepo.findAll();
    }
}
