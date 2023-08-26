package com.azia.landing.mapper;



import com.azia.landing.entity.User;
import com.azia.landing.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
