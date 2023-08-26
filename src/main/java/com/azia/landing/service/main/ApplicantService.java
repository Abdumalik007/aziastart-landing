package com.azia.landing.service.main;

import com.azia.landing.dto.ApplicantDto;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface ApplicantService {
    ResponseEntity<?> createApplicant(ApplicantDto ApplicantDto);
    ResponseEntity<?> findApplicantById(Integer id);
    ResponseEntity<?> findApplicantByDate(Date from, Date to);
    ResponseEntity<?> findAll();
    ResponseEntity<?> deleteApplicantById(Integer id);
}
