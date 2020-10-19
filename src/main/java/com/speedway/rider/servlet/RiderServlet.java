package com.speedway.rider.servlet;

import com.speedway.rider.dto.GetRiderResponse;
import com.speedway.rider.dto.GetRidersResponse;
import com.speedway.rider.entity.Rider;
import com.speedway.rider.service.RiderService;
import com.speedway.servlet.ServletUtility;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet(urlPatterns = RiderServlet.Paths.RIDER + "/*")
public class RiderServlet extends HttpServlet {

    private final RiderService service;

    @Inject
    public RiderServlet(RiderService service) {
        this.service = service;
    }

    public static class Paths {
        public static final String RIDER = "/api/riders";
    }

    public static class Patterns {
        public static final String RIDERS = "^/?$";
        public static final String RIDER = "^/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/?$";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if(Paths.RIDER.equals(servletPath)) {
            if(path.matches(Patterns.RIDER)) {
                getRider(req, resp);
                return;
            } else if(path.matches(Patterns.RIDERS)) {
                getRiders(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    private void getRider(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<Rider> rider = service.find(id);
        if(rider.isPresent()) {
            resp.getWriter()
                    .write(jsonb.toJson(GetRiderResponse.entityToDtoMapper().apply(rider.get())));
            resp.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void getRiders(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(service.findAll().size() > 0){
            resp.getWriter()
                    .write(jsonb.toJson(GetRidersResponse.entityToDtoMapper().apply(service.findAll())));
            resp.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
