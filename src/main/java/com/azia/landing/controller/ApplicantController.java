package com.azia.landing.controller;


import com.azia.landing.dto.ApplicantDto;
import com.azia.landing.service.main.ApplicantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PutMapping("/contact/{id}")
    public ResponseEntity<?> isApplicantContacted(@PathVariable("id") Integer applicantId,
                                                  @RequestParam Boolean isContacted){
        return applicantService.isApplicantContacted(applicantId, isContacted);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findApplicantById(@PathVariable Integer id){
        return applicantService.findApplicantById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filter/{value}")
    public ResponseEntity<?> getByFilter(@PathVariable String value){
        return applicantService.getByFilter(value);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findApplicantByDate(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to){
        return applicantService.findApplicantByDate(from, to);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchApplicantByParams(@PathVariable String name){
        String[] s = name.split(" ");
        boolean first = s.length > 0;
        boolean second = s.length > 1;
        return applicantService.search((first) ? s[0] : null, (second) ? s[1] : null);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<?> findAllApplicants(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        return applicantService.findAll();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplicantById(@PathVariable Integer id){
        return applicantService.deleteApplicantById(id);
    }

}























