package com.azia.landing.service.main;

import com.azia.landing.dto.ApplicantDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface ApplicantService {
    ResponseEntity<?> createApplicant(ApplicantDto ApplicantDto);
    ResponseEntity<?> findApplicantById(Integer id);
    ResponseEntity<?> findApplicantByDate(LocalDate from, LocalDate to);
    ResponseEntity<?> findAll();
    ResponseEntity<?> search(String firstName, String lastName);
    ResponseEntity<?> deleteApplicantById(Integer id);
    ResponseEntity<?> isApplicantContacted(Integer applicantId, Boolean isContacted);
    ResponseEntity<?> getByFilter(String value);
}
