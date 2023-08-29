package com.azia.landing.service.impl;

import com.azia.landing.dto.TeacherDto;
import com.azia.landing.entity.Image;
import com.azia.landing.entity.Teacher;
import com.azia.landing.mapper.TeacherMapper;
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


@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {
    public static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public ResponseEntity<?> createTeacher(TeacherDto teacherDto, MultipartFile file) {
        try {
            Teacher teacher = teacherMapper.toEntity(teacherDto);
            if(Optional.ofNullable(file).isPresent()){
                Image image = buildImage(file);
                teacher.setImage(image);
            }
            teacherRepository.save(teacher);
            teacherDto = teacherMapper.toDto(teacher);
            return ResponseEntity.ok(teacherDto);
        }catch (Exception e) {
            logger.error("Error while creating a teacher: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }



    @Override
    public ResponseEntity<?> updateTeacher(TeacherDto teacherDto, MultipartFile file) {
        try {
            Optional<Teacher> optional = teacherRepository.findById(teacherDto.getId());
            if(optional.isEmpty())
                return NOT_FOUND();
            Teacher teacher = optional.get();
            updateImage(teacherDto, teacher, file);

            teacher.setFullName(teacher.getFullName());
            teacher.setDescription(teacherDto.getDescription());
            teacher.setSubject(teacher.getSubject());

            teacherRepository.save(teacher);
            teacherDto = teacherMapper.toDto(teacher);

            return ResponseEntity.ok(teacherDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while updating a teacher: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
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
            teacherRepository.delete(teacherOptional.get());
            return OK_MESSAGE();
        }catch (Exception e){
            logger.error("Error while removing teachers: ".concat(e.getMessage()));
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



    private void updateImage(TeacherDto teacherDto, Teacher teacher, MultipartFile file) throws IOException {
        if(Optional.ofNullable(file).isPresent()) {
            if(teacherRepository.existsTeacherByIdAndImageIsNull(teacherDto.getId()))
                teacher.setImage(buildImage(file));
            else if(!Objects.equals(file.getOriginalFilename(), teacherRepository.getTeacherImageName(teacherDto.getId()))) {
                Image oldImage = teacher.getImage();
                teacher.setImage(buildImage(file));
                Files.delete(Path.of(oldImage.getPath()));
            }
        } else {
            Image image = teacher.getImage();
            String path = image.getPath();
            teacher.setImage(null);
            Files.delete(Path.of(path));
        }
    }


}
