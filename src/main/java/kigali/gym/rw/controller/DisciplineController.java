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
import org.springframework.web.bind.annotation.RestController;

import kigali.gym.rw.domain.Discipline;
import kigali.gym.rw.service.DisciplineService;

@RestController
@RequestMapping(value = "/api/disciplines")
public class DisciplineController {

    @Autowired
    private DisciplineService disciplineService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveDiscipline(@RequestBody Discipline discipline) {
        String message = disciplineService.saveDiscipline(discipline);
        if (message.equals("Discipline saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllDisciplines() {
        return new ResponseEntity<>(disciplineService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDisciplineById(@PathVariable UUID id) {
        Optional<Discipline> discipline = disciplineService.findById(id);
        if (discipline.isEmpty()) {
            return new ResponseEntity<>("The discipline with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(discipline.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateDiscipline(@PathVariable UUID id,@RequestBody Discipline discipline) {
        String message = disciplineService.updateDiscipline(id,discipline);
        if (message.equals("Discipline updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDiscipline(@PathVariable UUID id) {
        String message = disciplineService.deleteDiscipline(id);
        if (message.equals("Discipline deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/unused")
    public ResponseEntity<?> findUnusedDisciplines() {
        return new ResponseEntity<>(disciplineService.findUnusedDisciplines(), HttpStatus.OK);
    }
}