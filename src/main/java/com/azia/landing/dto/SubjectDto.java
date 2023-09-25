package com.azia.landing.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubjectDto {
    private Integer id;
    private String name;
    private TeacherDto teacher;

}
