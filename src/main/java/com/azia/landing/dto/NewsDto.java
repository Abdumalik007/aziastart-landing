package com.azia.landing.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NewsDto {
    private Integer id;
    private String title;
    private String content;
    private ImageDto image;
}
