package uz.mod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.mod.entity.Book;
import uz.mod.entity.Email;
import uz.mod.exceptions.PersistenceException;
import uz.mod.exceptions.ResourceNotFoundException;
import uz.mod.repository.BookRepo;
import uz.mod.repository.EmailRepo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    public JavaMailSenderImpl javaMailSender;

    @Value("${app.host}")
    private String host;

    public Email save(Email email) {
        try {
            Email save = emailRepo.save(email);
            try {
                sendWelcomeEmail(save);
            }catch (Exception e){
                e.getCause();
            }
            return save;
        } catch (Exception e) {
            throw new PersistenceException("persistence failed", e.getCause());
        }
    }

    public Email edit(UUID id, Email email) {
        Email emailByRepo = emailRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This email does not exist"));
        emailByRepo.setFullName(email.getFullName());
        return emailRepo.save(emailByRepo);
    }

    public Page<Email> findAll(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return emailRepo.findAll(of);
    }

    public Email findById(UUID id) {
        return emailRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("This email does not exist"));
    }

    public Boolean delete(UUID uuid) {
        try {
            emailRepo.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("This email does not exist", e.getCause());
        }
    }

    public void sendWelcomeEmail(Email email) {
        String fullUrl = host + "/api/user/email/unsubscribe/" + email.getId();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(email.getEmail());
            helper.setSubject("Registratsiya");

            helper.setText(
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                            "<head>" +
                            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
                            "    <title>Elektron manzilingiz ro`yhatdan o`tkazish</title>\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>" +
                            "</head>" +
                            "<body style=\"margin: 0; padding: 0;\">" +
                            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" +
                            "    <tr>" +
                            "        <td style=\"padding: 10px 0 30px 0;\">" +
                            "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">" +
                            "                <tr>" +
                            "                    <td align=\"center\" bgcolor=\"#70bbd9\" style=\"padding: 40px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\">" +
                            "                        <img src=\"https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/h1.gif\" alt=\"Creating Email Magic\" width=\"300\" height=\"230\" style=\"display: block;\" />" +
                            "                    </td>" +
                            "                </tr>" +
                            "                <tr>" +
                            "                    <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">" +
                            "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" +
                            "                            <tr>" +
                            "                                <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px;\">" +
                            "                                    <b>Milliy Oquv Dasturi</b>" +
                            "                                </td>" +
                            "                            </tr>" +
                            "                            <tr>" +
                            "                                <td style=\"padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">" +
                            "                                    Salom " + email.getFullName() +
                            "                                <td style=\"padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">" +
                            "                                    Tabriklayman sizning elektron manzilingiz ro`yhatdan o`tkazildi va siz endi bizning yangi kitoblarimiz haqida yangiliklardan xabardor bo'lish imkoniyatiga egasiz" +
                            "                                </td>" +
                            "                            </tr>" +
                            "                        </table>" +
                            "                    </td>" +
                            "                </tr>" +
                            "                <tr>" +
                            "                    <td bgcolor=\"rgba(30,79,227,0.67)\" style=\"padding: 30px 30px 30px 30px;\">" +
                            "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" +
                            "                            <tr>" +
                            "                                <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"75%\">" +
                            "                                    &reg; Someone, somewhere 2013<br/>" +
                            "                                    <a href=\"" + fullUrl + "\" style=\"color: #f50404;\"><font color=\"#f50404\">Unsubscribe</font></a> to this newsletter instantly" +
                            "                                </td>" +
                            " " +
                            "                            </tr>" +
                            "                        </table>" +
                            "                    </td>" +
                            "                </tr>" +
                            "            </table>" +
                            "        </td>" +
                            "    </tr>" +
                            "</table>" +
                            "</body>" +
                            "</html>" +
                            " ", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendNewsEmail(Email email, String bookName) {

        String fullUrl = host + "/api/user/email/unsubscribe/" + email.getId();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(email.getEmail());
            helper.setSubject("Yangi Darslik Xush Xabari");

            helper.setText(
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                            "<head>" +
                            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
                            "    <title>Yangi Darslik Xush Xabari</title>\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>" +
                            "</head>" +
                            "<body style=\"margin: 0; padding: 0;\">" +
                            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" +
                            "    <tr>" +
                            "        <td style=\"padding: 10px 0 30px 0;\">" +
                            "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">" +
                            "                <tr>" +
                            "                    <td align=\"center\" bgcolor=\"#70bbd9\" style=\"padding: 40px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\">" +
                            "                        <img src=\"https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/h1.gif\" alt=\"Creating Email Magic\" width=\"300\" height=\"230\" style=\"display: block;\" />" +
                            "                    </td>" +
                            "                </tr>" +
                            "                <tr>" +
                            "                    <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">" +
                            "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" +
                            "                            <tr>" +
                            "                                <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px;\">" +
                            "                                    <b>Milliy Oquv Dasturi</b>" +
                            "                                </td>" +
                            "                            </tr>" +
                            "                            <tr>" +
                            "                                <td style=\"padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">" +
                            "                                    Salom " + email.getFullName() +
                            "                                </td>" +
                            "                             </tr>" +
                            "                              <tr>" +
                            "                                  <td style=\"padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">" +
                            "                                    Bu elektron xat yuborishdan asosiy maqsadimiz sizni yangi e'lon qilingan va Milliy O`quv Dasturi tomonidan tastiqlangan kitob: " + bookName +
                            "                                       bilan tanishtirishdan iborat. E`tiboringiz uchun rahmat." +
                            "                                    </td>" +
                            "                            </tr>" +
                            "                        </table>" +
                            "                    </td>" +
                            "                </tr>" +
                            "                <tr>" +
                            "                    <td bgcolor=\"rgba(30,79,227,0.67)\" style=\"padding: 30px 30px 30px 30px;\">" +
                            "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" +
                            "                            <tr>" +
                            "                                <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"75%\">" +
                            "                                    &reg; Someone, somewhere 2020 "  +"<br/>" +
                            "                                    <a href=\""+fullUrl+"\" style=\"color: #f50404;\"><font color=\"#f50404\">Unsubscribe</font></a> to this newsletter instantly" +
                            "                                </td>" +
                            " " +
                            "                            </tr>" +
                            "                        </table>" +
                            "                    </td>" +
                            "                </tr>" +
                            "            </table>" +
                            "        </td>" +
                            "    </tr>" +
                            "</table>" +
                            "</body>" +
                            "</html>" +
                            " ", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Boolean sentEmailAboutBook(UUID bookId){
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("This book does not exist"));
        List<Email> allEmails = emailRepo.findAll();
        for (Email email: allEmails){
            sendNewsEmail(email,book.getNameUz());
        }
        return true;
    }
}
