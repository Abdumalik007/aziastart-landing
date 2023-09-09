package com.azia.landing.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
    private LocalDate createdAt;
}
