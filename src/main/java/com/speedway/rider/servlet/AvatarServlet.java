package com.speedway.rider.servlet;

import com.speedway.rider.entity.Rider;
import com.speedway.rider.service.AvatarService;
import com.speedway.rider.service.RiderService;
import com.speedway.servlet.ServletUtility;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;


@WebServlet(urlPatterns = AvatarServlet.Paths.AVATARS + "/*", initParams = {
        @WebInitParam(name = "location", value = "C:\\Users\\User\\Desktop\\AvatarsJEE")
})
@MultipartConfig
public class AvatarServlet extends HttpServlet {

    private final RiderService riderService;
    private final AvatarService avatarService;
    private String avatarLocation;

    @Inject
    public AvatarServlet(RiderService riderService, AvatarService avatarService){
        this.riderService = riderService; this.avatarService = avatarService;
    }

    public static class Paths {
        public static final String AVATARS = "/api/avatars";
    }

    public static class Patterns {
        public static final String AVATAR = "^/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/?$";
    }

    public static class Parameters {
        public static final String AVATAR = "avatar";
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        avatarLocation = config.getInitParameter("location");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if(Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                getAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getAvatar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/",""));
        Optional<Rider> rider = riderService.find(id);
        if(rider.isPresent()){
            Path path = java.nio.file.Paths.get(avatarLocation + "\\" + id.toString() + ".png");
            Optional<byte[]> optional = avatarService.find(path);
            if(optional.isPresent()){
                byte[] byteArray = optional.get();
                resp.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
                resp.setContentLength(byteArray.length);
                resp.getOutputStream().write(byteArray);
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if(Paths.AVATARS.equals(servletPath)) {
            if(path.matches(Patterns.AVATAR)) {
                postAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void postAvatar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/",""));
        Optional<Rider> rider = riderService.find(id);
        if(rider.isPresent()){
            Part avatar = req.getPart(Parameters.AVATAR);
            if(rider.get().getAvatarPath() == null){
                if(avatar != null && avatar.getInputStream().readAllBytes().length > 0){
                    avatarService.create(avatar.getInputStream().readAllBytes(), java.nio.file.Paths.get(avatarLocation + "\\" + id.toString() + ".png"));
                    riderService.updateAvatar(id, java.nio.file.Paths.get(avatarLocation + "\\" + id.toString() + ".png"));
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    return;
                }
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if(Paths.AVATARS.equals(servletPath)) {
            if(path.matches(Patterns.AVATAR)) {
                putAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void putAvatar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/",""));
        Optional<Rider> rider = riderService.find(id);
        if(rider.isPresent()){
            if(rider.get().getAvatarPath() != null){
                Part avatar = req.getPart(Parameters.AVATAR);
                if(avatar != null && avatar.getInputStream().readAllBytes().length > 0){
                    System.out.println(avatar.getInputStream().readAllBytes().length);
                    avatarService.update(avatar.getInputStream().readAllBytes(), java.nio.file.Paths.get(avatarLocation + "\\" + id.toString() + ".png"));
                    riderService.updateAvatar(id, java.nio.file.Paths.get(avatarLocation + "\\" + id.toString() + ".png"));
                    resp.setStatus(HttpServletResponse.SC_OK);
                    return;
                }
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if(Paths.AVATARS.equals(servletPath)) {
            if(path.matches(Patterns.AVATAR)) {
                deleteAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void deleteAvatar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/",""));
        Optional<Rider> rider = riderService.find(id);
        if(rider.isPresent()){
            if(rider.get().getAvatarPath() != null){
                avatarService.delete(java.nio.file.Paths.get(avatarLocation + "\\" + id.toString() + ".png"));
                riderService.updateAvatar(id, null);
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
