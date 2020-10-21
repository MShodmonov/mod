package uz.mod.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mod.entity.Category;
import uz.mod.entity.PostConception;

import java.util.List;
import java.util.UUID;

public interface PostConceptionRepo extends JpaRepository<PostConception, UUID> {

    List<PostConception> findAllByIsFavouriteTrueOrderByCreatedAtDesc();

    List<PostConception> findAllByIsFavouriteTrueAndDetails_IdOrderByCreatedAtDesc(UUID detailsId);

    Page<PostConception> findAllByIsEnabledOrderByCreatedAtDesc(Boolean isEnabled, Pageable pageable);

    List<PostConception> findAllByIsEnabledFalseOrderByCreatedAtDesc();

    @Query(value = "select count(post_conception.id) from post_conception join details d on post_conception.details_id = d.id join connector c on d.id = c.details_id join subject s on c.subject_id = s.id join category c2 on s.categories_id = c2.id where c2.id=:category_id",nativeQuery = true)
    Long countByDetailsAAndCategory(@Param("category_id") UUID categoryId);

    List<PostConception> findAllByDetails_IdOrderByCreatedAtDesc(UUID detailsId);
}
