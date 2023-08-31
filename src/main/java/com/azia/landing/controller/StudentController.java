package com.azia.landing.controller;

import com.azia.landing.dto.StudentDto;
import com.azia.landing.service.main.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createStudent(@ModelAttribute StudentDto studentDto,
                                           @RequestParam(value = "file", required = false) MultipartFile file){
        System.out.println(studentDto);
        return studentService.createStudent(studentDto, file);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateStudent(@ModelAttribute StudentDto studentDto,
                                           @RequestParam(value = "file", required = false) MultipartFile file){
        return studentService.updateStudent(studentDto, file);
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> findAllStudents(){
        return studentService.findAllStudents();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findStudentById(@PathVariable Integer id){
        return studentService.findStudentById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Integer id){
        return studentService.deleteStudentById(id);
    }

}
