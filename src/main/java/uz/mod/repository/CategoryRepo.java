package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Category;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
}
