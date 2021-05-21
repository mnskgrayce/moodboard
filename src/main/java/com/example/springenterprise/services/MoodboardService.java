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
    }

    // Get a moodboard from the database
    public Moodboard getMoodboard(long id) {
        Optional<Moodboard> moodboardOptional = moodboardRepository.findById(id);

        if (moodboardOptional.isEmpty()) {
            throw new IllegalStateException("Moodboard with ID " + id + " is not found!");
        }

        return moodboardOptional.get();
    }

    // Get an image from the database
    public Image getImage(long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);

        if (imageOptional.isEmpty()) {
            throw new IllegalStateException("Image with ID " + id + " is not found!");
        }

        return imageOptional.get();
    }

    // Delete a moodboard from the database
    public void deleteMoodboard(long id) {
        Moodboard moodboard = getMoodboard(id);

        // First, remove all images from the current moodboard
        // Beware of ConcurrentModification error and JPA key violation!
        for (Image image : moodboard.getImages()) {
            image.getMoodboards().remove(moodboard);
            imageRepository.save(image);
        }

        // Then delete the moodboard
        moodboardRepository.deleteById(id);

        // Purge stray images after the moodboard is deleted
        deleteAllStrayImages();
    }

    // Delete an image from the moodboard
    public void deleteImageFromMoodboard(Long mid, Long iid) {
        Moodboard moodboard = getMoodboard(mid);
        Image image = getImage(iid);

        // Remove moodboard and image from each other
        moodboard.getImages().remove(image);
        moodboardRepository.save(moodboard);
        image.getMoodboards().remove(moodboard);

        // Delete the image from database if not in any moodboard
        if (image.getMoodboards().isEmpty()) {
            imageRepository.deleteById(iid);
        } else {
            imageRepository.save(image);
        }
    }

    // Purge all images without a moodboard
    public void deleteAllStrayImages() {
        List<Image> images = imageRepository.findAll();

        for (Image image : images) {
            if (image.getMoodboards().isEmpty()) {
                imageRepository.deleteById(image.getId());
            }
        }
    }
}
