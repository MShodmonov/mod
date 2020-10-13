package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.District;
import uz.mod.entity.Region;

import java.util.List;
import java.util.UUID;

public interface DistrictRepo extends JpaRepository<District, UUID> {

    List<District> getAllByRegion(Region region);
}
