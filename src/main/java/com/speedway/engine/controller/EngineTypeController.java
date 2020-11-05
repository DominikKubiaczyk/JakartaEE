package com.speedway.engine.controller;

import com.speedway.engine.dto.CreateEngineTypeRequest;
import com.speedway.engine.dto.GetEngineTypeResponse;
import com.speedway.engine.dto.GetEngineTypesResponse;
import com.speedway.engine.dto.UpdateEngineTypeRequest;
import com.speedway.engine.entity.EngineType;
import com.speedway.engine.service.EngineTypeService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@Path("/enginetypes")
public class EngineTypeController {

    private EngineTypeService service;

    @Inject
    public void setService(EngineTypeService service){
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEngineTypes() {
        List<EngineType> engineTypes = service.findAll();
        return Response.ok(GetEngineTypesResponse.entityToDtoMapper()
                                    .apply(engineTypes)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEngineType(@PathParam("id") UUID id){
        Optional<EngineType> engineType = service.find(id);
        if(engineType.isPresent()){
            return Response.ok(GetEngineTypeResponse.entityToDtoMapper()
                                        .apply(engineType.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postEngineType(CreateEngineTypeRequest request){
        if(!checkIfEngineTypeAlreadyExists(request.getProducer().toString() + " " + request.getSize())){
            EngineType engineType = CreateEngineTypeRequest.dtoToEntityMapper().apply(request);
            service.create(engineType);
            return Response.created(URI.create("/enginetypes/" + engineType.getId())).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putEngineType(@PathParam("id") UUID id, UpdateEngineTypeRequest request){
        if(checkIfCanModifyEngineType(request.getProducer().toString() + " " + request.getSize(), id)){
            return Response.status(Response.Status.CONFLICT).build();
        }
        Optional<EngineType> engineType = service.find(id);
        if(engineType.isPresent()){
            UpdateEngineTypeRequest.dtoToMapperUpdater().apply(engineType.get(), request);
            service.update(engineType.get());
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEngineType(@PathParam("id") UUID id){
        Optional<EngineType> engineType = service.find(id);
        if(engineType.isPresent()){
            service.delete(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public boolean checkIfEngineTypeAlreadyExists(String producerAndSize){
        return service.findByName(producerAndSize).isPresent();
    }

    public boolean checkIfCanModifyEngineType(String producerAndSize, UUID id){
        return service.findByName(producerAndSize).isPresent() && !service.findByName(producerAndSize).get().getId().equals(id);
    }
}
