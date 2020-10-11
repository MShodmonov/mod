package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Email;

import java.util.UUID;

public interface EmailRepo extends JpaRepository<Email, UUID> {
}
