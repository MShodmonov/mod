package uz.mod.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mod.controller.admin.CategoryController;
import uz.mod.controller.admin.ConceptionController;
import uz.mod.controller.admin.SubjectController;
import uz.mod.entity.*;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.payload.*;
import uz.mod.repository.DistrictRepo;
import uz.mod.repository.PdfRepo;
import uz.mod.repository.RegionRepo;
import uz.mod.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConnectorService connectorService;

    @Autowired
    private DetailService detailService;

    @Autowired
    private PostResourceService postResourceService;


    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable UUID id) {
        return bookService.findById(id);
    }

    @GetMapping("/book/")
    public Page<Book> getBookPage(@RequestParam int page, @RequestParam int size) {
        return bookService.findAllTransactional(page, size);
    }

    ////////////////////////////////////
    @GetMapping("/category/")
    public Page<Category> getCategoryPage(@RequestParam int page, @RequestParam int size) {
        return categoryService.findAll(page, size);
    }

    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable UUID id) {
        return categoryService.findById(id);
    }
    ////////////////////////////////////

    @GetMapping("/conception/")
    public Page<Conception> getConceptionPage(@RequestParam int page, @RequestParam int size) {
        return conceptionService.findAll(page, size);
    }

    @GetMapping("/conception/{id}")
    public Conception getConception(@PathVariable UUID id) {
        return conceptionService.findById(id);
    }

    ////////////////////////////////////
    @GetMapping("/connector/{id}")
    public Connector getConnector(@PathVariable UUID id) {
        return connectorService.findById(id);
    }

    @GetMapping("/connector/subject/{subjectId}/conception/{conceptionId}")
    public List<Connector> getConnectorBySubjectAndConception(@PathVariable UUID subjectId, @PathVariable UUID conceptionId) {
        return connectorService.findAllConnectorByConceptionAndSubject(conceptionId, subjectId);
    }

    /////////////////////////////////////
    @GetMapping("/details/{id}")
    public Details getDetails(@PathVariable UUID id) {
        return detailService.findById(id);
    }

    /////////////////////////////////////

    @GetMapping("/file/{id}")
    public File getFile(@PathVariable UUID id) {
        return fileStorageService.getFile(id);
    }

    @GetMapping("/file/download/{id}")
    public ResponseEntity<Resource> downloadFileById(@PathVariable UUID id, HttpServletRequest httpServletRequest) {
        File file = fileStorageService.getFile(id);
        return fileStorageService.downloadFileToApp(file.getFileName(), httpServletRequest);
    }

    /////////////////////////////////////
    @GetMapping("/image/{id}")
    public Image getImage(@PathVariable UUID id) {
        return fileStorageService.getImage(id);
    }

    @GetMapping("/image/")
    public Page<Image> getImagePage(@RequestParam int page, @RequestParam int size) {
        return fileStorageService.findAllImage(page, size);
    }

    @GetMapping("/image/download/{id}")
    public ResponseEntity<Resource> downloadImageById(@PathVariable UUID id, HttpServletRequest httpServletRequest) {
        Image image = fileStorageService.getImage(id);
        return fileStorageService.downloadFileToApp(image.getFileName(), httpServletRequest);
    }

    /////////////////////////////////////
    @GetMapping("/pdf/")
    public Page<Pdf> getPdfPage(@RequestParam int page, @RequestParam int size) {
        return fileStorageService.findAllPdf(page, size);
    }

    @GetMapping("/pdf/download/{id}")
    public ResponseEntity<InputStreamResource> downloadPdfById(@PathVariable UUID id, HttpServletResponse response) throws IOException {
//        Pdf pdf = fileStorageService.getPdf(id);
//        return fileStorageService.downloadFile(pdf.getFileName(),httpServletRequest);
        return fileStorageService.downloadToServer(id, response);
    }

    @GetMapping("/pdf/download/app/{id}")
    public ResponseEntity<Resource> downloadPdfByIdToApp(@PathVariable UUID id, HttpServletRequest httpServletRequest) {
        Pdf pdf = fileStorageService.getPdf(id);
        return fileStorageService.downloadFileToApp(pdf.getFileName(), httpServletRequest);
    }

    @GetMapping("/pdf/{id}")
    public Pdf getPdf(@PathVariable UUID id) {
        return fileStorageService.getPdf(id);
    }
    /////////////////////////////////////

    @GetMapping("/post/book/{id}")
    public PostBook getPostBook(@PathVariable UUID id) {
        return postBookService.findById(id);
    }

    @GetMapping("/post/book/")
    public Page<PostBook> getPostBookPageEnabled(@RequestParam int page, @RequestParam int size) {
        return postBookService.findAllEnabled(page, size);
    }

    @GetMapping("/post/book/favourite/{bookId}")
    public List<PostBook> getPostBookPageByFavourite(@PathVariable UUID bookId) {
        return postBookService.findAllByIsFavourite(bookId);
    }

    @PostMapping("/post/book/")
    public PostBookModel postPostBook(@Valid @RequestBody PostBookModel postBookModel) {
        return postBookService.save(postBookModel);
    }

    @GetMapping("/post/book/list/book/{id}")
    public Page<PostBook> getPostByBookId(@PathVariable UUID id, @RequestParam int page, @RequestParam int size) {
        return postBookService.getPostBookByBookId(id, page, size);
    }

    /////////////////////////////////////
    @PostMapping("/post/conception/")
    public PostConceptionModel postPostConception(@Valid @RequestBody PostConceptionModel postConceptionModel) {
        return postConceptionService.save(postConceptionModel);
    }

    @GetMapping("/post/conception/{id}")
    public PostConception getPostConception(@PathVariable UUID id) {
        return postConceptionService.findById(id);
    }

    @GetMapping("/post/conception/favourite/{detailsId}")
    public List<PostConception> getFavouritePostConceptionBYDetailsId(@PathVariable UUID detailsId) {
        return postConceptionService.getFavourite(detailsId);
    }

    @GetMapping("/post/conception/favourite")
    public List<PostConception> getFavouritePostConception() {

        List<PostConception> postConceptions = postConceptionService.getFavourite();
        if (postConceptions.size() > 7)
            return postConceptions.subList(0, 7);
        else
            return postConceptions;
    }

    @GetMapping("/post/conception/accepted")
    public Page<PostConception> getAcceptedPostConceptionPage(@RequestParam int page, @RequestParam int size) {
        return postConceptionService.getAcceptedPosts(page, size);
    }

    @GetMapping("/post/conception/details/{id}")
    public List<PostConception> getPostConceptionByDetailsId(@PathVariable UUID id) {
        return postConceptionService.getPostConceptionByDetailsId(id);
    }

    /////////////////////////////////////
    @GetMapping("/post/resource/accepted")
    public Page<PostResource> getPostResourcePageAccepted(@RequestParam int page, @RequestParam int size) {
        return postResourceService.getAcceptedPosts(page, size);
    }

    @GetMapping("/post/resource/{id}")
    public PostResource getPostResource(@PathVariable UUID id) {
        return postResourceService.findById(id);
    }

    @GetMapping("/post/resource/file/{id}")
    public Page<PostResource> getPostResourceByFileId(@PathVariable UUID id, @RequestParam int page, @RequestParam int size) {
        return postResourceService.getPostByFileId(id, page, size);
    }

    @GetMapping("/post/resource/favourite/{resourceId}")
    public List<PostResource> getFavouritePost(@PathVariable UUID resourceId) {
        return postResourceService.getFavourite(resourceId);
    }

    @PostMapping("/post/resource/")
    public PostResourcePayload postPostResource(@Valid @RequestBody PostResourcePayload payload) {
        return postResourceService.save(payload);
    }

    ///////////////////////////////////////////
    @GetMapping("/subject/{id}")
    public Subject getSubject(@PathVariable UUID id) {
        return subjectService.findById(id);
    }

    @GetMapping("/subject/")
    public Page<Subject> getSubjectPage(@RequestParam int page, @RequestParam int size) {
        return subjectService.findAll(page, size);
    }

    @GetMapping("/subject/category/{id}")
    public List<Subject> getSubjectListByCategoryId(@PathVariable UUID id) {
        Category category = categoryService.findById(id);
        return subjectService.getSubjectsByCategory(category);
    }

    @GetMapping("/subject/list")
    private List<Subject> getSubjectList() {
        return subjectService.getAllSubject();
    }
    ///////////////////////////////////////////

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

    @PostMapping("/email")
    public Email postEmail(@Valid @RequestBody Email email) {
        return emailService.save(email);
    }

    @GetMapping("/book/favourite")
    private List<Book> getFavouriteBook() {
        return bookService.getFavouriteBook();
    }

    @GetMapping("/email/unsubscribe/{id}")
    public Result sentEmailAboutBook(@PathVariable UUID id) {
        return new Result(emailService.delete(id));
    }

    @GetMapping("/post/detail/category")
    public List<PostCount> getFavouritePostConceptionPage() {

        return postConceptionService.getPostCountByCategory();
    }

}
