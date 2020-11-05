package com.speedway.motorcycle.view;

import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.motorcycle.model.MotorcycleModel;
import com.speedway.motorcycle.service.MotorcycleService;
import lombok.Getter;
import lombok.Setter;

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
public class MotorcycleView implements Serializable {
    private final MotorcycleService service;

    @Getter
    @Setter
    private String id;

    @Getter
    private MotorcycleModel motorcycle;

    @Inject
    public MotorcycleView(MotorcycleService service){
        this.service = service;
    }


    public void init() throws IOException {
        if(id == null || !id.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")){
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
                                                                                                    "Invalid id");
        } else {
            Optional<Motorcycle> motorcycle = service.find(UUID.fromString(id));
            motorcycle.ifPresentOrElse(
                    motor -> {
                        this.motorcycle = MotorcycleModel.entityToModelMapper().apply(motor);
                    },
                    () -> {
                        try {
                            FacesContext.getCurrentInstance().getExternalContext()
                                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Motorcycle not found");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }
}
