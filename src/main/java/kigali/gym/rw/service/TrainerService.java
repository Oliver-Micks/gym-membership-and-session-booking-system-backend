package kigali.gym.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.gym.rw.domain.Trainer;
import kigali.gym.rw.repository.TrainerRepository;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepo;

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
}