package kigali.gym.rw.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.gym.rw.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    List<Member> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
    
    @Query("""
    SELECT DISTINCT s.member FROM Session s
    WHERE s.trainer.id = :trainerId
    """)
    List<Member> findMembersOfTrainer(@Param("trainerId") UUID trainerId);
}