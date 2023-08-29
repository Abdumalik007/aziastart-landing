package com.azia.landing.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SchoolInfoDto {
    private Integer id;
    private Integer graduateAmount;
    private Integer branchAmount;
    private Integer studentAmount;
    private Integer mediumOptScore;
}
