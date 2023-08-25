package com.azia.landing.dto.custom;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidatorDTO {
    private String fieldName;
    private String error;
}
