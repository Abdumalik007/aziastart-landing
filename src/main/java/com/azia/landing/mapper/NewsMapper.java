package com.azia.landing.mapper;

import com.azia.landing.dto.ImageDto;
import com.azia.landing.dto.NewsDto;
import com.azia.landing.entity.Image;
import com.azia.landing.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(target = "image", ignore = true)
    News toEntity(NewsDto newsDto);
    NewsDto toDto(News news);


    default ImageDto imageToImageDto(Image image) throws IOException {
        return Optional.ofNullable(image).isPresent() ?
                ImageDto.builder()
                        .id(image.getId())
                        .path(image.getPath())
                        .build()
                : null;
    }
}
