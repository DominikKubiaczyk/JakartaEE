package com.speedway.rider.service;

import com.speedway.rider.entity.Rider;
import com.speedway.rider.repository.RiderRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class RiderService {

    private RiderRepository repository;

    @Inject
    public RiderService(RiderRepository repository) {
        this.repository = repository;
    }

    public List<Rider> findAll() {
        return repository.findAll();
    }

    public Optional<Rider> find(UUID id) {
        return repository.find(id);
    }

    public void create(Rider rider) {
        this.repository.create(rider);
    }

    public void updateAvatar(UUID id, Path avatarPath) {
        repository.find(id).ifPresent(rider -> {
            try {
                if(avatarPath != null){
                    rider.setAvatarPath(avatarPath.toString());
                } else {
                    rider.setAvatarPath(null);
                }
                this.repository.update(rider);
            } catch (IllegalStateException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }
}
