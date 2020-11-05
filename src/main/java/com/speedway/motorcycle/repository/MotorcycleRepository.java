package com.speedway.motorcycle.repository;

import com.speedway.datastore.DataStore;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class MotorcycleRepository implements Repository<Motorcycle, UUID> {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Motorcycle> find(UUID id) {
        return Optional.ofNullable(entityManager.find(Motorcycle.class, id));
    }

    @Override
    public List<Motorcycle> findAll() {
        return entityManager.createQuery("select m from Motorcycle m", Motorcycle.class).getResultList();
    }

    @Override
    public void create(Motorcycle entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(UUID id) {
        entityManager.remove(entityManager.find(Motorcycle.class, id));
    }

    @Override
    public void update(Motorcycle entity) {
        entityManager.merge(entity);
    }

    public List<Motorcycle> findMotorcycleByEngine(UUID id){
        try{
            return entityManager.createQuery("select m from Motorcycle m where m.engineType.id = :id", Motorcycle.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}
