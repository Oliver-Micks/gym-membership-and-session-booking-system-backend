package kigali.gym.rw.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.gym.rw.domain.Trainer;
import kigali.gym.rw.service.TrainerService;

@RestController
@RequestMapping(value = "/api/trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveTrainer(@RequestBody Trainer trainer) {
        String message = trainerService.saveTrainer(trainer);
        if (message.equals("Trainer saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllTrainers() {
        return new ResponseEntity<>(trainerService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTrainerById(@PathVariable UUID id) {
        Optional<Trainer> trainer = trainerService.findById(id);
        if (trainer.isEmpty()) {
            return new ResponseEntity<>("The trainer with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trainer.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateTrainer(@PathVariable UUID id,@RequestBody Trainer trainer) {
        String message = trainerService.updateTrainer(id,trainer);
        if (message.equals("Trainer updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTrainer(@PathVariable UUID id) {
        String message = trainerService.deleteTrainer(id);
        if (message.equals("Trainer deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/by-discipline")
    public ResponseEntity<?> findByDiscipline(@RequestParam String name) {
        return new ResponseEntity<>(trainerService.findByDiscipline(name), HttpStatus.OK);
    }
}