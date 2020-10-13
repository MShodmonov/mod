package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.mod.entity.*;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.payload.ConnectorPayload;
import uz.mod.repository.ConceptionRepo;
import uz.mod.repository.ConnectorRepo;
import uz.mod.repository.DetailRepo;
import uz.mod.repository.SubjectRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConnectorService {

    @Autowired
    private ConnectorRepo connectorRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ConceptionRepo conceptionRepo;

    @Autowired
    private DetailRepo detailRepo;

    public Connector save(ConnectorPayload connector) {
        try {
            Conception conception = conceptionRepo.findById(connector.getConceptionId()).orElseThrow(() -> new ResourceNotFoundException("This conception does not exist"));
            Subject subject = subjectRepo.findById(connector.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("This subject does not exist"));
            Details details = detailRepo.findById(connector.getDetailId()).orElseThrow(() -> new ResourceNotFoundException("This connector does not exist"));
            Connector connector1 = new Connector(subject,details,conception);
            return connectorRepo.save(connector1);
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public Connector edit(UUID id, ConnectorPayload connector) {
        Connector connectorByRepo = connectorRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This connector does not exist"));
        Conception conception = conceptionRepo.findById(connector.getConceptionId()).orElseThrow(() -> new ResourceNotFoundException("This conception does not exist"));
        Subject subject = subjectRepo.findById(connector.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("This subject does not exist"));
        Details details = detailRepo.findById(connector.getDetailId()).orElseThrow(() -> new ResourceNotFoundException("This connector does not exist"));
        connectorByRepo.setSubject(subject);
        connectorByRepo.setDetails(details);
        connectorByRepo.setConception(conception);
        return connectorRepo.save(connectorByRepo);
    }

    public Page<Connector> findAll(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return connectorRepo.findAll(of);
    }

    public Connector findById(UUID id) {
        return connectorRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This connector does not exist"));
    }

    public Boolean delete(UUID uuid) {
        try {
            connectorRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This connector does not exist", e.getCause());
        }
    }

    public List<Connector>findAllConnectorByConceptionAndSubject(UUID conceptionId,UUID subjectId){
        return connectorRepo.findAllByConception_IdAndSubject_Id(conceptionId,subjectId);
    };
}
