package com.azia.landing.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String fullName;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;
    private String _class;
}
