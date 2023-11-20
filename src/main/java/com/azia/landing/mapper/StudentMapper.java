package com.azia.landing.mapper;

import com.azia.landing.dto.ImageDto;
import com.azia.landing.dto.StudentDto;
import com.azia.landing.entity.Image;
import com.azia.landing.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDto(Student studentDto);
    @Mapping(target = "image", ignore = true)
    Student toEntity(StudentDto studentDto);

    default ImageDto imageToImageDto(Image image) throws IOException {
        return Optional.ofNullable(image).isPresent() ?
                ImageDto.builder()
                        .id(image.getId())
                        .path(image.getPath())
                        .build()
                : null;
    }

}
