package com.example.springenterprise.services;

import com.example.springenterprise.models.Image;
import com.example.springenterprise.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> listAll() {
        return imageRepository.findAll();
    }
}
