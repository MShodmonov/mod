package uz.mod.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mod.entity.Book;
import uz.mod.entity.Image;
import uz.mod.entity.Pdf;
import uz.mod.entity.PostBook;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.payload.DashboardInfo;
import uz.mod.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.swing.UIManager.get;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private PdfRepo pdfRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private PostBookRepo postBookRepo;

    @Autowired
    private PostConceptionRepo postConceptionRepo;


    public Book save(Book book) {
        try {
            book.setIsFavourite(false);
            return bookRepo.save(book);
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public Book edit(UUID id, Book book) {
        Book bookByRepo = bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This book does not exist"));
        bookByRepo.setDescription(book.getDescription());
        bookByRepo.setNameRu(book.getNameRu());
        bookByRepo.setNameUz(book.getNameUz());

        Optional<Image> optionalImage = imageRepo.findById(bookByRepo.getImage().getId());


        Optional<Pdf> optionalPdf = pdfRepo.findById(bookByRepo.getPdf().getId());

        bookByRepo.setImage(book.getImage());
        bookByRepo.setPdf(book.getPdf());
        Book save = bookRepo.save(bookByRepo);
        if (optionalImage.isPresent())
            if (!optionalImage.get().getId().equals(book.getImage().getId())) {
                imageRepo.delete(optionalImage.get());

            }
        if (optionalPdf.isPresent())
            if (!optionalPdf.get().getId().equals(book.getPdf().getId())) {
                pdfRepo.delete(optionalPdf.get());

            }

        return save;
    }

    public Page<Book> findAll(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return bookRepo.findAll(of);
    }

    public Book findById(UUID id) {
        return bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This book does not exist"));
    }

    public Boolean delete(UUID uuid) {
        try {
            Book book = findById(uuid);
            List<PostBook> postBookList = postBookRepo.findAllByBookOrderByCreatedAtDesc(book);
            for (PostBook postBook : postBookList) {
                postBookRepo.delete(postBook);
            }
            bookRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This book does not exist", e.getCause());
        }
    }

    public List<Book> getFavouriteBook() {
        try {
            List<Book> books = bookRepo.findAllByIsFavouriteTrueOrderByCreatedAtDesc();
                return books;
        } catch (Exception e) {
            throw new PersistenceException("There is not any favourite Book");
        }
    }

    public Boolean makeBookFavourite(UUID uuid) {
            Book book = findById(uuid);
            book.setIsFavourite(!book.getIsFavourite());
            bookRepo.save(book);
        return true;
    }

    @Transactional
    public DashboardInfo getDashboardItems() {
        long countEmail = emailRepo.count();
        Long count = bookRepo.countByIsFavouriteTrueOrderByCreatedAtDesc();
        long postBookCount = postBookRepo.count();
        long countPostConception = postConceptionRepo.count();
        long countBook = bookRepo.count();
        return new DashboardInfo(countEmail, countBook, postBookCount, countPostConception, count);

    }

    @Transactional
    public Page<Book> findAllTransactional(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return bookRepo.findAll(of);
    }
}
