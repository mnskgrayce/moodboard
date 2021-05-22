package com.example.springenterprise.controllers;

import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.services.MoodboardService;
import com.example.springenterprise.user.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    @PostMapping("/moodboard/{mId}/rename")
    public String renameMoodboard(@RequestBody String mName, @PathVariable("mId") Long mId) {
        moodboardService.renameMoodboard(mName, mId);
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
        model.addAttribute("moodboard", moodboardService.getMoodboard(id));
        return "edit_moodboard";
    }

    // Delete a moodboard and redirect to homepage
    @RequestMapping(value = "/delete_moodboard", method = RequestMethod.GET)
    public String deleteMoodboard(@RequestParam(name="id") Long id) {
        moodboardService.deleteMoodboard(id);
        return "redirect:/";
    }

    @RequestMapping("/delete_image/{mId}/{iId}")
    public String deleteImageFromMoodboard(@PathVariable(value = "mId") Long mid, @PathVariable(value = "iId") Long iid) {
        moodboardService.deleteImageFromMoodboard(mid, iid);
        return "redirect:/moodboard/edit/{mId}";
    }
}
