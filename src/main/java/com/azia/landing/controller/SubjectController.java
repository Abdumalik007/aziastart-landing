package com.azia.landing.controller;

import com.azia.landing.dto.SubjectDto;
import com.azia.landing.service.main.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateSubjects(@RequestBody List<SubjectDto> subjectsDto){
        return subjectService.updateSubjects(subjectsDto);
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> findAllSubjects(){
        return subjectService.findAllSubjects();
    }

}
