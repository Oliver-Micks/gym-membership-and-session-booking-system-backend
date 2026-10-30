package kigali.gym.rw.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.gym.rw.domain.Discipline;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, UUID> {

    @Query("""
    SELECT d FROM Discipline d
    WHERE d.trainers IS EMPTY
    """)
    List<Discipline> findUnusedDisciplines();
}