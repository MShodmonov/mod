package uz.mod.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.PostConception;
import uz.mod.entity.Subject;
import uz.mod.payload.Result;
import uz.mod.service.PostConceptionService;
import uz.mod.service.SubjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/")
    public Subject postSubject(@Valid @RequestBody Subject subject) {
        return subjectService.save(subject);
    }

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable UUID id) {
        return subjectService.findById(id);
    }

    @GetMapping("/")
    public Page<Subject> getSubjectPage(@RequestParam int page, @RequestParam int size) {
        return subjectService.findAll(page, size);
    }

    @PutMapping("/edit/{id}")
    public Subject editSubject(@PathVariable UUID id, @RequestBody Subject subject) {
        return subjectService.edit(id, subject);
    }

    @DeleteMapping("/delete/{id}")////////////////////
    public Result deleteSubject(@PathVariable UUID id) {
        return new Result(subjectService.delete(id));
    }

    @GetMapping("/list")
    private List<Subject> getSubjectList() {
        return subjectService.getAllSubject();
    }


}
