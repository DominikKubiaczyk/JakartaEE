package com.speedway.motorcycle.view;

import com.speedway.engine.entity.EngineType;
import com.speedway.engine.model.EngineTypeModel;
import com.speedway.engine.service.EngineTypeService;
import com.speedway.motorcycle.model.MotorcycleCreateModel;
import com.speedway.motorcycle.service.MotorcycleService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
@Named
@NoArgsConstructor
public class MotorcycleCreate implements Serializable {
    private MotorcycleService service;
    private EngineTypeService engineTypeService;

    @Setter
    @Getter
    private String id;

    @Getter
    private MotorcycleCreateModel motorcycle;

    @Getter
    private List<EngineTypeModel> engineTypes;

    @Inject
    public MotorcycleCreate(MotorcycleService service, EngineTypeService engineTypeService){
        this.service = service;
        this.engineTypeService = engineTypeService;
    }

    @PostConstruct
    public void init(){
        engineTypes = engineTypeService.findAll().stream()
                .map(EngineTypeModel.entityToModelMapper())
                .collect(Collectors.toList());
        motorcycle = MotorcycleCreateModel.builder().build();
    }

    public String cancelAction(){
        return "/engineType/engine_type_list?faces-redirect=true";
    }

    public String saveAction(){
        service.create(MotorcycleCreateModel.modelToEntityMapper(
                engineType -> engineTypeService.findByName(engineType).orElseThrow()).apply(motorcycle));
        return "/engineType/engine_type_list?faces-redirect=true";
    }

}
