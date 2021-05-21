package com.example.springenterprise.repositories;

import com.example.springenterprise.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByApiId(String apiId);
}
