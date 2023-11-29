package com.itis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private Integer id;
    private String title;
    private String content;
    private String date;

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
