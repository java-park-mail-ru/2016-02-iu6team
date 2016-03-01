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
 * Created by e.shubin on 25.02.2016.
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
        if (accountService.checkAuth(sessionId)){
          //  long temp = accountService.checkExists(user);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserProfile user, @Context HttpHeaders headers, @Context HttpServletRequest request){
        long temp = accountService.checkExists(user);
        if(temp != -1){
            final String sessionId = request.getSession().getId();
            accountService.addSession(sessionId, user);
            return Response.status(Response.Status.OK).entity(user.getIdByJson(temp)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
