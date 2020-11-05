package com.speedway.engine.repository;

import com.speedway.datastore.DataStore;
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

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<EngineType> find(UUID id) {
        return Optional.ofNullable(em.find(EngineType.class, id));
    }

    @Override
    public List<EngineType> findAll() {
        return em.createQuery("select e from EngineType e", EngineType.class).getResultList();
    }


    public Optional<EngineType> findByName(String name){
        String[] nameTab = name.split(" ");
        EngineProducers producer = EngineProducers.valueOf(nameTab[0]);
        int size = Integer.parseInt(nameTab[1]);
        System.out.println(producer);
        System.out.println(size);
        try{
            return Optional.of(em.createQuery("select e from EngineType e where e.producer = :producer and e.size = :size", EngineType.class)
                    .setParameter("producer", producer)
                    .setParameter("size", size)
                    .getSingleResult());
        } catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public void create(EngineType entity) {
        em.persist(entity);
    }

    @Override
    public void delete(UUID id) {
        em.remove(em.find(EngineType.class, id));
    }

    @Override
    public void update(EngineType entity) {
        em.merge(entity);
    }
}
