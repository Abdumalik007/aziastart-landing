package com.azia.landing.entity;

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
    private String firstName;
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;
    @OneToOne
    private Subject subject;
    private String description;
}
