package com.azia.landing.controller;


import com.azia.landing.dto.ApplicantDto;
import com.azia.landing.service.main.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/student-info")
public class ApplicantController {
    private final ApplicantService ApplicantService;

    @PostMapping
    public ResponseEntity<?> createApplicant(@RequestBody ApplicantDto ApplicantDto){
        return ApplicantService.createApplicant(ApplicantDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findApplicantById(@PathVariable Integer id){
        return ApplicantService.findApplicantById(id);
    }

    @GetMapping
    public ResponseEntity<?> findApplicantByDate(@RequestParam Date from, @RequestParam Date to){
        return ApplicantService.findApplicantByDate(from, to);
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> findApplicantById(){
        return ApplicantService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplicantById(@PathVariable Integer id){
        return ApplicantService.deleteApplicantById(id);
    }

}























