package com.speedway.repository;

import java.util.Optional;

public interface PathRepository<E, K> {
    Optional<E> find(K path);
    void create(E img, K path);
    void update(E img, K path);
    void delete(K path);
}
