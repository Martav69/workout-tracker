package com.workouttracker.workout_tracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.workouttracker.workout_tracker.model.Exercise;
import com.workouttracker.workout_tracker.dto.ExerciseDTO;


@Mapper(componentModel = "spring")
public interface ExerciseMapper {


    @Mapping(target = "workoutId", source = "workout.id")
    ExerciseDTO toDto(Exercise exercise);

    @Mapping(target = "workout", ignore = true)
    Exercise toEntity(ExerciseDTO dto);
}
