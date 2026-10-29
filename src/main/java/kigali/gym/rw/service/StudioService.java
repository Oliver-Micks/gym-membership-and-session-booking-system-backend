package kigali.gym.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.gym.rw.domain.Studio;
import kigali.gym.rw.repository.StudioRepository;

@Service
public class StudioService {

    @Autowired
    private StudioRepository studioRepo;

    public String saveStudio(Studio studio) {
        studioRepo.save(studio);
        return "Studio saved successfully";
    }

    public List<Studio> findAll() {
        return studioRepo.findAll();
    }

    public Optional<Studio> findById(UUID id) {
        return studioRepo.findById(id);
    }

    public String updateStudio(UUID id, Studio studio) {
        Optional<Studio> old = studioRepo.findById(id);
        if (old.isEmpty()) {
            return "The studio with that id does not exist";
        }
        old.get().setStudioNumber(studio.getStudioNumber());
        old.get().setName(studio.getName());
        studioRepo.save(old.get());
        return "Studio updated successfully";
    }

    public String deleteStudio(UUID id) {
        if (!studioRepo.existsById(id)) {
            return "The studio with that id does not exist";
        }
        studioRepo.deleteById(id);
        return "Studio deleted successfully";
    }
}