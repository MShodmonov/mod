package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.PostBook;
import uz.mod.payload.PostBookModel;
import uz.mod.payload.Result;
import uz.mod.service.PostBookService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/post/book")
public class PostBookController {
    @Autowired
    private PostBookService postBookService;

    @PostMapping("/")
    public PostBookModel postPostBook(@Valid @RequestBody PostBookModel postBookModel) {
        return postBookService.save(postBookModel);
    }

    @GetMapping("/{id}")
    public PostBook getPostBook(@PathVariable UUID id) {
        return postBookService.findById(id);
    }

    @GetMapping("/")
    public Page<PostBook> getPostBookPage(@RequestParam int page, @RequestParam int size) {
        return postBookService.findAllEnabled(page, size);
    }

    @PutMapping("/edit/{id}")
    public PostBookModel editPostBook(@PathVariable UUID id, @RequestBody PostBookModel postBookMo) {
        return postBookService.edit(id, postBookMo);
    }

    @DeleteMapping("/delete/{id}")////////////////////
    public Result deletePostBook(@PathVariable UUID id) {
        return new Result(postBookService.delete(id));
    }

    @GetMapping("/enable/{id}")
    public Result editEnablePost(@PathVariable UUID id) {
        return new Result(postBookService.setEnable(id));
    }

    @GetMapping("/favourite/{id}")
    public Result makeFavourite(@PathVariable UUID id) {
        return new Result(postBookService.setFavourite(id));
    }

    @GetMapping("/unfavourite/{id}")
    public Result makeUnfavorable(@PathVariable UUID id) {
        return new Result(postBookService.unSetFavourite(id));
    }

    @GetMapping("/favourite")
    public List<PostBook> getPostBookPageByFavourite() {
        return postBookService.findAllByIsFavourite();
    }

    @GetMapping("/new")
    public Page<PostBook> getNewPosts(@RequestParam int page, @RequestParam int size) {
        return postBookService.getNewPosts(page, size);
    }

    @GetMapping("/count")
    public Long getNewPosts() {
        return postBookService.getPostCount();
    }

    @GetMapping("/accept/all")
    public Result acceptAllNewPost() {
        return new Result(postBookService.makeEnableAll());
    }

    @GetMapping("/count/book/{id}")
    public Long countPostByBookId(@PathVariable UUID id) {
        return postBookService.getPostCountByBook(id);
    }

    @GetMapping("/list/book/{id}")
    public Page<PostBook> getPostByBookId(@PathVariable UUID id,@RequestParam int page, @RequestParam int size) {
        return postBookService.getPostBookByBookId(id,page,size);
    }

}
