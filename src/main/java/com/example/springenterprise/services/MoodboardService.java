package com.example.springenterprise.services;

import com.example.springenterprise.models.Image;
import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.repositories.ImageRepository;
import com.example.springenterprise.repositories.MoodboardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Implementation for Moodboard Controller's methods
@Service
@AllArgsConstructor
public class MoodboardService {

    private final MoodboardRepository moodboardRepository;
    private final ImageRepository imageRepository;

    public List<Moodboard> listAll() {
        return moodboardRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTimeCreated"));
    }

    // Filter list of moodboards by current user email
    public List<Moodboard> listByUser(String userEmail) {
        List<Moodboard> moodboards = listAll();

        return moodboards
                .stream()
                .filter(p -> p.getAppUser().getEmail().equals(userEmail))
                .collect(Collectors.toList());
    }

    // Save a moodboard to the database
    public void saveMoodboard(Moodboard moodboard) {
        if (moodboard.getName().isEmpty()) {
            moodboard.setName("Untitled");
        }
        if (moodboard.getDescription().isEmpty()) {
            moodboard.setDescription("");
        }

        moodboardRepository.save(moodboard);
        System.out.println(moodboard);
    }

    // Get a moodboard from the database
    public Moodboard getMoodboard(long id) {
        Optional<Moodboard> moodboardOptional = moodboardRepository.findById(id);

        if (moodboardOptional.isEmpty()) {
            throw new IllegalStateException("Moodboard with ID " + id + " is not found!");
        }

        return moodboardOptional.get();
    }

    // Delete a moodboard from the database
    public void deleteMoodboard(long id) {
        moodboardRepository.deleteById(id);
    }

    // Delete an image from the moodboard
    public void deleteImageFromMoodboard(Long mid, String iid) {
        Moodboard moodboard = getMoodboard(mid);
        Image image = imageRepository.findByApiId(iid);

        moodboard.getImages().remove(image);
        moodboardRepository.save(moodboard);

        image.getMoodboards().remove(moodboard);

        if (image.getMoodboards().isEmpty()) {
            imageRepository.deleteByApiId(iid);
        } else {
            imageRepository.save(image);
        }
    }
}
