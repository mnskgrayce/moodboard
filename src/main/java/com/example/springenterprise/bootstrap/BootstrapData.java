package com.example.springenterprise.bootstrap;

import com.example.springenterprise.repositories.ImageRepository;
import com.example.springenterprise.repositories.MoodboardRepository;
import com.example.springenterprise.user.AppUser;
import com.example.springenterprise.user.AppUserRepository;
import com.example.springenterprise.user.AppUserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(String... args) throws Exception {
        // add sample data here if any
        AppUser user = new AppUser(
                "Trang",
                "Nguyen",
                "mtrang1812@gmail.com",
                "1234",
                AppUserRole.ADMIN
        );

        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        appUserRepository.save(user);
    }
}
