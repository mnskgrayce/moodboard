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
    private String url;

    @ManyToMany
    @JoinTable(
            name = "moodboard_image",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "moodboard_id"))
    private Set<Moodboard> moodboards;

    public Image(String apiId, String url, Set<Moodboard> moodboards) {
        this.apiId = apiId;
        this.url = url;
        this.moodboards = moodboards;
    }
}
