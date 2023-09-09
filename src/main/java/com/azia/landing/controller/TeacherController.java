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
public class TeacherController {

    private final TeacherService teacherService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createTeacher(@ModelAttribute TeacherDto teacherDto,
                                           @RequestParam(value = "file") MultipartFile file){
        return teacherService.createTeacher(teacherDto, file);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateTeacher(@ModelAttribute TeacherDto teacherDto,
                                           @RequestParam(value = "file") MultipartFile file){
        return teacherService.updateTeacher(teacherDto, file);
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> findAllTeachers(){
        return teacherService.findAllTeachers();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findTeacherById(@PathVariable Integer id){
        return teacherService.findTeacherById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacherById(@PathVariable Integer id){
        return teacherService.deleteTeacherById(id);
    }



}
