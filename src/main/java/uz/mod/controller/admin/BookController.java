package uz.mod.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.Book;
import uz.mod.payload.Result;
import uz.mod.service.BookService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book/")
    public Book postBook(@Valid @RequestBody Book book){
        return bookService.save(book);
    }
    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable UUID id){
        return bookService.findById(id);
    }
    @GetMapping("/book/")
    public Page<Book> getBookPage(@RequestParam int page,@RequestParam int size){
        return bookService.findAll(page, size);
    }
    @PutMapping("/book/edit/{id}")
    public Book editBook(@PathVariable UUID id,@RequestBody Book book){
        return bookService.edit(id, book);
    }
    @DeleteMapping("/book/delete/{id}")////////////////////
    public Result deleteBook(@PathVariable UUID id){
        return new Result(bookService.delete(id));
    }

    @GetMapping("/favourite")
    private Book getFavouriteBook(){
        return bookService.getFavouriteSubject();
    }
    @GetMapping("/favourite/{id}")
    private Result makeFavouriteBook(@PathVariable UUID id){
        return new Result( bookService.makeBookFavourite(id));
    }
    @GetMapping("/unfavourite/{id}")
    private Result unsetFavouriteSubject(@PathVariable UUID id){
        return new Result( bookService.unmakeBookFavourite(id));
    }


}
