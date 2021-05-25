package com.example.springenterprise.services;

import com.example.springenterprise.models.Image;
import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.repositories.ImageRepository;
import com.example.springenterprise.repositories.MoodboardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
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

        // Update the date of a moodboard
        moodboard.setDateTimeCreated(
                "Last updated: "
                + DateTimeFormatter
                .ofPattern("uuuu/MM/dd HH:mm:ss")
                .format(LocalDateTime.now()));

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

    // Rename a moodboard
    public void renameMoodboard(Moodboard moodboard, Long mId) {
        String name = moodboard.getName();
        String desc = moodboard.getDescription();

        Moodboard updated = getMoodboard(mId);
        updated.setName(name);
        updated.setDescription(desc);
        saveMoodboard(updated);
    }

    // Get an image from the database by ID
    public Image getImageById(long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);

        if (imageOptional.isEmpty()) {
            throw new IllegalStateException("Image with ID " + id + " is not found!");
        }

        return imageOptional.get();
    }

    // Get an image from the database by apiId
    public Image getImageByApiId(String apiId) {
        Optional<Image> imageOptional = imageRepository.findByApiId(apiId);

        if (imageOptional.isEmpty()) {
            throw new IllegalStateException("Image with apiID " + apiId + " is not found!");
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
    public void deleteImageFromMoodboard(Long mId, String apiId) {
        Moodboard moodboard = getMoodboard(mId);
        Image image = getImageByApiId(apiId);

        // Remove moodboard and image from each other
        moodboard.getImages().remove(image);
        saveMoodboard(moodboard);
        image.getMoodboards().remove(moodboard);

        // Delete the image from database if not in any moodboard
        if (image.getMoodboards().isEmpty()) {
            imageRepository.deleteById(image.getId());
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

    // Add image to moodboard(s) by request
    public void parseSaveImageRequest(String request) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(request, ",");
        while (tokenizer.hasMoreElements()) {
            tokens.add(tokenizer.nextToken());
        }

        // Only save if moodboards are selected
        if (tokens.size() > 0) {
            String apiId = tokens.get(0);

            try {
                // Check if image is already in database
                Image image = getImageByApiId(apiId);
                saveImageToMoodboard(tokens, image);

            } catch (IllegalStateException e) {
                // If not, create a new one
                Image image = new Image(apiId);
                saveImageToMoodboard(tokens, image);
            }
        }
    }

    private void saveImageToMoodboard(List<String> mIds, Image image) {
        for (int i = 1; i < mIds.size(); i++) {
            Moodboard moodboard = getMoodboard(Long.parseLong(mIds.get(i)));
            moodboard.getImages().add(image);
            image.getMoodboards().add(moodboard);

            saveMoodboard(moodboard);
            imageRepository.save(image);
        }
    }
}
