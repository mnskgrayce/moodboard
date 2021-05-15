package com.example.springenterprise.services;

import com.example.springenterprise.models.Moodboard;
import com.example.springenterprise.repositories.MoodboardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MoodboardService {

    private final MoodboardRepository moodboardRepository;

    public List<Moodboard> listAll() {
        return moodboardRepository.findAll();
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
