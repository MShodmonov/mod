package uz.mod.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.Connector;
import uz.mod.payload.ConnectorPayload;
import uz.mod.payload.Result;
import uz.mod.service.ConnectorService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/connector")
public class ConnectorController {

    @Autowired
    private ConnectorService connectorService;

    @PostMapping("/")
    public Connector postConnector(@Valid @RequestBody ConnectorPayload connector) {
        return connectorService.save(connector);
    }

    @GetMapping("/")
    public Page<Connector> getConnectorPage(@RequestParam int page, @RequestParam int size) {
        return connectorService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public Connector getConnector(@PathVariable UUID id) {
        return connectorService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public Connector editConnector(@PathVariable UUID id, @RequestBody ConnectorPayload connector) {
        return connectorService.edit(id, connector);
    }

    @DeleteMapping("/delete/{id}")////////////////////
    public Result deleteConnector(@PathVariable UUID id) {
        return new Result(connectorService.delete(id));
    }

    @GetMapping("/subject/{subjectId}/conception/{conceptionId}")
    public List<Connector> getConnectorBySubjectAndConception(@PathVariable UUID subjectId, @PathVariable UUID conceptionId) {
        return connectorService.findAllConnectorByConceptionAndSubject(conceptionId, subjectId);
    }

    @DeleteMapping("/subject/{subjectId}")
    public Result deleteSubject(@PathVariable UUID subjectId) {
        return connectorService.deleteSubject(subjectId);
    }

}
