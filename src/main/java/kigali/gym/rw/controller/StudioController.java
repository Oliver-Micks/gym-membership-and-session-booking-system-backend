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

import kigali.gym.rw.domain.Studio;
import kigali.gym.rw.service.StudioService;

@RestController
@RequestMapping(value = "/api/studios")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveStudio(@RequestBody Studio studio) {
        String message = studioService.saveStudio(studio);
        if (message.equals("Studio saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllStudios() {
        return new ResponseEntity<>(studioService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getStudioById(@PathVariable UUID id) {
        Optional<Studio> studio = studioService.findById(id);
        if (studio.isEmpty()) {
            return new ResponseEntity<>("The studio with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studio.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateStudio(@PathVariable UUID id,@RequestBody Studio studio) {
        String message = studioService.updateStudio(id,studio);
        if (message.equals("Studio updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteStudio(@PathVariable UUID id) {
        String message = studioService.deleteStudio(id);
        if (message.equals("Studio deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}