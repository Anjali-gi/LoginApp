package com.userlogin.controller;

import com.userlogin.entity.User;
import com.userlogin.repository.UserRepository;
import com.userlogin.service.StorageService;
import exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final UserRepository userRepository;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(UserRepository userRepository, StorageService storageService) {
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    @GetMapping("/upload")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("userId") String userId,
                                   @RequestParam("password") String password,
                                   RedirectAttributes redirectAttributes ) {
        Optional<User> userOptional = userRepository.findByUsername(userId);
        String filename="file";

        if(userOptional.isPresent()){
            StringBuffer stringBuffer=new
                    StringBuffer(userOptional.get().getPassword());


            StringBuffer stringBuffer2=new
                    StringBuffer(password);


            if(stringBuffer2.toString().equals(stringBuffer.toString())){
                filename=userId;
            }else{
                redirectAttributes.addFlashAttribute("message",
                        "Invalid credentail" + "!");

                return "redirect:/upload";
            }
        }


            storageService.store(file,filename);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");

            return "redirect:/upload";

//        }
//        return "redirect:/upload" ;



    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageException exc) {
        return ResponseEntity.notFound().build();
    }
}
