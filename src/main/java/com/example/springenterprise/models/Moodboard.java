package com.example.springenterprise.models;

import com.example.springenterprise.user.AppUser;
import lombok.*;

import javax.persistence.*;
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

    @ManyToMany(mappedBy = "moodboards")
    private Set<Image> images;

    @ManyToOne
    private AppUser appUser;

    public Moodboard(String name, Set<Image> images) {
        this.name = name;
        this.images = images;
    }
}
