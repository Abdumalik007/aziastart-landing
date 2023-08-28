package com.azia.landing.controller;


import com.azia.landing.dto.TeacherDto;
import com.azia.landing.service.main.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<?> createTeacher(@ModelAttribute TeacherDto teacherDto,
                                           @RequestParam("file") MultipartFile file){
        return teacherService.createTeacher(teacherDto, file);
    }


    @PutMapping
    public ResponseEntity<?> updateTeacher(@ModelAttribute TeacherDto teacherDto,
                                           @RequestParam("file") MultipartFile file){
        return teacherService.updateTeacher(teacherDto, file);
    }

}
