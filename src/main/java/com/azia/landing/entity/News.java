package com.azia.landing.entity;

import com.azia.landing.dto.ImageDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String title;

    @Column(length = 5000)
    private String content;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;
    private LocalDate createdAt;
}
