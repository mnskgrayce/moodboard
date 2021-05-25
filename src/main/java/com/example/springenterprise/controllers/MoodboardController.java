package com.example.springenterprise.controllers;

import com.example.springenterprise.models.Image;
import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.services.MoodboardService;
import com.example.springenterprise.user.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;

// Class to handle moodboard-specific issues
@AllArgsConstructor
@Controller
public class MoodboardController {

    private final MoodboardService moodboardService;
    private final AppUserService appUserService;

    // Get moodboard creation form
    @RequestMapping(value = "/new_moodboard")
    public String createMoodboard(Model model) {
        Moodboard moodboard = new Moodboard();
        model.addAttribute("moodboard", moodboard);
        return "new_moodboard";
    }

    // Save new moodboard to database and redirect to homepage
    @RequestMapping(value = "/save_moodboard", method = RequestMethod.POST)
    public String saveNewMoodboard(@ModelAttribute("moodboard") Moodboard moodboard, Principal principal) {
        moodboard.setAppUser(appUserService.getUserByEmail(principal.getName()));
        moodboardService.saveMoodboard(moodboard);
        return "redirect:/";
    }

    // Add one image to one or more moodboards
    @PostMapping("/moodboard/add")
    public String addToMoodboard(@RequestBody String request) {
        moodboardService.parseSaveImageRequest(request);
        return "redirect:/";
    }

    // Rename moodboard
    @PostMapping("/moodboard/rename/{mId}")
    public String renameMoodboard(@ModelAttribute Moodboard moodboard, @PathVariable("mId") Long mId) {
        moodboardService.renameMoodboard(moodboard, mId);
        return "redirect:/moodboard/edit/{mId}";
    }

    // Show picture detail page (+ list of moodboards to save to)
    @RequestMapping("/pic")
    public String getPicView(Model model, Principal principal) {
        String userEmail = principal.getName();
        model.addAttribute("moodboards", moodboardService.listByUser(userEmail));
        return "pic";
    }

    // Get the moodboard detail page
    @RequestMapping(value= "/moodboard/edit/{id}", method = RequestMethod.GET)
    public String editMoodboard(@PathVariable("id") Long id, Model model) {
        Moodboard moodboard = moodboardService.getMoodboard(id);
        model.addAttribute("moodboard", moodboard);
        model.addAttribute("images", moodboard.getImages());
        model.addAttribute("byId", Comparator.comparing(Image::getId));
        return "edit_moodboard";
    }

    // Delete a moodboard and redirect to homepage
    @GetMapping(value = "/delete_moodboard/{id}")
    public String deleteMoodboard(@PathVariable(value="id") Long id) {
        moodboardService.deleteMoodboard(id);
        return "redirect:/";
    }

    // Delete an image from moodboard
    @RequestMapping("/delete_image/{mId}/{apiId}")
    public String deleteImageFromMoodboard(@PathVariable(value = "mId") Long mId, @PathVariable(value = "apiId") String apiId) {
        moodboardService.deleteImageFromMoodboard(mId, apiId);
        return "redirect:/moodboard/edit/{mId}";
    }

    // Set an image as the moodboard thumbnail
    @RequestMapping("/set_thumbnail/{mId}/{apiId}")
    public String setImageAsMoodboardThumbnail(@PathVariable(value = "mId") Long mId, @PathVariable(value = "apiId") String apiId) {
        moodboardService.setImageAsMoodboardThumbnail(mId, apiId);
        return "redirect:/moodboard/edit/{mId}";
    }
}
