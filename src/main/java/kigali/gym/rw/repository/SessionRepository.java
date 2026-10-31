package kigali.gym.rw.repository;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.gym.rw.domain.Session;
import kigali.gym.rw.domain.SessionStatus;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findByStatusOrderBySessionDateAsc(SessionStatus status);
    List<Session> findBySessionDateBetweenOrderBySessionDateAsc(LocalDate start,LocalDate end);
    
    boolean existsByTrainerIdAndSessionDateAndStatusNot(UUID trainerId,LocalDate sessionDate,SessionStatus status);

    @Query("""
        SELECT s.status, COUNT(s) FROM Session s
        GROUP BY s.status
        """)
    List<Object[]> countSessionsByStatus();

    @Modifying
    @Query("""
        UPDATE Session s SET s.status = :cancelled
        WHERE s.trainer.id = :trainerId AND s.sessionDate = :date
        AND s.status <> :attended AND s.status <> :cancelled
        """)
        int cancelDay(@Param("trainerId") UUID trainerId,@Param("date") LocalDate date,@Param("attended") SessionStatus attended,@Param("cancelled") SessionStatus cancelled);
}