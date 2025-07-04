package com.azia.landing.controller;


import com.azia.landing.dto.TeacherDto;
import com.azia.landing.service.main.TeacherService;
import jakarta.validation.Valid;
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
    @PostMapping("/{subjectId}")
    public ResponseEntity<?> createTeacher(@ModelAttribute @Valid TeacherDto teacherDto,
                                           @RequestParam MultipartFile file,
                                           @PathVariable Integer subjectId){
        return teacherService.createTeacher(teacherDto, file, subjectId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{subjectId}")
    public ResponseEntity<?> updateTeacher(@ModelAttribute @Valid TeacherDto teacherDto,
                                           @RequestParam(value = "file", required = false) MultipartFile file,
                                           @PathVariable Integer subjectId){
        return teacherService.updateTeacher(teacherDto, file, subjectId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchTeacherByParams(@PathVariable String name){
        String[] s = name.split(" ");
        boolean first = s.length > 0;
        boolean second = s.length > 1;
        return teacherService.search((first) ? s[0] : null, (second) ? s[1] : null);
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
