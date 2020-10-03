package uz.mod.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Category;
import uz.mod.entity.PostBook;
import uz.mod.entity.PostConception;

import java.util.List;
import java.util.UUID;

public interface PostBookRepo extends JpaRepository<PostBook, UUID> {

    List<PostBook>getAllByIsFavouriteOrderByCreatedByDesc(Boolean isFavourite);

    Page<PostBook> findAllByIsEnabled(Boolean isEnabled, PageRequest pageRequest);


}
