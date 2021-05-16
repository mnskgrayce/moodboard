package com.example.springenterprise.bootstrap;

import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.repositories.ImageRepository;
import com.example.springenterprise.repositories.MoodboardRepository;
import com.example.springenterprise.user.AppUser;
import com.example.springenterprise.user.AppUserRepository;
import com.example.springenterprise.user.AppUserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

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
    public void run(String... args) {
        // add sample data here if any
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

        Moodboard moodboard1 = new Moodboard(
                "https://images.unsplash.com/photo-1593642532009-6ba71e22f468?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
                new HashSet<>()
        );
        moodboard1.setAppUser(trang);
        moodboardRepository.save(moodboard1);

        Moodboard moodboard2 = new Moodboard(
                "Long's Moodboard",
                new HashSet<>()
        );
        moodboard2.setAppUser(lon);
        moodboardRepository.save(moodboard2);

        System.out.println("Total number of users: " + appUserRepository.count());
        System.out.println("Total number of moodboard: " + moodboardRepository.count());
    }
}
