package rest;
import main.AccountService;

import javax.inject.Singleton;
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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserProfile user, @Context HttpHeaders headers){
        long temp = accountService.checkExists(user);
        if(temp != -1){
            return Response.status(Response.Status.OK).entity(user.getIdByJson(temp)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
