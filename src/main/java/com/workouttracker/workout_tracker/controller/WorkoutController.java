package com.workouttracker.workout_tracker.controller;

import com.workouttracker.workout_tracker.dto.WorkoutDTO;
import com.workouttracker.workout_tracker.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.workouttracker.workout_tracker.util.SecurityUtils;

import java.util.List;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
@CrossOrigin
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> list() {
        return ResponseEntity.ok(workoutService.getWorkoutsByUser());
    }

    @PostMapping
    public ResponseEntity<WorkoutDTO> create(@Valid @RequestBody WorkoutDTO dto) {
        WorkoutDTO created = workoutService.createWorkout(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutDTO dto
    ) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
