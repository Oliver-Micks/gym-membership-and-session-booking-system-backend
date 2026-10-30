package kigali.gym.rw.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.gym.rw.domain.Trainer;
import kigali.gym.rw.domain.Studio;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, UUID> {
    
    @Query("""
    SELECT t FROM Trainer t
    JOIN t.disciplines d
    WHERE LOWER(d.name) = LOWER(:name)
    """)
    List<Trainer> findByDiscipline(@Param("name") String name);

    boolean existsByStudio(Studio studio);
}