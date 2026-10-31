package kigali.gym.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.gym.rw.domain.Session;
import kigali.gym.rw.domain.SessionStatus;
import kigali.gym.rw.domain.WorkoutLog;
import kigali.gym.rw.repository.SessionRepository;
import kigali.gym.rw.repository.WorkoutLogRepository;

@Service
public class WorkoutLogService {

    @Autowired
    private WorkoutLogRepository workoutLogRepo;

    @Autowired
    private SessionRepository sessionRepo;

    public String saveWorkoutLog(UUID sessionId,WorkoutLog workoutLog) {
        Optional<Session> session = sessionRepo.findById(sessionId);
        if (session.isEmpty()) {
            return "The session with that id does not exist";
        }
        if (session.get().getStatus() != SessionStatus.ATTENDED) {
            return "Workout logs are only for ATTENDED sessions";
        }
        workoutLog.setLoggedDate(LocalDate.now());
        workoutLog.setSession(session.get());
        workoutLogRepo.save(workoutLog);
        return "Workout log saved successfully";
    }

    public List<WorkoutLog> findByMemberNumber(String membershipNumber) {
        return workoutLogRepo.findByMemberNumber(membershipNumber);
    }

    public String deleteWorkoutLog(UUID id) {
        if (!workoutLogRepo.existsById(id)) {
            return "The workout log with that id does not exist";
        }
        workoutLogRepo.deleteById(id);
        return "Workout log deleted successfully";
    }
}