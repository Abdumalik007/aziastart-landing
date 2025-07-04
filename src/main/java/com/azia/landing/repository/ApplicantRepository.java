package com.azia.landing.repository;

import com.azia.landing.entity.Applicant;
import com.azia.landing.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {

    @Modifying
    @Transactional
    @Query("update Applicant a set a.isContacted = :value where a.id = :id")
    void isContacted(Boolean value, Integer id);
    List<Applicant> findAllByIsContactedIsTrue();
    List<Applicant> findApplicantByIsContactedIsFalse();
    List<Applicant> findAllByOrderByCreatedAtDesc();
    List<Applicant> findAllByCreatedAtBetweenOrderByCreatedAt(LocalDate from, LocalDate to);
    List<Applicant> findByOrderByCreatedAt();
    @Query("""
    select a from Applicant a where (:second is null and (a.firstName ilike :first% or a.lastName ilike :first%))
    or (a.firstName ilike :first% and a.lastName ilike :second%)
    """)
    List<Applicant> findByFirstNameOrLastName(String first, String second);
}
