package com.speedway.engine.view;

import com.speedway.engine.entity.EngineType;
import com.speedway.engine.model.EngineTypeModel;
import com.speedway.engine.service.EngineTypeService;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.motorcycle.service.MotorcycleService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class EngineTypeView implements Serializable {
    private final EngineTypeService service;

    @Getter
    @Setter
    private String id;

    @Getter
    private EngineTypeModel engineType;

    @Inject
    public EngineTypeView(EngineTypeService service){
        this.service = service;
    }

    public void init(){
        Optional<EngineType> engineType = service.find(UUID.fromString(id));
        engineType.ifPresentOrElse(
                engineT -> {
                    this.engineType = EngineTypeModel.entityToModelMapper().apply(engineT);
                },
                () -> {
                    try {
                        FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Engine type not found");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
