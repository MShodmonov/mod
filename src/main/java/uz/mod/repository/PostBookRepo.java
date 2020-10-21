package uz.mod.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Book;
import uz.mod.entity.Category;
import uz.mod.entity.PostBook;
import uz.mod.entity.PostConception;

import java.util.List;
import java.util.UUID;

public interface PostBookRepo extends JpaRepository<PostBook, UUID> {

    List<PostBook> getAllByIsFavouriteTrueAndBook_IdOrderByCreatedAtDesc(UUID bookId);

    List<PostBook> getAllByIsFavouriteTrueOrderByCreatedAtDesc();

    Page<PostBook> findAllByIsEnabledTrueOrderByCreatedAtDesc(Pageable pageRequest);

    Page<PostBook> findAllByIsEnabledFalseOrderByCreatedAtDesc(Pageable pageRequest);

    List<PostBook> findAllByIsEnabledOrderByCreatedAtDesc(Boolean falseValue);

    Long countByBook_Id(UUID id);

    Page<PostBook>findAllByBook_IdOrderByCreatedAtDesc(UUID bookId,Pageable pageable);

    List<PostBook>findAllByBookOrderByCreatedAtDesc(Book book);




}
