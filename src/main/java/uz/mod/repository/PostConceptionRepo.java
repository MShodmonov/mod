package uz.mod.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Category;
import uz.mod.entity.PostConception;

import java.util.List;
import java.util.UUID;

public interface PostConceptionRepo extends JpaRepository<PostConception, UUID> {

    Integer countByConception_Category(Category category);

    List<PostConception>findAllByIsFavourite(Boolean isFavourite);

    Page<PostConception> findAllByIsEnabled(Boolean isEnabled, PageRequest pageRequest);
}
