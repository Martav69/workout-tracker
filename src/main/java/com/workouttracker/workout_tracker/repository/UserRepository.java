package com.workouttracker.workout_tracker.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workouttracker.workout_tracker.model.User;

/**
 * Repository JPA pour l’entité User.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
