package kigali.gym.rw.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.gym.rw.domain.Studio;

@Repository
public interface StudioRepository extends JpaRepository<Studio, UUID> {
    
    @Query("""
    SELECT s FROM Studio s
    WHERE NOT EXISTS (SELECT t FROM Trainer t WHERE t.studio = s)
    ORDER BY s.studioNumber ASC
    """)
    List<Studio> findFreeStudios();
}