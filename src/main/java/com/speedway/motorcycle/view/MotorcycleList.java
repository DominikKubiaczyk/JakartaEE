package com.speedway.motorcycle.view;

import com.speedway.motorcycle.model.MotorcyclesModel;
import com.speedway.motorcycle.service.MotorcycleService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@RequestScoped
@Named
public class MotorcycleList implements Serializable {
    private final MotorcycleService service;
    private MotorcyclesModel motorcycles;

    @Inject
    public MotorcycleList(MotorcycleService service){
        this.service = service;
    }

    public MotorcyclesModel getMotorcycles(String id){
        if(motorcycles == null){
            motorcycles = MotorcyclesModel.entityToModelMapper().apply(service.findMotorcycleByEngine(UUID.fromString(id)));
        }
        return motorcycles;
    }

    public String deleteMotorcycle(MotorcyclesModel.Motorcycle motorcycle){
        service.delete(motorcycle.getId());
        return "engine_type_view?faces-redirect=true&includeViewParams=true";
    }
}
