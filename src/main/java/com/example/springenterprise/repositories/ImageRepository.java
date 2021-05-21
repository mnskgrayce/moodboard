package com.example.springenterprise.repositories;

import com.example.springenterprise.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByApiId(String apiId);

    void deleteByApiId(String apiId);
}
