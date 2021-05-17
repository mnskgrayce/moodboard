package com.example.springenterprise.controllers;

import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.services.MoodboardService;
import com.example.springenterprise.user.AppUser;
import com.example.springenterprise.user.AppUserService;
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
    private final AppUserService appUserService;

    @RequestMapping(value = "/new_moodboard")
    public String createMoodboard(Model model) {
        Moodboard moodboard = new Moodboard();
        model.addAttribute("moodboard", moodboard);
        return "new_moodboard";
    }

    @RequestMapping(value = "/save_moodboard", method = RequestMethod.POST)
    public String saveNewMoodboard(@ModelAttribute("moodboard") Moodboard moodboard, Principal principal) {
        moodboard.setAppUser(appUserService.getUserByEmail(principal.getName()));
        moodboardService.saveMoodboard(moodboard);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete_moodboard", method = RequestMethod.GET)
    public String deleteMoodboard(@RequestParam(name="id") Long id) {
        moodboardService.deleteMoodboard(id);
        return "redirect:/";
    }
}
