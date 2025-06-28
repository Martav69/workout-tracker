package com.workouttracker.workout_tracker.mapper;

import com.workouttracker.workout_tracker.dto.WorkoutDTO;
import com.workouttracker.workout_tracker.mapper.ExerciseMapper;
import com.workouttracker.workout_tracker.model.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ExerciseMapper.class})
public interface WorkoutMapper {

    // Pour la réponse, on mappe workout.user.id → dto.userId
    @Mapping(target = "userId", source = "user.id")
    WorkoutDTO toDto(Workout workout);

    // Pour la création/màj, on ignore complètement le user (c'est le service qui le lie)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "exercises", source = "exercises")
    Workout toEntity(WorkoutDTO dto);
}
