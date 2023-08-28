package com.azia.landing.mapper;


import com.azia.landing.dto.ApplicantDto;
import com.azia.landing.entity.Applicant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {

    ApplicantDto toDto(Applicant Applicant);
    @Mapping(target = "createdAt", ignore = true)
    Applicant toEntity(ApplicantDto ApplicantDto);
}
