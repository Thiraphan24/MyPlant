package com.stou.example.thymeleaf.springapp3_myplant;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PlantController {
    @Autowired private PlantService plantService;
    @Autowired private final FileUpload fileUpload;


    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/create")
    public RedirectView createPlant(@ModelAttribute Plant plant, @RequestParam("image") MultipartFile multipartFile, HttpSession session) throws IOException {
        // Upload image to Cloudinary
        Map<String, Object> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
        String imageURL = (String) uploadResult.get("url");

        // Set imageURL to plant
        plant.setImageFileName(imageURL);

        // Save plant to database
        plantService.addPlant(plant);

        // Return to home page
        return new RedirectView("/", true);
    }

    public PlantController(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    @GetMapping("/")
    public String getIndex(Model model, @Param("keyword") String keyword) {
        List<Plant> listPlant = plantService.listAll(keyword);
        model.addAttribute("plants",listPlant);
        return "index";
    }

    @GetMapping("/newplant")
    public String showPlantForm(Model model) {
        // สร้าง Plant และเพิ่มไปยัง Model Attribute เพื่อให้ไปแสดงบนหน้าเว็บ editplant.html
        model.addAttribute("plant", new Plant());
        return "plantform";
    }


//    @PostMapping("/addplant")
//    public String postPlantForm(@ModelAttribute("plants") Plant plant) {
//        plantService.addPlant(plant);
//        return "redirect:/";
//    }

//    @RequestMapping(value = "/creatplant/save", method = RequestMethod.POST)
//    public RedirectView savePlant(Plant plant, @RequestParam("image") MultipartFile multipartFile, HttpSession session) throws IOException {
//        // Upload file
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        plant.setImageFileName(fileName);
//        Plant addPlant = plantService.addPlant(plant);
//        String uploadDir = "user-photos/" + addPlant.getId();
//        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//
//        // Construct the URL for the uploaded image
//        String imageURL = "/user-photos/" + addPlant.getId() + "/" + fileName;
//
//        // Set imageURL as a session attribute
//        session.setAttribute("imageURL", imageURL);
//
//        // Save the imageURL to the imageFileName column in the database
//        addPlant.setImageFileName(imageURL); // Assuming imageURL is the complete URL path to the image
//        plantService.save(addPlant);
//
//        // Return view
//        return new RedirectView("/", true);
//    }



//here
    @GetMapping("/editplant/{id}")
    public String showUpdatePlantForm(@PathVariable("id") Integer id, Model model) {
        Plant plant = plantService.findPlantById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("plant",plant);
        return "updateplantform";
    }

//    @PostMapping("/updateplant/{id}")
//    public String updatePlant(@PathVariable("id") Integer id, @ModelAttribute("plants") Plant plant) {
//        plantService.save(plant);
//        return "redirect:/";
//    }
//

    @PostMapping("/updateplant/{id}")
    public String updatePlant(@PathVariable("id") Integer id, @ModelAttribute Plant plant, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Plant existingPlant = plantService.findPlantById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // Check if a new image file is uploaded
        if (!multipartFile.isEmpty()) {
            // Upload image to Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            String imageURL = (String) uploadResult.get("url");

            // Set the new imageURL to the updated plant
            existingPlant.setImageFileName(imageURL);
        }

        // Update other fields of the plant
        existingPlant.setThName(plant.getThName());
        existingPlant.setEngName(plant.getEngName());
        existingPlant.setFamily(plant.getFamily());
        existingPlant.setDescription(plant.getDescription());

        // Save the updated plant to the database
        plantService.save(existingPlant);

        return "redirect:/";
    }



    @GetMapping("/deleteplant/{id}")
    public String deletePlant(@PathVariable("id") Integer id, Model model) {
        Plant foundplant = plantService.findPlantById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid user Id:" + id));

        plantService.deletePlant(foundplant);
        return "redirect:/";
    }

    @GetMapping("/aboutme")
    public String showAboutme() {
        return "aboutme";
    }

}
