package com.workouttracker.workout_tracker.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "workouts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Date de la séance */
    @Column(nullable = false)
    private LocalDate date;

    /** Durée de la séance en minutes */
    @Column(nullable = false)
    private Integer durationMinutes;

    /** Notes libres (jusqu’à 500 caractères) */
    @Column(length = 500)
    private String notes;

    /** Utilisateur propriétaire de la séance */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Liste des exercices composant la séance */
    @OneToMany(
            mappedBy = "workout",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Exercise> exercises;
}
