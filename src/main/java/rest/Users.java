package rest;

import db.UserDataSet;
import main.AccountService;
import main.AccountServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * @author iu6team
 */
@Singleton
@Path("/user")
public class Users {
    @Inject
    private main.Context context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        final AccountService accountService = context.get(AccountService.class);
        final Collection<UserDataSet> allUsers = accountService.getAllUsers();
        return Response.status(Response.Status.OK).entity(allUsers.toArray(new UserDataSet[allUsers.size()])).build();
    }
/*
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") long id, @Context HttpServletRequest request) {
        final AccountService accountService = context.get(AccountService.class);
        final String sessionId = request.getSession().getId();
        if (accountService.checkAuth(sessionId)) {
            final UserProfile userTemp = accountService.getUserById(id);
            if (userTemp == null) {
                return Response.status(Response.Status.FORBIDDEN).build();
            } else {
                return Response.status(Response.Status.OK).entity(accountService.toJson(userTemp)).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
*/
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserDataSet user) {
        final AccountService accountService = context.get(AccountService.class);
        if (accountService.addUser(user)) {
            return Response.status(Response.Status.OK).entity(accountService.getIdByJson(user.getId())).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
/*
    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(@PathParam("id") long id, UserProfile user, @Context HttpServletRequest request) {
        final AccountService accountService = context.get(AccountService.class);
        final String sessionId = request.getSession().getId();
        UserProfile userTemp = accountService.giveProfileFromSessionId(sessionId);
        if ((user != null)&&(userTemp.getId() == accountService.getUserById(id).getId())){
            accountService.editUser(id, user, sessionId);
            return Response.status(Response.Status.OK).entity(accountService.getIdByJson(id)).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity(accountService.toJsonError("wrong user")).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") long id, @Context HttpServletRequest request) {
        final AccountService accountService = context.get(AccountService.class);
        final String sessionId = request.getSession().getId();
        UserDataSet user = accountService.getUser(sessionId);
        if ((accountService.checkAuth(sessionId))&&(user.getId() == accountService.getUserById(id).getId())) {
            accountService.deleteUser(id);
            accountService.deleteSession(sessionId);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity(accountService.toJsonError("wrong user")).build();
        }
    }
    */
}
