package com.speedway.rider.repository;

import com.speedway.repository.PathRepository;

import javax.enterprise.context.Dependent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Dependent
public class AvatarRepository implements PathRepository<byte[], Path> {

    @Override
    public Optional<byte[]> find(Path path) {
        try{
            byte[] byteArray = Files.readAllBytes(path);
            return Optional.of(byteArray);
        } catch (IOException | NullPointerException e) {
            System.out.println("Wyjątek podczas szukania pliku!");
        }
        return Optional.empty();
    }

    @Override
    public void create(byte[] img, Path path) {
        try{
            Files.write(path, img);
        } catch(IOException e){
            System.out.println("Wyjątek podczas tworzenia pliku!");
        }
    }

    @Override
    public void update(byte[] img, Path path) {
        try{
            InputStream is = new ByteArrayInputStream(img);
            Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e){
            System.out.println("Wyjątek podczas nadpisywania pliku!");
        }
    }

    @Override
    public void delete(Path path) {
        try{
            Files.deleteIfExists(path);
        } catch(IOException e){
            System.out.println("Wyjątek podczas usuwania pliku!");
        }
    }
}
