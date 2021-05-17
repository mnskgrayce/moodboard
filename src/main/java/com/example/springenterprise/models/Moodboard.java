package com.example.springenterprise.models;

import com.example.springenterprise.user.AppUser;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Moodboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String dateTimeCreated;
    private String description;
    private String thumbnailId;

    @ManyToMany(mappedBy = "moodboards")
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    private AppUser appUser;

    public Moodboard(String name, String dateTimeCreated, String description, String thumbnailId) {
        this.name = name;
        this.dateTimeCreated = dateTimeCreated;
        this.description = description;
        this.thumbnailId = thumbnailId;
    }
}
