package com.azia.landing.mapper;


import com.azia.landing.dto.ApplicantDto;
import com.azia.landing.entity.Applicant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {
    ApplicantDto toDto(Applicant Applicant);
    Applicant toEntity(ApplicantDto ApplicantDto);
}
