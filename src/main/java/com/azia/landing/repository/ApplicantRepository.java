package com.azia.landing.repository;

import com.azia.landing.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
    List<Applicant> findAllByCreatedAtBetweenOrderByCreatedAt(LocalDate from, LocalDate to);
    List<Applicant> findByOrderByCreatedAt();
}
