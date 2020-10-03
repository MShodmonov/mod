package uz.mod.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.mod.entity.Book;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.repository.BookRepo;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;


    public Book save(Book book){
        try {
            return bookRepo.save(book);
        }catch (Exception e){
            throw new PersistenceException("persistence failed",e.getCause());
        }
    }
    public Book edit(UUID id,Book book){
            Book bookByRepo = bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This book does not exist"));
            bookByRepo.setDescription(book.getDescription());
            bookByRepo.setImage(book.getImage());
            bookByRepo.setNameRu(book.getNameRu());
            bookByRepo.setNameUz(book.getNameUz());
            bookByRepo.setPdf(book.getPdf());

        return bookRepo.save(bookByRepo);
    }

    public Page<Book>findAll(int page, int size){
        PageRequest of = PageRequest.of(page, size);
        return bookRepo.findAll(of);
    }

    public Book findById(UUID id){
        return bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This book does not exist"));
    }

    public Boolean delete(UUID uuid){
        try {
            bookRepo.deleteById(uuid);
            return true;
        }catch (Exception e) {
            throw new ResourceNotFoundException("This book does not exist",e.getCause());
        }
    }
}