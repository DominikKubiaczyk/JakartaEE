package com.speedway.motorcycle.view;

import com.speedway.engine.model.EngineTypeModel;
import com.speedway.engine.service.EngineTypeService;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.motorcycle.model.MotorcycleEditModel;
import com.speedway.motorcycle.service.MotorcycleService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class MotorcycleEdit implements Serializable {
    private final MotorcycleService service;
    private final EngineTypeService engineTypeService;

    @Getter
    @Setter
    private String id;

    @Getter
    private MotorcycleEditModel motorycle;

    @Getter
    private List<EngineTypeModel> engineTypes;

    @Inject
    public MotorcycleEdit(MotorcycleService service, EngineTypeService engineTypeService){
        this.service = service;
        this.engineTypeService = engineTypeService;
    }

    public void init() {
        Optional<Motorcycle> motorcycle = service.find(UUID.fromString(id));
        motorcycle.ifPresentOrElse(
                motor -> {
                    this.motorycle = MotorcycleEditModel.entityToModelMapper(engineType -> String.format("%s %s",engineType.getProducer().toString(), Integer.toString(engineType.getSize()))).apply(motor);
                },
                () -> {
                    try {
                        FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Motorcycle not found");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        );
        engineTypes = engineTypeService.findAll().stream()
                .filter(engineType -> !(engineType.getProducer().toString() + " " + engineType.getSize()).equals(this.motorycle.getEngineType()))
                .map(EngineTypeModel.entityToModelMapper())
                .collect(Collectors.toList());
    }

    public String saveAction() {
        System.out.println("PPPPFFFFF " + motorycle.getEngineType());

        service.update(MotorcycleEditModel.modelToEntityMapper(engineType ->
            engineTypeService.findByName(engineType).orElseThrow())
                .apply(service.find(UUID.fromString(id)).orElseThrow(), motorycle));
        return "/engineType/engine_type_list.xhtml?faces-redirect=true";
    }
}
