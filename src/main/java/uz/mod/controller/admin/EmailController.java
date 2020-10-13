package uz.mod.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.mod.entity.Conception;
import uz.mod.entity.Email;
import uz.mod.payload.Result;
import uz.mod.service.ConceptionService;
import uz.mod.service.EmailService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/")
    public Email postEmail(@Valid @RequestBody Email email) {
        return emailService.save(email);
    }

    @GetMapping("/")
    public Page<Email> getEmailPage(@RequestParam int page, @RequestParam int size) {
        return emailService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public Email getEmail(@PathVariable UUID id) {
        return emailService.findById(id);
    }

    @GetMapping("/book/{id}")
    public Result sentEmailAboutBook(@PathVariable UUID id) {
        return new Result(emailService.sentEmailAboutBook(id));
    }

    @PutMapping("/edit/{id}")
    public Email editEmail(@PathVariable UUID id, @RequestBody Email email) {
        return emailService.edit(id, email);
    }

    @DeleteMapping("/delete/{id}")////////////////////
    public Result deleteEmail(@PathVariable UUID id) {
        return new Result(emailService.delete(id));
    }

}
