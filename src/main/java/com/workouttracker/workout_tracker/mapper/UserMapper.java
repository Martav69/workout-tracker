package com.workouttracker.workout_tracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.workouttracker.workout_tracker.model.User;
import com.workouttracker.workout_tracker.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Convertit User → UserDTO
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    UserDTO toDto(User user);

    // Convertit UserDTO → User
    @Mapping(target = "role", ignore = true)  // on définit le rôle en service, pas dans le DTO
    User toEntity(UserDTO dto);
}
