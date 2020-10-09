package com.sc.attachment;

import com.sc.attachment.entity.Attachment;
import com.sc.attachment.repository.AttachmentRestRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    public AttachmentRestRepository repository;

    @GetMapping("/download/{id}")
    @ResponseBody
    public void serveFile(@PathVariable String id, HttpServletResponse response) throws IOException, SQLException {
        Optional<Attachment> attachment = repository.findById(id);
        if (attachment.isPresent()) {
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.get().getName() + "\"");
            Blob blob = attachment.get().getContent();
            IOUtils.copy(blob.getBinaryStream(), response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
}

@Controller
class GreetingController {
    @Autowired
    public AttachmentRestRepository repository;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes,
                                   Model model) throws IOException, SQLException {
        Attachment attachment = new Attachment();
        Blob blob = new SerialBlob(file.getBytes());
        attachment.setContent(blob);
        attachment.setName(file.getOriginalFilename());
        repository.save(attachment);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        fileList(model);
        model.addAttribute("name", "test");
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String handleFileUpload(@PathVariable String id,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        Optional<Attachment> attachment = repository.findById(id);
        repository.delete(attachment.get());
        redirectAttributes.addFlashAttribute("message",
                "You successfully deleted " + id + "!");
        fileList(model);
        return "index";
    }

    private void fileList(Model model) {
        List al = new ArrayList();
        repository.findAll().iterator().forEachRemaining(attachment1 -> {
            al.add(attachment1);
            System.out.println(attachment1.getId());
        });
        model.addAttribute("files", al);
    }
}