package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Book;

import java.util.List;
import java.util.UUID;

public interface BookRepo extends JpaRepository<Book, UUID> {

    List<Book> findAllByIsFavouriteTrueOrderByCreatedAtDesc();
}
