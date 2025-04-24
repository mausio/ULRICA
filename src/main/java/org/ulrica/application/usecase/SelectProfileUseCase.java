package org.ulrica.application.usecase;

import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.ProfileSelectionService;
import org.ulrica.domain.repository.CarProfileRepository;

public class SelectProfileUseCase {
    private final CarProfileRepository repository;
    private final ProfileSelectionService selectionService;

    public SelectProfileUseCase(CarProfileRepository repository, ProfileSelectionService selectionService) {
        this.repository = repository;
        this.selectionService = selectionService;
    }

    public void execute(String profileName) {
        CarProfile profile = repository.findByName(profileName);
        if (profile != null) {
            selectionService.selectProfile(profile);
        }
    }
} 