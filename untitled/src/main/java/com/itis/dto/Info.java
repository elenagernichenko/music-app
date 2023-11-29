package com.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    private Integer trackId;
    private String trackName;
    private String createDate;
    private String playListName;
    private String userName;
}
