package com.speedway.motorcycle.controller;

import com.speedway.engine.entity.EngineType;
import com.speedway.engine.service.EngineTypeService;
import com.speedway.motorcycle.dto.CreateMotorcycleRequest;
import com.speedway.motorcycle.dto.GetMotorcycleResponse;
import com.speedway.motorcycle.dto.GetMotorcyclesResponse;
import com.speedway.motorcycle.dto.UpdateMotorcycleRequest;
import com.speedway.motorcycle.entity.Motorcycle;
import com.speedway.motorcycle.service.MotorcycleService;
import com.speedway.serialization.CloningUtility;
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
@Path("/enginetypes/{engineId}/motorcycles")
public class MotorcycleController {
    private MotorcycleService service;
    private EngineTypeService engineTypeService;

    @Inject
    public void setService(MotorcycleService service, EngineTypeService engineTypeService) {
        this.service = service;
        this.engineTypeService = engineTypeService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMotorcycles(@PathParam("engineId") UUID engineId) {
        if (checkIfEngineTypeIsPresent(engineId)) {
            List<Motorcycle> motorcycles = service.findMotorcycleByEngine(engineId);
            return Response.ok(GetMotorcyclesResponse.entityToDtoMapper().apply(motorcycles)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMotorcycle(@PathParam("engineId") UUID engineId, @PathParam("id") UUID id) {
        if (checkIfEngineTypeIsPresent(engineId) && checkIfMotorcycleExistsWithEngineType(id, engineId)) {
            Optional<Motorcycle> motorcycle = service.find(id);
            return Response.ok(GetMotorcycleResponse.entityToDtoMapper().apply(motorcycle.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postMotorcycle(@PathParam("engineId") UUID engineId, CreateMotorcycleRequest request) {
        if (checkIfEngineTypeIsPresent(engineId)) {
            Optional<EngineType> engineType = engineTypeService.find(engineId);
            Motorcycle motorcycle = CreateMotorcycleRequest
                    .dtoToEntityMapper(engineType.get())
                    .apply(request);
            service.create(motorcycle);
            return Response.created(URI.create("/enginetypes/" + engineId.toString() + "/motorcycles/" + motorcycle.getId().toString())).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putMotorcycle(@PathParam("engineId") UUID engineId,@PathParam("id") UUID id, UpdateMotorcycleRequest request){
            if(checkIfEngineTypeIsPresent(engineId) && checkIfMotorcycleExistsWithEngineType(id, engineId)){
                Optional<Motorcycle> motorcycle = service.find(id);
                UpdateMotorcycleRequest.dtoToEntityUpdater().apply(motorcycle.get(), request);
                service.update(motorcycle.get());
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMotorcycle(@PathParam("engineId") UUID engineId, @PathParam("id") UUID id){
        if(checkIfEngineTypeIsPresent(engineId) && checkIfMotorcycleExistsWithEngineType(id, engineId)){
                service.delete(id);
                return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    public boolean checkIfEngineTypeIsPresent(UUID engineId){
        return engineTypeService.find(engineId).isPresent();
    }

    public boolean checkIfMotorcycleExistsWithEngineType(UUID motorcycleId, UUID engineTypeId){
        Optional<Motorcycle> motorcycle = service.findMotorcycleByEngine(engineTypeId).stream()
                                                    .filter(motorcycle1 -> motorcycle1.getId().equals(motorcycleId))
                                                    .findFirst()
                                                    .map(CloningUtility::clone);
        return motorcycle.isPresent();
    }
}
