package kigali.gym.rw.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.gym.rw.domain.WorkoutLog;
import kigali.gym.rw.service.WorkoutLogService;

@RestController
@RequestMapping(value = "/api/workout-logs")
public class WorkoutLogController {

    @Autowired
    private WorkoutLogService workoutLogService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveWorkoutLog(@RequestParam UUID sessionId,@RequestBody WorkoutLog workoutLog) {
        String message = workoutLogService.saveWorkoutLog(sessionId,workoutLog);
        if (message.equals("Workout log saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("Workout logs are only for ATTENDED sessions")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/member/{membershipNumber}")
    public ResponseEntity<?> findByMemberNumber(@PathVariable String membershipNumber) {
        return new ResponseEntity<>(workoutLogService.findByMemberNumber(membershipNumber), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteWorkoutLog(@PathVariable UUID id) {
        String message = workoutLogService.deleteWorkoutLog(id);
        if (message.equals("Workout log deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}