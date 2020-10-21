package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.mod.entity.Connector;
import uz.mod.entity.Details;

import java.util.List;
import java.util.UUID;

public interface DetailRepo extends JpaRepository<Details, UUID> {

        List<Details>findAllByConnector(Connector connector);
}
