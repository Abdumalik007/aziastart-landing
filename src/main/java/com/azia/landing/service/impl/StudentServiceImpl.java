package com.azia.landing.service.impl;

import com.azia.landing.dto.StudentDto;
import com.azia.landing.entity.Image;
import com.azia.landing.entity.Student;
import com.azia.landing.mapper.StudentMapper;
import com.azia.landing.repository.StudentRepository;
import com.azia.landing.service.main.StudentService;
import com.azia.landing.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.azia.landing.helper.ResponseEntityHelper.*;


@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    public static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public ResponseEntity<?> createStudent(StudentDto studentDto, MultipartFile file) {
        try {
            Student student = studentMapper.toEntity(studentDto);
            if(Optional.ofNullable(file).isPresent()){
                Image image = buildImage(file);
                student.setImage(image);
            }
            studentRepository.save(student);
            studentDto = studentMapper.toDto(student);
            return ResponseEntity.ok(studentDto);
        }catch (Exception e) {
            logger.error("Error while creating a student: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }



    @Override
    public ResponseEntity<?> updateStudent(StudentDto studentDto, MultipartFile file) {
        try {
            Optional<Student> optional = studentRepository.findById(studentDto.getId());
            if(optional.isEmpty())
                return NOT_FOUND();
            Student student = optional.get();
            updateImage(studentDto, student, file);

            student.setFullName(student.getFullName());
            student.setLevel(studentDto.getLevel());
            studentRepository.save(student);
            studentDto = studentMapper.toDto(student);

            return ResponseEntity.ok(studentDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while updating a student: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }


    @Override
    public ResponseEntity<?> findStudentById(Integer id) {
        Optional<Student> optional = studentRepository.findById(id);
        if(optional.isPresent()) return ResponseEntity.ok(studentMapper.toDto(optional.get()));
        return NOT_FOUND();
    }

    @Override
    public ResponseEntity<?> findAllStudents() {
        List<StudentDto> students = studentRepository.findAll()
                .stream().map(studentMapper::toDto).toList();
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<?> deleteStudentById(Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        try {
            if(studentOptional.isEmpty())
                return NOT_FOUND();
            Files.delete(Path.of(studentOptional.get().getImage().getPath()));
            studentRepository.delete(studentOptional.get());
            return OK_MESSAGE();
        }catch (Exception e){
            logger.error("Error while removing students: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }


    private Image buildImage(MultipartFile file) {
        return Image.builder()
                .name(file.getOriginalFilename())
                .ext(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1])
                .path(ImageUtil.uploadImage(file))
                .build();
    }



    private void updateImage(StudentDto studentDto, Student student, MultipartFile file) throws IOException {
        if(Optional.ofNullable(file).isPresent()) {
            if(studentRepository.existsStudentByIdAndImageIsNull(studentDto.getId()))
                student.setImage(buildImage(file));
            else if(!Objects.equals(file.getOriginalFilename(), studentRepository.getStudentImageName(studentDto.getId()))) {
                Image oldImage = student.getImage();
                student.setImage(buildImage(file));
                Files.delete(Path.of(oldImage.getPath()));
            }
        } else {
            Image image = student.getImage();
            String path = image.getPath();
            student.setImage(null);
            Files.delete(Path.of(path));
        }
    }


}

