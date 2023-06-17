package kpo.crud.repositories;

import kpo.crud.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends JpaRepository<Workout, Integer> {
}
