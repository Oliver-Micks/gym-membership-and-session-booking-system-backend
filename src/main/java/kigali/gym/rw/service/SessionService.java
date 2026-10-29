package kigali.gym.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.gym.rw.domain.Session;
import kigali.gym.rw.repository.SessionRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepo;

    public String saveSession(Session session) {
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
        old.get().setStatus(session.getStatus());
        old.get().setMember(session.getMember());
        old.get().setTrainer(session.getTrainer());
        sessionRepo.save(old.get());
        return "Session updated successfully";
    }

    public String deleteSession(UUID id) {
        if (!sessionRepo.existsById(id)) {
            return "The session with that id does not exist";
        }
        sessionRepo.deleteById(id);
        return "Session deleted successfully";
    }
}