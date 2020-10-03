package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Image;
import uz.mod.entity.Pdf;

import java.util.Optional;
import java.util.UUID;

public interface PdfRepo extends JpaRepository<Pdf, UUID> {

    Optional<Pdf> findByFileName(String fileName);
}
