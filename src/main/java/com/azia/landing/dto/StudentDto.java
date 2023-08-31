package com.azia.landing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StudentDto {
    private Integer id;
    @NotBlank(message = "Full name must not be blank")
    private String fullName;
    private ImageDto image;
    private String level;
}