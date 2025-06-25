package com.workouttracker.workout_tracker.controller;

import com.workouttracker.workout_tracker.dto.*;
import com.workouttracker.workout_tracker.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExercisesController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<ExerciseDTO> create(
            @RequestParam Long workoutId,
            @Valid @RequestBody ExerciseDTO createDto
    ) {
        ExerciseDTO created = exerciseService.createExercise(workoutId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> list(
            @RequestParam(required = false) Long workoutId
    ) {
        List<ExerciseDTO> all =
                workoutId == null
                        ? exerciseService.getAllExercises()
                        : exerciseService.getExercisesByWorkout(workoutId);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getOne(@PathVariable Long id) {
        ExerciseDTO dto = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDTO> update(
            @PathVariable Long id,
            @RequestParam Long workoutId,
            @Valid @RequestBody ExerciseDTO updateDto
    ) {
        ExerciseDTO updated = exerciseService.updateExercise(id, updateDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam Long workoutId
    ) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}
