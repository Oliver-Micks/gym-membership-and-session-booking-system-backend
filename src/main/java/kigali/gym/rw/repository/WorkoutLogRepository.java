package kigali.gym.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.gym.rw.domain.Session;
import kigali.gym.rw.domain.WorkoutLog;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, UUID> {

    @Query("""
    SELECT w FROM WorkoutLog w 
    WHERE w.session.member.membershipNumber = :membershipNumber 
    ORDER BY w.loggedDate DESC
    """)
    List<WorkoutLog> findByMemberNumber(@Param("membershipNumber") String membershipNumber);

    boolean existsBySession(Session session);
}