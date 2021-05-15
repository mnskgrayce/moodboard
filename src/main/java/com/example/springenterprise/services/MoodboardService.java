package com.example.springenterprise.services;

import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.repositories.MoodboardRepository;
import com.example.springenterprise.user.AppUser;
import com.example.springenterprise.user.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MoodboardService {

    private final MoodboardRepository moodboardRepository;
    private final AppUserRepository appUserRepository;

    public List<Moodboard> listAll() {
        return moodboardRepository.findAll();
    }

    public List<Moodboard> listByUser(String userEmail) {
        Optional<AppUser> userOptional = appUserRepository.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(userEmail);
        }

        Long userId = userOptional.get().getId();
        List<Moodboard> moodboards = listAll();

        return moodboards
                .stream()
                .filter(p -> p.getAppUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    public void save(Moodboard moodboard) {
        moodboardRepository.save(moodboard);
    }

    public Moodboard get(long id) {
        return moodboardRepository.findById(id).get();
    }

    public void delete(long id) {
        moodboardRepository.deleteById(id);
    }

}
