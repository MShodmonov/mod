package uz.mod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mod.entity.Connector;
import uz.mod.entity.Subject;

import java.util.List;
import java.util.UUID;

public interface ConnectorRepo extends JpaRepository<Connector, UUID> {

    List<Connector>findAllByConception_IdAndSubject_Id(UUID conceptionId,UUID subjectId);

    List<Connector>findAllBySubject(Subject subject);
}
