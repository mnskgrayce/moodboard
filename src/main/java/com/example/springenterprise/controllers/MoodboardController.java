package com.example.springenterprise.controllers;

import com.example.springenterprise.models.MoodboardCreationForm;
import com.example.springenterprise.services.ImageService;
import com.example.springenterprise.services.MoodboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@AllArgsConstructor
@Controller
public class MoodboardController {

    private final MoodboardService moodboardService;
    private final ImageService imageService;

    @RequestMapping(value = "/new_moodboard")
    public String createMoodboard(Model model) {
        MoodboardCreationForm moodboardForm = new MoodboardCreationForm();
        model.addAttribute("moodboardForm", moodboardForm);
        return "new_moodboard";
    }

    @RequestMapping(value = "/save_moodboard", method = RequestMethod.POST)
    public String saveNewMoodboard(@ModelAttribute("moodboardForm") MoodboardCreationForm moodboardForm, Principal principal) {
        String userEmail = principal.getName();
        moodboardService.saveNewMoodboard(moodboardForm, userEmail);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete_moodboard", method = RequestMethod.GET)
    public String deleteMoodboard(@RequestParam(name="id") Long id) {
        moodboardService.deleteMoodboard(id);
        return "redirect:/";
    }
}
