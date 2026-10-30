package kigali.gym.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kigali.gym.rw.domain.Trainer;
import kigali.gym.rw.domain.Discipline;
import kigali.gym.rw.domain.Studio;
import kigali.gym.rw.repository.TrainerRepository;
import kigali.gym.rw.repository.DisciplineRepository;
import kigali.gym.rw.repository.StudioRepository;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepo;

    @Autowired
    private DisciplineRepository disciplineRepo;

    @Autowired
    private StudioRepository studioRepo;

    public String saveTrainer(Trainer trainer) {
        trainerRepo.save(trainer);
        return "Trainer saved successfully";
    }

    public List<Trainer> findAll() {
        return trainerRepo.findAll();
    }

    public Optional<Trainer> findById(UUID id) {
        return trainerRepo.findById(id);
    }

    public String updateTrainer(UUID id, Trainer trainer) {
        Optional<Trainer> old = trainerRepo.findById(id);
        if (old.isEmpty()) {
            return "The trainer with that id does not exist";
        }
        old.get().setFirstName(trainer.getFirstName());
        old.get().setLastName(trainer.getLastName());
        old.get().setPhone(trainer.getPhone());
        old.get().setStudio(trainer.getStudio());
        old.get().setDisciplines(trainer.getDisciplines());
        trainerRepo.save(old.get());
        return "Trainer updated successfully";
    }

    public String deleteTrainer(UUID id) {
        if (!trainerRepo.existsById(id)) {
            return "The trainer with that id does not exist";
        }
        trainerRepo.deleteById(id);
        return "Trainer deleted successfully";
    }

    public List<Trainer> findByDiscipline(String name) {
        return trainerRepo.findByDiscipline(name);
    }

    @Transactional
    public String addDiscipline(UUID trainerId,UUID disciplineId) {
        Optional<Trainer> trainer = trainerRepo.findById(trainerId);
        if (trainer.isEmpty()) {
            return "The trainer with that id does not exist";
        }
        Optional<Discipline> discipline = disciplineRepo.findById(disciplineId);
        if (discipline.isEmpty()) {
            return "That discipline does not exist";
        }
        if (trainer.get().getDisciplines().contains(discipline.get())) {
            return "The trainer already has that discipline";
        }
        trainer.get().getDisciplines().add(discipline.get());
        trainerRepo.save(trainer.get());
        return "Discipline added to the trainer";
    }

    public String changeStudio(UUID trainerId,int studioNumber) {
        Optional<Trainer> trainer = trainerRepo.findById(trainerId);
        if (trainer.isEmpty()) {
            return "The trainer with that id does not exist";
        }
        Optional<Studio> studio = studioRepo.findByStudioNumber(studioNumber);
        if (studio.isEmpty()) {
            return "The studio with that studioNumber does not exist";
        }
        if (trainerRepo.existsByStudio(studio.get())) {
            if (trainer.get().getStudio() == null || !trainer.get().getStudio().getId().equals(studio.get().getId())) {
                return "That studio is taken by another trainer";
            }
        }
        trainer.get().setStudio(studio.get());
        trainerRepo.save(trainer.get());
        return "Studio changed successfully";
    }
}