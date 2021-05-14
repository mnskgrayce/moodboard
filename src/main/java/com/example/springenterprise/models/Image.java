package com.example.springenterprise.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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
    private Set<Moodboard> moodboards;

    public Image(String apiId, Set<Moodboard> moodboards) {
        this.apiId = apiId;
        this.moodboards = moodboards;
    }
}
