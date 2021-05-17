package com.example.springenterprise.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
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
}
