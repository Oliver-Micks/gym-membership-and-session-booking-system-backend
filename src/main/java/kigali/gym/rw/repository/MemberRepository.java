package kigali.gym.rw.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kigali.gym.rw.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    List<Member> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}