package com.azia.landing.controller;

import com.azia.landing.dto.StudentDto;
import com.azia.landing.service.main.StudentService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createStudent(@ModelAttribute @Valid StudentDto studentDto,
                                           @RequestParam MultipartFile file){
        System.out.println(studentDto);
        return studentService.createStudent(studentDto, file);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateStudent(@ModelAttribute @Valid StudentDto studentDto,
                                           @RequestParam(required = false) MultipartFile file){
        return studentService.updateStudent(studentDto, file);
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> findAllStudents(){
        return studentService.findAllStudents();
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchStudentByParams(@PathVariable String name){
        String[] s = name.split(" ");
        boolean first = s.length > 0;
        boolean second = s.length > 1;
        return studentService.search((first) ? s[0] : null, (second) ? s[1] : null);
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
