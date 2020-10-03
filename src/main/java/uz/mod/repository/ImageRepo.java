package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Image;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepo extends JpaRepository<Image, UUID> {

    Optional<Image> findByFileName(String fileName);
}
