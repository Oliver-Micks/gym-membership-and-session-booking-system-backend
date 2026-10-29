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

import kigali.gym.rw.domain.Session;
import kigali.gym.rw.service.SessionService;

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
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}