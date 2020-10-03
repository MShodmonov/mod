package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.Category;
import uz.mod.payload.Result;
import uz.mod.service.CategoryService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public Category postCategory(@Valid @RequestBody Category category){
        return categoryService.save(category);
    }
    @GetMapping("/")
    public Page<Category> getCategoryPage(@RequestParam int page, @RequestParam int size){
        return categoryService.findAll(page, size);
    }
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable UUID id){
        return categoryService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public Category editCategory(@PathVariable UUID id,@RequestBody Category category){
        return categoryService.edit(id, category);
    }
    @DeleteMapping("/delete/{id}")////////////////////
    public Result deleteCategory(@PathVariable UUID id){
        return new Result(categoryService.delete(id));
    }
}
