package com.example.springenterprise.repositories;

import com.example.springenterprise.models.Moodboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodboardRepository extends JpaRepository<Moodboard, Long> {
}
