package com.azia.landing.mapper;

import com.azia.landing.dto.ImageDto;
import com.azia.landing.dto.SubjectDto;
import com.azia.landing.dto.TeacherDto;
import com.azia.landing.entity.Image;
import com.azia.landing.entity.Subject;
import com.azia.landing.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDto toDto(Teacher teacher);
    @Mapping(target = "image", ignore = true)
    Teacher toEntity(TeacherDto teacherDto);

    default ImageDto imageToImageDto(Image image) throws IOException {
        return Optional.ofNullable(image).isPresent() ?
                ImageDto.builder()
                        .id(image.getId())
                        .name(image.getName())
                        .ext(image.getExt())
                        .path(image.getPath())
                        .data(Files.readAllBytes(Path.of(image.getPath())))
                        .build()
                : null;
    }


    default SubjectDto subjectToSubjectDto(Subject subject) throws IOException {
        return Optional.ofNullable(subject).isPresent() ?
                SubjectDto.builder()
                        .id(subject.getId())
                        .name(subject.getName())
                        .build()
                : null;
    }

}
