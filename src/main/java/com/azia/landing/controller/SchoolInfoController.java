package com.azia.landing.controller;


import com.azia.landing.dto.SchoolInfoDto;
import com.azia.landing.service.main.SchoolInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/school-info")
public class SchoolInfoController {
    private final SchoolInfoService schoolInfoService;

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateSchoolInfo(@RequestBody @Valid SchoolInfoDto schoolInfoDto){
        return schoolInfoService.updateSchoolInfo(schoolInfoDto);
    }


    @GetMapping
    public ResponseEntity<?> getSchoolInfo(){
        return schoolInfoService.getSchoolInfoDto();
    }

}
