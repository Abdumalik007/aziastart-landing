package com.azia.landing.controller;


import com.azia.landing.dto.ApplicantDto;
import com.azia.landing.service.main.ApplicantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applicant")

public class ApplicantController {
    private final ApplicantService applicantService;

    @PostMapping
    public ResponseEntity<?> createApplicant(@RequestBody @Valid ApplicantDto ApplicantDto){
        return applicantService.createApplicant(ApplicantDto);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findApplicantById(@PathVariable Integer id){
        return applicantService.findApplicantById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findApplicantByDate(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to){
        return applicantService.findApplicantByDate(from, to);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<?> findAllApplicants(){
        return applicantService.findAll();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplicantById(@PathVariable Integer id){
        return applicantService.deleteApplicantById(id);
    }

}























