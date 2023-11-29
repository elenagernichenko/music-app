package com.itis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    private int id;
    private String name;
    private String artist;
    private String album;
    private String year;
    private String filename;
    private Integer popularity;

    public Track(String name, String artist, String album, String year, String filename) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.filename = filename;
    }
}
