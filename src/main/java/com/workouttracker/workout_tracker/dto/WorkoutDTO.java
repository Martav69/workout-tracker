package com.workouttracker.workout_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WorkoutDTO {

    private Long id;

    @NotNull(message = "Le nom de la séance est requis")
    @Size(max = 100, message = "Le nom ne peut dépasser 100 caractères.")
    private String name;


    @NotNull(message = "La date de la séance est requise !")
    private LocalDate date;

    @NotNull(message = "La durée de la séance est requise !")
    @Positive(message = "La durée ne peux être négative")
    private Integer durationMinutes;

    @Size(max = 500, message = "Les notes ne peuvent dépasser 500 caractères.")
    private String notes;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;

    @NotNull(message = "La liste des exercices est requise")
    @Valid
    private List<ExerciseDTO> exercises;


}
