package com.itis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    private Integer id;
    private String name;
    private Integer userId;

    public Playlist(String name, Integer userId) {
        this.name = name;
        this.userId = userId;
    }

}
