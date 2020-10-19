package com.speedway.engine.view;

import com.speedway.engine.entity.EngineType;
import com.speedway.engine.model.EngineTypesModel;
import com.speedway.engine.service.EngineTypeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class EngineTypesList implements Serializable {
    private final EngineTypeService service;
    private EngineTypesModel engineTypes;

    @Inject
    public EngineTypesList(EngineTypeService service){
        this.service = service;
    }

    public EngineTypesModel getEngineTypes() {
        if(engineTypes == null){
            engineTypes = EngineTypesModel.entityToModelMapper().apply(service.findAll());
        }
        return engineTypes;
    }

    public String deleteEngineType(EngineTypesModel.EngineType engineType){
        service.delete(engineType.getId());
        return "engine_type_list?faces-redirect=true&includeViewParams=true";
    }

}
