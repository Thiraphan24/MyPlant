package com.stou.example.thymeleaf.springapp3_myplant;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUpload fileUpload;

    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image")MultipartFile multipartFile,
                             Model model) throws IOException {
        String imageURL = fileUpload.uploadFile(multipartFile);
        model.addAttribute("imageURL",imageURL);
        return "index";
    }
}

