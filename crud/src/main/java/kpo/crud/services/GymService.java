package kpo.crud.services;

import kpo.crud.models.Workout;
import kpo.crud.requests.WorkoutRequest;
import kpo.crud.repositories.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository repository;

    public List<Workout> getAll() {
        return repository.findAll();
    }

    public ResponseEntity<String> add(WorkoutRequest request) {
        Workout workout = new Workout();

        if (request.getName() == null || request.getName().equals("")) {
            return new ResponseEntity<>("Incorrect name", HttpStatus.BAD_REQUEST);
        } else if (request.getTime() == null || request.getTime() <= 0 || request.getTime() > 180)  {
            return new ResponseEntity<>("Incorrect time", HttpStatus.BAD_REQUEST);
        } else if (request.getDescription() == null || request.getDescription().equals("")) {
            return new ResponseEntity<>("Incorrect description", HttpStatus.BAD_REQUEST);
        } else if (request.getCoach() == null || request.getCoach().equals("")) {
            return new ResponseEntity<>("Incorrect coach", HttpStatus.BAD_REQUEST);
        }

        workout.setName(request.getName());
        workout.setTime(request.getTime());
        workout.setDescription(request.getDescription());
        workout.setCoach(request.getCoach());

        repository.save(workout);

        return ResponseEntity.ok("Workout has been successfully created");
    }

    public Workout get(int id) {
        return repository.findById(id).orElse(null);
    }

    public ResponseEntity<StringBuilder> update(Integer id, WorkoutRequest request) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(new StringBuilder("Id not found"), HttpStatus.BAD_REQUEST);
        }

        StringBuilder response = new StringBuilder();
        Workout workout;

        try {
            workout = repository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new StringBuilder("Id not found"),HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equals(request.getName(), "") && !Objects.equals(workout.getName(), request.getName())) {
            response.append("Name has been changed").append("\n");
            workout.setName(request.getName());
        }

        if (!Objects.equals(request.getDescription(), "") && !Objects.equals(workout.getDescription(), request.getDescription())) {
            response.append("Description has been changed").append("\n");
            workout.setDescription(request.getDescription());
        }

        if ((request.getTime() > 0 && request.getTime() <= 180) && !request.getTime().equals(workout.getTime())) {
            response.append("Time has been changed").append("\n");
            workout.setTime(request.getTime());
        }

        if (!Objects.equals(request.getCoach(), "") && !Objects.equals(workout.getCoach(), request.getCoach())) {
            response.append("Coach has been changed").append("\n");
            workout.setCoach(request.getCoach());
        }

        if (response.isEmpty()) {
            return new ResponseEntity<>(new StringBuilder("Empty response was given"), HttpStatus.BAD_REQUEST);
        }

        repository.save(workout);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> delete(Integer id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>("Id doesn't exist", HttpStatus.BAD_REQUEST);
        }

        repository.deleteById(id);
        return ResponseEntity.ok("Workout has been successfully deleted");
    }
}
