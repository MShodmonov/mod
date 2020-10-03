package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Region;

import java.util.UUID;

public interface RegionRepo extends JpaRepository<Region, UUID> {
}
