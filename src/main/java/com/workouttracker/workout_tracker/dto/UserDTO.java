package com.workouttracker.workout_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO pour exposer un utilisateur sans la couche persistence.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @NotBlank(message = "Le nom d’utilisateur ne peut être vide")
    private String username;


    @NotBlank(message = "Le mot de passe ne peut être vide")
    @Size(min = 6, message = "Le mot de passe doit faire au moins 6 caractères")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role;
}
