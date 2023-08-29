package com.azia.landing.service.main;

import com.azia.landing.dto.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {
    ResponseEntity<?> createStudent(StudentDto studentDto, MultipartFile file);
    ResponseEntity<?> updateStudent(StudentDto studentDto, MultipartFile file);
    ResponseEntity<?> findStudentById(Integer id);
    ResponseEntity<?> findAllStudents();
    ResponseEntity<?> deleteStudentById(Integer id);
}