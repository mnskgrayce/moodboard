package com.example.springenterprise.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class MoodboardCreationForm {

    private String name;
    private String dateTimeCreated;
    private String description;
    private String thumbnailId;

    private final List<Image> stocks = new ArrayList<>() {
        {
            add(new Image("uanoYn1AmPs"));
            add(new Image("8Ce-NRKCq4s"));
            add(new Image("kMiare0UFcU"));
            add(new Image("X91OLW261HI"));
            add(new Image("zNU3ErDAbAw"));
            add(new Image("wQLAGv4_OYs"));
        }
    };
}
