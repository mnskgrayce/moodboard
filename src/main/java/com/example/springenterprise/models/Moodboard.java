package com.example.springenterprise.models;

import com.example.springenterprise.user.AppUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private String name = "New Moodboard";
    private String dateTimeCreated =
            "Last updated: " + DateTimeFormatter
                    .ofPattern("uuuu/MM/dd HH:mm:ss")
                    .format(LocalDateTime.now());
    private String description = "This is a new moodboard!";
    private String thumbnailUrl = "https://via.placeholder.com/600x400";

    @ManyToMany(mappedBy = "moodboards")
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    private AppUser appUser;

    public Moodboard(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
