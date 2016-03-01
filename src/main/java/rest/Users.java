package rest;

import main.AccountService;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * @author iu6team
 */
@Singleton
@Path("/user")
public class Users {
    private AccountService accountService;

    public Users(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        final Collection<UserProfile> allUsers = accountService.getAllUsers();
        return Response.status(Response.Status.OK).entity(allUsers.toArray(new UserProfile[allUsers.size()])).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") long id, @Context HttpServletRequest request) {
        final String sessionId = request.getSession().getId();
        if (accountService.checkAuth(sessionId)) {
            final UserProfile user = accountService.getUserById(id);
            if (user == null) {
                return Response.status(Response.Status.FORBIDDEN).build();
            } else {
                return Response.status(Response.Status.OK).entity(user).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserProfile user, @Context HttpHeaders headers) {
        if (accountService.addUser(user.getLogin(), user)) {
            return Response.status(Response.Status.OK).entity(user.getLogin()).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(@PathParam("id") long id, UserProfile user, @Context HttpServletRequest request) {
        final String sessionId = request.getSession().getId();
        if (accountService.checkAuth(sessionId)) {
            accountService.editUser(id, user);
            return Response.status(Response.Status.OK).entity(accountService.getIdByJson(id)).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("wrong user").build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") long id, @Context HttpServletRequest request) {
        final String sessionId = request.getSession().getId();
        if (accountService.checkAuth(sessionId)) {
            accountService.deleteUser(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("wrong user").build();
        }
    }
}
