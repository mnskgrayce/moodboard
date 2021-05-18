package com.example.springenterprise.bootstrap;

import com.example.springenterprise.models.Image;
import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.repositories.ImageRepository;
import com.example.springenterprise.repositories.MoodboardRepository;
import com.example.springenterprise.user.AppUser;
import com.example.springenterprise.user.AppUserRepository;
import com.example.springenterprise.user.AppUserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    public BootstrapData(ImageRepository imageRepository, MoodboardRepository moodboardRepository, AppUserRepository appUserRepository) {
        this.imageRepository = imageRepository;
        this.moodboardRepository = moodboardRepository;
        this.appUserRepository = appUserRepository;
    }

    private final ImageRepository imageRepository;
    private final MoodboardRepository moodboardRepository;
    private final AppUserRepository appUserRepository;

    // Insert sample data to JPA
    @Override
    public void run(String... args) {
        // Add two sample users Trang and Long
        AppUser trang = new AppUser(
                "Trang",
                "Nguyen",
                "mtrang1812@gmail.com",
                "1234",
                AppUserRole.ADMIN
        );

        String encodedPassword = new BCryptPasswordEncoder().encode(trang.getPassword());
        trang.setPassword(encodedPassword);
        appUserRepository.save(trang);

        AppUser lon = new AppUser(
                "Long",
                "Tran",
                "longtran2378@gmail.com",
                "YrS526nfFs",
                AppUserRole.ADMIN
        );

        encodedPassword = new BCryptPasswordEncoder().encode(lon.getPassword());
        lon.setPassword(encodedPassword);
        appUserRepository.save(lon);

        Moodboard moodboard = new Moodboard();
        Image image = new Image("5TK1F5VfdIk");
        moodboard.getImages().add(image);
        image.getMoodboards().add(moodboard);

        moodboardRepository.save(moodboard);
        imageRepository.save(image);

        System.out.println("Total number of users: " + appUserRepository.count());
        System.out.println("Total number of moodboards: " + moodboardRepository.count());
        System.out.println("Total number of images: " + imageRepository.count());
    }
}
