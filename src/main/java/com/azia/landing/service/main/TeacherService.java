package com.azia.landing.service.main;

import com.azia.landing.dto.TeacherDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherService {
    ResponseEntity<?> createTeacher(TeacherDto teacherDto, MultipartFile file);
    ResponseEntity<?> updateTeacher(TeacherDto teacherDto, MultipartFile file);
    ResponseEntity<?> findTeacherById(Integer id);
    ResponseEntity<?> findAllTeachers();
    ResponseEntity<?> deleteTeacherById(Integer id);
}
