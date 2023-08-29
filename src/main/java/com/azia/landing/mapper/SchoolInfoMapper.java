package com.azia.landing.mapper;

import com.azia.landing.dto.SchoolInfoDto;
import com.azia.landing.entity.SchoolInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolInfoMapper {
    SchoolInfoDto toDto(SchoolInfo schoolInfo);
    SchoolInfo toEntity(SchoolInfoDto schoolInfoDto);
}
