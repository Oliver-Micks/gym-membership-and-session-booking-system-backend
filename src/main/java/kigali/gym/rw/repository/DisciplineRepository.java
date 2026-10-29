package kigali.gym.rw.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kigali.gym.rw.domain.Discipline;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, UUID> {

}