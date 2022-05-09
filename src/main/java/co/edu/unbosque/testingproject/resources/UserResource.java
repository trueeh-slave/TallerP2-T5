package co.edu.unbosque.testingproject.resources;

import co.edu.unbosque.testingproject.dto.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static co.edu.unbosque.testingproject.services.UserService.*;

@Path("/users")
public class UserResource {

    @Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            List<User> users = getUsers();

            return Response.ok().entity(users).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        String contextPath = context.getRealPath("") + File.separator;
        try {
            user = createUser(user.getUsername(), user.getPassword(), user.getRole(), user.getCoins(), contextPath);
            return Response.created(UriBuilder.fromResource(UserResource.class).path(user.getUsername()).build()).entity(user).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

/*    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public User getUserByUsername(@PathParam("username") String username) {
        try {
            List<User> users = getUsers();
            return users.stream().filter(userFound -> userFound.getUsername().equals(username)).findFirst().orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/
/*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public Response get(@PathParam("username") String username) {
        try {
            List<User> users = getUsers();
            User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);

//            if (user != null) {
            var test = Response.ok().entity(user).build();
            System.out.println("test = " + test);
            return Response.ok().entity(user).build();
//            } else {
//                return Response
//                return Response.status(404).entity("User not found").build();
//            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }*/

}
