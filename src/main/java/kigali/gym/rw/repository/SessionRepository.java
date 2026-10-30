package kigali.gym.rw.repository;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kigali.gym.rw.domain.Session;
import kigali.gym.rw.domain.SessionStatus;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findByStatusOrderBySessionDateAsc(SessionStatus status);
    List<Session> findBySessionDateBetweenOrderBySessionDateAsc(LocalDate start,LocalDate end);
    
    boolean existsByTrainerIdAndSessionDateAndStatusNot(UUID trainerId,LocalDate sessionDate,SessionStatus status);
}