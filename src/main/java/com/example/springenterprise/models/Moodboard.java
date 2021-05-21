package com.example.springenterprise.models;

import com.example.springenterprise.user.AppUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Moodboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name = "Untitled";
    private String dateTimeCreated = "Last updated: "
            + DateTimeFormatter
            .ofPattern("uuuu/MM/dd HH:mm:ss")
            .format(LocalDateTime.now());
    private String description = "";
    private String thumbnailId = Arrays.asList(
            "5TK1F5VfdIk",
            "ineC_oi7NHs",
            "fT49QnFucQ8",
            "c-8C2Tl97jQ",
            "TeCdlzOUneQ",
            "B6sCxQzDQqE"
            )
            .get(new Random().nextInt(6));

    @ManyToMany(mappedBy = "moodboards")
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    private AppUser appUser;

    // Custom toString() to handle parsing issues
    @Override
    public String toString() {
        return "Moodboard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", thumbnailId='" + thumbnailId + '\'' +
                ", images=" + images +
                '}';
    }

}
