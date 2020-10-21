package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.File;

import java.util.List;
import java.util.UUID;

public interface FileRepo extends JpaRepository<File, UUID> {

    List<File> findAllByIsFavouriteTrueOrderByCreatedAtDesc();

}
