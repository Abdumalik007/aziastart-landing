package com.azia.landing.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Title must not be blank")
    private String title;
    @NotBlank(message = "Content must not be blank")
    private String content;
    private ImageDto image;
    private LocalDate createdAt;
}
