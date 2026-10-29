package kigali.gym.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.gym.rw.domain.Discipline;
import kigali.gym.rw.repository.DisciplineRepository;

@Service
public class DisciplineService {
    
    @Autowired 
    private DisciplineRepository disciplineRepo;

    public String saveDiscipline(Discipline discipline) {
        disciplineRepo.save(discipline);
        return "Discipline saved successfully";
    }

    public List<Discipline> findAll() {
        return disciplineRepo.findAll();
    }

    public Optional<Discipline> findById(UUID id) {
        return disciplineRepo.findById(id);
    }

    public String updateDiscipline(UUID id, Discipline discipline) {
        Optional<Discipline> old = disciplineRepo.findById(id);
        if (old.isEmpty()) {
            return "The discipline with that id does not exist";
        }
        old.get().setName(discipline.getName());
        disciplineRepo.save(old.get());
        return "Discipline updated successfully";
    }

    public String deleteDiscipline(UUID id) {
        if (!disciplineRepo.existsById(id)) {
            return "The discipline with that id does not exist";
        }
        disciplineRepo.deleteById(id);
        return "Discipline deleted successfully";
    }
}
