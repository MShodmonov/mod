package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Category;
import uz.mod.entity.Conception;
import uz.mod.entity.Subject;

import java.util.List;
import java.util.UUID;

public interface ConceptionRepo extends JpaRepository<Conception, UUID> {

    List<Conception> getAllBySubject(Subject subject);

    List<Conception> getAllByCategory_Id(UUID categoryId);
}
