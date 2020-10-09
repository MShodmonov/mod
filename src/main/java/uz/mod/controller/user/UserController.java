package uz.mod.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mod.controller.admin.CategoryController;
import uz.mod.controller.admin.ConceptionController;
import uz.mod.controller.admin.SubjectController;
import uz.mod.entity.*;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.payload.PostBookModel;
import uz.mod.payload.PostConceptionModel;
import uz.mod.payload.PostCount;
import uz.mod.repository.DistrictRepo;
import uz.mod.repository.PdfRepo;
import uz.mod.repository.PostConceptionRepo;
import uz.mod.repository.RegionRepo;
import uz.mod.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private PostConceptionService postConceptionService;

    @Autowired
    private PdfRepo pdfRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private SubjectController subjectController;

    @Autowired
    private ConceptionController conceptionController;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ConceptionService conceptionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostBookService postBookService;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private RegionRepo regionRepo;

    @GetMapping("/count/post")
    public List<PostCount> getPostsCount() {
        return postConceptionService.countByCategory();
    }

    @GetMapping("/download/pdf/{id}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable UUID uuid, HttpServletRequest request) {

        Pdf pdf = fileStorageService.getPdf(uuid);
        Resource resource = fileStorageService.loadFileAsResource(pdf.getFileName());
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            throw new ResourceNotFoundException("sorry but we could not determine the type of the object");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =" + resource.getFilename())
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(resource);
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable UUID id) {
        return bookService.findById(id);
    }

    @GetMapping("/image/{id}")
    public Image getImage(@PathVariable UUID id) {
        return fileStorageService.getImage(id);
    }


    @GetMapping("/book")
    public Page<Book> getBookPage(@RequestParam int page, @RequestParam int size) {
        return bookService.findAll(page, size);
    }
    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable UUID id){
        return subjectService.findById(id);
    }

    @GetMapping("/subject/category/{id}")
    List<Subject> getSubjectByCategory(@PathVariable UUID id) {
        Category category = categoryService.findById(id);
        return subjectService.getSubjectsByCategory(category);
    }

    @GetMapping("/conception/subject/{id}")
    List<Conception> getConceptionBySubject(@PathVariable(name = "id") UUID uuid) {
        return conceptionService.getConceptionBySubject(uuid);
    }

    @GetMapping("/conception/category/{id}")
    private List<Conception> getConceptionByCategoryId(@PathVariable UUID id){
        return conceptionService.getConceptionByCategoryId(id);
    }

    @PostMapping("/conception/post")
    public PostConceptionModel postPostConception(@Valid @RequestBody PostConceptionModel postConceptionModel) {
        return postConceptionService.save(postConceptionModel);
    }

    @PostMapping("/book/post")
    public PostBookModel postPostBook(@Valid @RequestBody PostBookModel postBookModel) {
        return postBookService.save(postBookModel);
    }

    @GetMapping("/post/book/favourite")
    public List<PostBook> getPostBookPageByFavourite() {
        return postBookService.findAllByIsFavourite().subList(0, 2);
    }

    @GetMapping("/district")
    public List<District> getAllDistrict() {
        return districtRepo.findAll();
    }

    @GetMapping("/district/region/{id}")
    public List<District> getDistrictByRegion(@PathVariable(name = "id") UUID uuid) {
        Optional<Region> optionalRegion = regionRepo.findById(uuid);
        if (optionalRegion.isPresent())
            return districtRepo.getAllByRegion(optionalRegion.get());
        else throw new ResourceNotFoundException("this region does not exist");
    }

    @GetMapping("/region")
    public List<Region> getAllRegions() {
        return regionRepo.findAll();
    }

    @GetMapping("/category")
    public Page<Category> getCategoryPage(@RequestParam int page, @RequestParam int size){
        return categoryService.findAll(page, size);
    }
    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable UUID id){
        return categoryService.findById(id);
    }

    @GetMapping("/subject/list")
    private List<Subject> getSubjectList(){
        return subjectService.getAllSubject();
    }


}
