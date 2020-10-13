package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.Book;
import uz.mod.entity.Category;
import uz.mod.entity.Conception;
import uz.mod.entity.Subject;
import uz.mod.payload.Result;
import uz.mod.repository.ConceptionRepo;
import uz.mod.service.ConceptionService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/conception")
public class ConceptionController {

    @Autowired
    private ConceptionService conceptionService;

    @PostMapping("/")
    public Conception postConception(@Valid @RequestBody Conception conception) {
        return conceptionService.save(conception);
    }

    @GetMapping("/")
    public Page<Conception> getConceptionPage(@RequestParam int page, @RequestParam int size) {
        return conceptionService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public Conception getConception(@PathVariable UUID id) {
        return conceptionService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public Conception editConception(@PathVariable UUID id, @RequestBody Conception conception) {
        return conceptionService.edit(id, conception);
    }

    @DeleteMapping("/delete/{id}")////////////////////
    public Result deleteConception(@PathVariable UUID id) {
        return new Result(conceptionService.delete(id));
    }




}
