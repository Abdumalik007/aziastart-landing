package com.azia.landing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApplicantDto {
    private Integer id;
    @NotBlank(message = "Firstname must not be blank")
    private String firstName;
    @NotBlank(message = "Lastname must not be blank")
    private String lastName;
    @NotBlank(message = "Level must not be blank")
    private String level;
    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;
}
