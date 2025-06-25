// src/main/java/com/workouttracker/workout_tracker/dto/ExerciseDTO.java
package com.workouttracker.workout_tracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDTO {

    private Long id;

    @NotBlank(message = "Le nom de l’exercise ne peut être vide")
    private String name;

    @NotNull @Positive(message = "Le nombre de séries doit être positif")
    private Integer sets;

    @NotNull @Positive(message = "Le nombre de répétitions doit être positif")
    private Integer reps;

    @NotNull @PositiveOrZero(message = "Le poids ne peut être négatif")
    private Double weight;
    
}
