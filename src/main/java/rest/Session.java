package rest;
import main.AccountService;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * @author iu6team
 */

@Singleton
@Path("/session")
public class Session {
    private AccountService accountService;

    public Session(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkAuth(@Context HttpServletRequest request) {
        final String sessionId = request.getSession().getId();
        if (accountService.checkAuth(sessionId)) {
            UserProfile userTemp = accountService.giveProfileFromSessionId(sessionId);
            if (accountService.isExists(userTemp)) {
                long temp = accountService.getUser(userTemp.getLogin()).getId();
                return Response.status(Response.Status.OK).entity(accountService.getIdByJson(temp)).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserProfile user, @Context HttpHeaders headers, @Context HttpServletRequest request) {
        if (accountService.isExists(user)) {
            if (user.getPassword().equals(accountService.getUser(user.getLogin()).getPassword())) {
                final String sessionId = request.getSession().getId();
                accountService.addSession(sessionId, user);
                return Response.status(Response.Status.OK).entity(accountService.getIdByJson(accountService.getUser(user.getLogin()).getId())).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response logOut(@Context HttpServletRequest request){
        final String sessionId = request.getSession().getId();
        accountService.deleteSession(sessionId);
        return Response.status(Response.Status.OK).build();
    }
}
