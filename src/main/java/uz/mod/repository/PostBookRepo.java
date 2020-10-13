package uz.mod.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Category;
import uz.mod.entity.PostBook;
import uz.mod.entity.PostConception;

import java.util.List;
import java.util.UUID;

public interface PostBookRepo extends JpaRepository<PostBook, UUID> {

    List<PostBook> getAllByIsFavouriteTrue();


    Page<PostBook> findAllByIsEnabledTrue(Pageable pageRequest);

    Page<PostBook> findAllByIsEnabledFalse(Pageable pageRequest);

    List<PostBook> findAllByIsEnabled(Boolean falseValue);

    Long countByBook_Id(UUID id);

    List<PostBook>findAllByBook_Id(UUID bookId);


}
