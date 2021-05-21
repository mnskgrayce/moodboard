package com.example.springenterprise.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiId;

    @ManyToMany
    @JoinTable(
            name = "moodboard_image",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "moodboard_id"))
    private Set<Moodboard> moodboards = new HashSet<>();

    public Image(String apiId) {
        this.apiId = apiId;
    }

    // Custom toString() to handle parsing issues
    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", apiId='" + apiId + '\'' +
                '}';
    }
}
