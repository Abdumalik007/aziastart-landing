package com.azia.landing.mapper;

import com.azia.landing.dao.Admin;
import com.azia.landing.dto.AdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "user.role")
    AdminDto toDto(Admin admin);
    Admin toEntity(AdminDto adminDto);
}
