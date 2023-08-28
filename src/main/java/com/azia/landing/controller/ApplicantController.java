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
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applicant")

public class ApplicantController {
    private final ApplicantService ApplicantService;

    @PostMapping
    public ResponseEntity<?> createApplicant(@RequestBody @Valid ApplicantDto ApplicantDto){
        return ApplicantService.createApplicant(ApplicantDto);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findApplicantById(@PathVariable Integer id){
        return ApplicantService.findApplicantById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findApplicantByDate(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to){
        return ApplicantService.findApplicantByDate(from, to);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<?> findAllApplicants(){
        return ApplicantService.findAll();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplicantById(@PathVariable Integer id){
        return ApplicantService.deleteApplicantById(id);
    }

}























