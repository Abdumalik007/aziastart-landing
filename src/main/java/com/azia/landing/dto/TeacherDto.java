package com.azia.landing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeacherDto {
    private Integer id;
    @NotBlank(message = "Full name must not be blank")
    private String fullName;
    private ImageDto image;
    private String subject;
    private String description;
}
