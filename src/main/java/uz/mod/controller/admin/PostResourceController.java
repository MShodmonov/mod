package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.Connector;
import uz.mod.entity.PostConception;
import uz.mod.entity.PostResource;
import uz.mod.payload.ConnectorPayload;
import uz.mod.payload.PostResourcePayload;
import uz.mod.payload.Result;
import uz.mod.service.ConnectorService;
import uz.mod.service.PostResourceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/admin/post/resource")
public class PostResourceController {

    @Autowired
    private PostResourceService postResourceService;

    @PostMapping("/")
    public PostResourcePayload postPostResource(@Valid @RequestBody PostResourcePayload payload) {
        return postResourceService.save(payload);
    }

    @GetMapping("/")
    public Page<PostResource> getPostResourcePage(@RequestParam int page, @RequestParam int size) {
        return postResourceService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public PostResource getPostResource(@PathVariable UUID id) {
        return postResourceService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public PostResource editPostResource(@PathVariable UUID id, @RequestBody PostResourcePayload payload) {
        return postResourceService.edit(id, payload);
    }

    @DeleteMapping("/delete/{id}")////////////////////
    public Result deletePostResource(@PathVariable UUID id) {
        return new Result(postResourceService.delete(id));
    }

    @GetMapping("/file/{id}")
    public List<PostResource> getPostResourceByFileId(@PathVariable UUID id){
        return postResourceService.getPostCountByFileId(id);
    }

    @GetMapping("/enable/{id}")
    public Result editEnablePost(@PathVariable UUID id) {
        return new Result(postResourceService.setEnable(id));
    }

    @GetMapping("/new")
    public Page<PostResource> getNewPosts(@RequestParam int page, @RequestParam int size) {
        return postResourceService.getNewPosts(page, size);
    }

    @GetMapping("/count")
    public Long getNewPosts() {
        return postResourceService.getPostCount();
    }


    @GetMapping("/accept/all")
    public Result acceptAllNewPost() {
        return new Result(postResourceService.makeEnableAll());
    }

}
