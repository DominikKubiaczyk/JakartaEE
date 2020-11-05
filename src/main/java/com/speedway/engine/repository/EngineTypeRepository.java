package com.speedway.engine.repository;

import com.speedway.engine.entity.EngineProducers;
import com.speedway.engine.entity.EngineType;
import com.speedway.repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class EngineTypeRepository implements Repository<EngineType, UUID> {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<EngineType> find(UUID id) {
        return Optional.ofNullable(entityManager.find(EngineType.class, id));
    }

    @Override
    public List<EngineType> findAll() {
        return entityManager.createQuery("select e from EngineType e", EngineType.class).getResultList();
    }

    public Optional<EngineType> findByName(String name){
        String[] nameTab = name.split(" ");
        EngineProducers producer = EngineProducers.valueOf(nameTab[0]);
        int size = Integer.parseInt(nameTab[1]);
        try{
            return Optional.of(entityManager.createQuery("select e from EngineType e where e.producer = :producer and e.size = :size", EngineType.class)
                    .setParameter("producer", producer)
                    .setParameter("size", size)
                    .getSingleResult());
        } catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public void create(EngineType entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(UUID id) {
        entityManager.remove(entityManager.find(EngineType.class, id));
    }

    @Override
    public void update(EngineType entity) {
        entityManager.merge(entity);
    }
}
