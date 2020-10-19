package com.speedway.engine.model;

import com.speedway.engine.entity.EngineType;
import com.speedway.engine.service.EngineTypeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

@FacesConverter(forClass = EngineTypeModel.class, managed = true)
public class EngineTypeModelConverter implements Converter<EngineTypeModel> {

    private EngineTypeService service;

    @Inject
    public EngineTypeModelConverter(EngineTypeService service){
        this.service = service;
    }

    @Override
    public EngineTypeModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s == null || s.isBlank()){
            return null;
        }
        Optional<EngineType> engineType = service.findByName(s);
        return engineType.isEmpty() ? null : EngineTypeModel.entityToModelMapper().apply(engineType.get());
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, EngineTypeModel engineTypeModel) {
        if(engineTypeModel == null){
            System.out.println("");
        } else {
            System.out.println(engineTypeModel);
        }
        return engineTypeModel == null ? "" : String.format("%s %s",engineTypeModel.getProducer().toString(), Integer.toString(engineTypeModel.getSize()));
    }
}
