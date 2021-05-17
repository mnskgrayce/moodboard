package com.example.springenterprise.services;

import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.repositories.MoodboardRepository;
import com.example.springenterprise.user.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MoodboardService {

    private final MoodboardRepository moodboardRepository;
    private final AppUserService appUserService;

    public List<Moodboard> listAll() {
        return moodboardRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTimeCreated"));
    }

    public List<Moodboard> listByUser(String userEmail) {
        List<Moodboard> moodboards = listAll();

        return moodboards
                .stream()
                .filter(p -> p.getAppUser().getEmail().equals(userEmail))
                .collect(Collectors.toList());
    }

    public void saveMoodboard(Moodboard moodboard, String userEmail) {
        moodboard.setAppUser(appUserService.getUserByEmail(userEmail));
        moodboardRepository.save(moodboard);
    }

    public Moodboard getMoodboard(long id) {
        Optional<Moodboard> moodboardOptional = moodboardRepository.findById(id);

        if (moodboardOptional.isEmpty()) {
            throw new IllegalStateException("Moodboard with ID " + id + " is not found!");
        }

        return moodboardOptional.get();
    }

    public void deleteMoodboard(long id) {
        moodboardRepository.deleteById(id);
    }
}