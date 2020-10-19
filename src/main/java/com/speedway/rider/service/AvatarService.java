package com.speedway.rider.service;

import com.speedway.rider.repository.AvatarRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class AvatarService {
    private AvatarRepository repository;

    @Inject
    public AvatarService(AvatarRepository repository){this.repository = repository;}

    public Optional<byte[]> find(Path path) {return this.repository.find(path);}

    public void create(byte[] img, Path path) {this.repository.create(img, path);}

    public void update(byte[] img, Path path){this.repository.update(img,path);}

    public void delete(Path path){this.repository.delete(path);}
}
