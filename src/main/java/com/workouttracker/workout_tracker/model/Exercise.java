package com.workouttracker.workout_tracker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercises")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nom de l’exercice (ex. “Squat”) */
    @Column(nullable = false, length = 100)
    private String name;

    /** Nombre de séries */
    @Column(nullable = false)
    private Integer sets;

    /** Nombre de répétitions par série */
    @Column(nullable = false)
    private Integer reps;

    /** Charge utilisée (en kg) */
    @Column(nullable = false)
    private Double weight;

    /** Séance à laquelle appartient cet exercice */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;
}
