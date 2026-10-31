package kigali.gym.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import kigali.gym.rw.domain.Session;
import kigali.gym.rw.repository.SessionRepository;
import kigali.gym.rw.domain.SessionStatus;
import kigali.gym.rw.repository.WorkoutLogRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private WorkoutLogRepository workoutLogRepo;

    public String saveSession(Session session) {
        if (session.getSessionDate().isBefore(LocalDate.now())) {
            return "Session date cannot be in the past";
        }
        boolean booked = sessionRepo.existsByTrainerIdAndSessionDateAndStatusNot(session.getTrainer().getId(),session.getSessionDate(),SessionStatus.CANCELLED);
        if (booked) {
            return "Trainer is already booked on that date";
        }
        session.setStatus(SessionStatus.BOOKED);
        sessionRepo.save(session);
        return "Session saved successfully";
    }

    public List<Session> findAll() {
        return sessionRepo.findAll();
    }

    public Optional<Session> findById(UUID id) {
        return sessionRepo.findById(id);
    }

    public String updateSession(UUID id, Session session) {
        Optional<Session> old = sessionRepo.findById(id);
        if (old.isEmpty()) {
            return "The session with that id does not exist";
        }
        old.get().setSessionDate(session.getSessionDate());
        old.get().setTimeSlot(session.getTimeSlot());
        old.get().setMember(session.getMember());
        old.get().setTrainer(session.getTrainer());
        sessionRepo.save(old.get());
        return "Session updated successfully";
    }

    public String deleteSession(UUID id) {
        Optional<Session> session = sessionRepo.findById(id);
        if (session.isEmpty()) {
            return "The session with that id does not exist";
        }
        if (workoutLogRepo.existsBySession(session.get())) {
            return "That session has workout logs, delete them first";
        }
        sessionRepo.deleteById(id);
        return "Session deleted successfully";
    }

    public List<Session> findByStatus(SessionStatus status) {
        return sessionRepo.findByStatusOrderBySessionDateAsc(status);
    }

    public List<Session> findBetween(LocalDate start,LocalDate end) {
        return sessionRepo.findBySessionDateBetweenOrderBySessionDateAsc(start,end);
    }

    public String changeStatus(UUID id,SessionStatus newStatus) {
        Optional<Session> session = sessionRepo.findById(id);
        if (session.isEmpty()) {
            return "The session with that id does not exist";
        }
        SessionStatus oldStatus = session.get().getStatus();
        if ((oldStatus == SessionStatus.BOOKED && (newStatus == SessionStatus.CHECKED_IN || newStatus == SessionStatus.CANCELLED)) ||
            (oldStatus == SessionStatus.CHECKED_IN && (newStatus == SessionStatus.ATTENDED || newStatus == SessionStatus.CANCELLED))) {
            session.get().setStatus(newStatus);
            sessionRepo.save(session.get());
            return "Session status updated successfully";
        }
        return "Cannot change status from " + oldStatus + " to " + newStatus;
    }

    public List<Object[]> countSessionsByStatus() {
        return sessionRepo.countSessionsByStatus();
    }

    @Transactional
    public String cancelDay(UUID trainerId,LocalDate date) {
        int count = sessionRepo.cancelDay(trainerId,date,SessionStatus.ATTENDED,SessionStatus.CANCELLED);
        return count + " sessions cancelled";
    }
}