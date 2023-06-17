package kpo.crud.controllers;

import kpo.crud.services.GymService;
import kpo.crud.models.Workout;
import kpo.crud.requests.WorkoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class GymController {

    private final GymService service;

    /**
     * 1. Получение списка всех тренировок.
     */
    @GetMapping
    public ResponseEntity<StringBuilder> getAllWorkouts() {
        StringBuilder response = new StringBuilder();
        for (Workout workout: service.getAll()) {
            response.append("id: ").append(workout.getId()).append("\n")
                    .append("name: ").append(workout.getName()).append("\n")
                    .append("description: ").append(workout.getDescription()).append("\n")
                    .append("time: ").append(workout.getTime()).append("\n")
                    .append("coach: ").append(workout.getCoach()).append("\n")
                    .append("\n");
        }

        if (response.isEmpty()) {
            return ResponseEntity.ok(new StringBuilder("No workouts yet"));
        } else {
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 2. Создание новой тренировки.
     */
    @PostMapping
    public ResponseEntity<String> createWorkout(@RequestBody WorkoutRequest request) {
        return service.add(request);
    }

    /**
     * 3. Получение информации о тренировке по id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StringBuilder> getWorkoutById(@PathVariable Integer id) {
        Workout workout = service.get(id);
        StringBuilder response = new StringBuilder();

        if (workout == null) {
            return new ResponseEntity<>(new StringBuilder("Id not found"),HttpStatus.BAD_REQUEST);
        }

        response.append("name: ").append(workout.getName()).append("\n")
                .append("description: ").append(workout.getDescription()).append("\n")
                .append("time: ").append(workout.getTime()).append("\n")
                .append("coach: ").append(workout.getCoach()).append("\n")
                .append("\n");

        return ResponseEntity.ok(response);
    }

    /**
     * 4. Обновление информации о тренировке по id.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StringBuilder> updateWorkoutById(@PathVariable Integer id,
                                                           @RequestBody WorkoutRequest request) {
        return service.update(id, request);
    }

    /**
     * 5. Удаление тренировки по id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkoutById(@PathVariable Integer id) {
        return service.delete(id);
    }
}
