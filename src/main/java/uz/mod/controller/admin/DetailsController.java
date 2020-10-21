package uz.mod.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.Details;
import uz.mod.payload.Result;
import uz.mod.service.DetailService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/details")
public class DetailsController {

    @Autowired
    private DetailService detailService;

    @PostMapping("/")
    public Details postDetails(@Valid @RequestBody Details details) {
        return detailService.save(details);
    }

    @GetMapping("/")
    public Page<Details> getDetailsPage(@RequestParam int page, @RequestParam int size) {
        return detailService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public Details getDetails(@PathVariable UUID id) {
        return detailService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public Details editDetails(@PathVariable UUID id, @RequestBody Details details) {
        return detailService.edit(id, details);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteDetails(@PathVariable UUID id) {
        return new Result(detailService.delete(id));
    }
}
