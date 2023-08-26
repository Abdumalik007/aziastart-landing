package com.azia.landing.dto;


import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImageDto {
    private Integer id;
    private String name;
    private String path;
    private String ext;
    private byte[] data;
}
