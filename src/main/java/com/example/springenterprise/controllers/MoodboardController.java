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

    // Add a picture to a moodboard and redirect to homepage
    @RequestMapping(value = "/add_to_moodboards/image-id/{iid}/mb-id/{mid}", method = RequestMethod.POST)
    public String addToMoodboard(@PathVariable("iid") String iID, @PathVariable("mid") Long mID) {
        Image image = new Image(iID);
        Moodboard moodboard = moodboardService.getMoodboard(mID);
        moodboard.getImages().add(image);
        image.getMoodboards().add(moodboard);
        System.out.println("Passed ID: " + iID + " " + mID);
        return "redirect:/";
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

    @RequestMapping("/delete_image/{mid}/{iid}")
    public String deleteImageFromMoodboard( @PathVariable(value = "mid") Long mid, @PathVariable(value = "iid") String iid) {
        moodboardService.deleteImageFromMoodboard(mid, iid);
        return "redirect:/moodboard/edit/{mid}";
    }
}
