package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.PostBook;
import uz.mod.entity.PostConception;
import uz.mod.payload.PostConceptionModel;
import uz.mod.payload.PostCount;
import uz.mod.payload.Result;
import uz.mod.service.PostBookService;
import uz.mod.service.PostConceptionService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/post/conception")
public class PostConceptionController {

    @Autowired
    private PostConceptionService postConceptionService;

    @PostMapping("/")
    public PostConceptionModel postPostConception(@Valid @RequestBody PostConceptionModel postConceptionModel){
        return postConceptionService.save(postConceptionModel);
    }
    @GetMapping("/{id}")
    public PostConception getPostConception(@PathVariable UUID id){
        return postConceptionService.findById(id);
    }
    @GetMapping("/")
    public Page<PostConception> getPostConceptionPage(@RequestParam int page, @RequestParam int size){
        return postConceptionService.findAll(page, size);
    }
    @PutMapping("/edit/{id}")
    public PostConceptionModel editPostConception(@PathVariable UUID id,@RequestBody PostConceptionModel postConceptionModel){
        return postConceptionService.edit(id, postConceptionModel);
    }
    @DeleteMapping("/delete/{id}")////////////////////
    public Result deletePostBook(@PathVariable UUID id){
        return new Result(postConceptionService.delete(id));
    }

    @GetMapping("/enable/{id}")
    public Result editEnablePost(@PathVariable UUID id){
        return new Result(postConceptionService.setEnable(id));
    }

    @GetMapping("/new")
    public Page<PostConception> getNewPosts(@RequestParam int page, @RequestParam int size){
        return postConceptionService.getNewPosts(page,size);
    }
    @GetMapping("/count")
    public Long getNewPosts(){
        return postConceptionService.getPostCount();
    }
    @GetMapping("/count/category")
    public List<PostCount> getPostCountByCategory(){
        return postConceptionService.countByCategory();
    }
}
