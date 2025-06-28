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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutDTO>> listByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(workoutService.getWorkoutsByUser());
    }


    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO> getOne(@PathVariable Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> update(
            @PathVariable Long id,
            @RequestParam Long userId,
            @Valid @RequestBody WorkoutDTO dto
    ) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
