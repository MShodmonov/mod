package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Category;
import uz.mod.entity.Conception;
import uz.mod.entity.Subject;

import java.util.List;
import java.util.UUID;

public interface SubjectRepo extends JpaRepository<Subject, UUID> {


    List<Subject> getAllByCategories(Category category);

    List<Subject>getAllByConceptionList(Conception conception);

}
