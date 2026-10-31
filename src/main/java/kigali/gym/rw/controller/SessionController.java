package kigali.gym.rw.controller;

import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PatchMapping;

import kigali.gym.rw.domain.Session;
import kigali.gym.rw.service.SessionService;
import kigali.gym.rw.domain.SessionStatus;

@RestController
@RequestMapping(value = "/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveSession(@RequestBody Session session) {
        String message = sessionService.saveSession(session);
        if (message.equals("Session saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("Session date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (message.equals("Trainer is already booked on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllSessions() {
        return new ResponseEntity<>(sessionService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSessionById(@PathVariable UUID id) {
        Optional<Session> session = sessionService.findById(id);
        if (session.isEmpty()) {
            return new ResponseEntity<>("The session with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(session.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateSession(@PathVariable UUID id,@RequestBody Session session) {
        String message = sessionService.updateSession(id,session);
        if (message.equals("Session updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable UUID id) {
        String message = sessionService.deleteSession(id);
        if (message.equals("Session deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if (message.equals("That session has workout logs, delete them first")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/by-status")
    public ResponseEntity<?> findByStatus(@RequestParam SessionStatus status) {
        return new ResponseEntity<>(sessionService.findByStatus(status), HttpStatus.OK);
    }

    @GetMapping(value = "/between")
    public ResponseEntity<?> findBetween(@RequestParam String start,@RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(sessionService.findBetween(startDate,endDate), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id,@RequestParam SessionStatus status) {
        String message = sessionService.changeStatus(id,status);
        if (message.equals("Session status updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if (message.equals("The session with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}