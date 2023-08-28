package com.azia.landing.entity;

import com.azia.landing.dto.ImageDto;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String fullName;
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;
    private String subject;
    private String description;
}
