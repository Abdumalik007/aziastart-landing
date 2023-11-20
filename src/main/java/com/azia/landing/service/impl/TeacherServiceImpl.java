package com.azia.landing.service.impl;

import com.azia.landing.dto.TeacherDto;
import com.azia.landing.entity.Image;
import com.azia.landing.entity.Subject;
import com.azia.landing.entity.Teacher;
import com.azia.landing.mapper.TeacherMapper;
import com.azia.landing.repository.ImageRepository;
import com.azia.landing.repository.SubjectRepository;
import com.azia.landing.repository.TeacherRepository;
import com.azia.landing.service.main.TeacherService;
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
import static com.azia.landing.util.ImageUtil.IMAGE_PATH;
import static com.azia.landing.util.ImageUtil.buildImage;


@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {
    public static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ImageRepository imageRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public ResponseEntity<?> createTeacher(TeacherDto teacherDto, MultipartFile file, Integer subjectId) {
        try {
            Teacher teacher = teacherMapper.toEntity(teacherDto);

            Image image = buildImage(file);
            teacher.setImage(image);

            addSubjectToTeacher(teacher, subjectId);
            teacherDto = teacherMapper.toDto(teacher);

            return ResponseEntity.ok(teacherDto);
        }catch (Exception e) {
            logger.error("Error while creating a teacher: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    private void addSubjectToTeacher(Teacher teacher, Integer subjectId) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);

        if(optionalSubject.isEmpty())
            throw new RuntimeException("The subject does not exist");

        Subject subject = optionalSubject.get();

        if(Optional.ofNullable(subject.getTeacher()).isPresent())
            throw new RuntimeException("The subject has already connected to teacher");

        teacher.setSubject(subject);
        teacherRepository.save(teacher);

        subject.setTeacher(teacher);
        subjectRepository.save(subject);
    }


    @Override
    public ResponseEntity<?> updateTeacher(TeacherDto teacherDto, MultipartFile file, Integer subjectId) {
        try {
            Optional<Teacher> optional = teacherRepository.findById(teacherDto.getId());
            if(optional.isEmpty())
                return NOT_FOUND();

            Teacher teacher = optional.get();
            if(file != null)
                updateImage(teacher, file);

            teacher.setFirstName(teacherDto.getFirstName());
            teacher.setLastName(teacherDto.getLastName());
            teacher.setDescription(teacherDto.getDescription());

            if(!subjectId.equals(teacher.getSubject().getId()))
                updateTeachersSubject(teacher, subjectId);

            teacherRepository.save(teacher);

            teacherDto = teacherMapper.toDto(teacher);

            return ResponseEntity.ok(teacherDto);
        } catch (Exception e) {
            logger.error("Error while updating a teacher: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }


    @Override
    public ResponseEntity<?> search(String firstName, String lastName) {
        List<TeacherDto> teachers = teacherRepository.findByFirstNameOrLastName(firstName, lastName)
                .stream().map(teacherMapper::toDto).toList();
        return ResponseEntity.ok(teachers);
    }


    @Override
    public ResponseEntity<?> findTeacherById(Integer id) {
        Optional<Teacher> optional = teacherRepository.findById(id);
        if(optional.isPresent()) return ResponseEntity.ok(teacherMapper.toDto(optional.get()));
        return NOT_FOUND();
    }



    @Override
    public ResponseEntity<?> findAllTeachers() {
        List<TeacherDto> teachers = teacherRepository.findAll()
                .stream().map(teacherMapper::toDto).toList();
        return ResponseEntity.ok(teachers);
    }



    @Override
    public ResponseEntity<?> deleteTeacherById(Integer id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        try {
            if(teacherOptional.isEmpty())
                return NOT_FOUND();
            Teacher teacher = teacherOptional.get();
            teacher.getSubject().setTeacher(null);
            teacherRepository.save(teacher);
            teacherRepository.delete(teacher);

            Files.delete(Path.of(IMAGE_PATH + "/" + teacherOptional.get().getImage().getPath()));
            return OK_MESSAGE();
        }catch (Exception e){
            logger.error("Error while removing teachers: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }


    private void updateTeachersSubject(Teacher teacher, Integer subjectId) {
        Optional<Subject> optional = subjectRepository.findById(subjectId);

        if(optional.isEmpty()) throw new RuntimeException("Subject does not exist");
        Subject subject = optional.get();

        if(Optional.ofNullable(subject.getTeacher()).isPresent())
            throw new RuntimeException("The subject has already connected to teacher");

        if(Optional.ofNullable(teacher.getSubject().getTeacher()).isPresent()) {
            teacher.getSubject().setTeacher(null);
            teacherRepository.save(teacher);
        }

        subject.setTeacher(teacher);
        subjectRepository.save(subject);

        teacher.setSubject(subject);
    }




    private void updateImage(Teacher teacher, MultipartFile file) throws IOException {
        if(Optional.ofNullable(teacher.getImage()).isPresent()) {
            Image oldImage = teacher.getImage();
            imageRepository.delete(oldImage);
            Files.delete(Path.of(oldImage.getPath()));
        }
        teacher.setImage(buildImage(file));

    }


}
