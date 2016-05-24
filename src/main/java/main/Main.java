package main;


import mechanic.GameMechanic;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import rest.Users;
import rest.Session;
import websocket.GameWebSocketServlet;

/**
 * @author iu6team
 */
public class Main {
    @SuppressWarnings("OverlyBroadThrowsClause")
    public static void main(String[] args) throws Exception {
        int port = -1;
        final GameMechanic gameMechanic = new GameMechanic();
        if (args.length == 1) {
            port = Integer.valueOf(args[0]);
        } else {
            System.err.println("Specify port");
            System.exit(1);
        }
        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');
        final Server server = new Server(port);
        final ServletContextHandler contextHandler = new ServletContextHandler(server, "/api/", ServletContextHandler.SESSIONS);
        final Context context = new Context();

        context.put(AccountService.class, new AccountServiceImpl());
        final ResourceConfig config = new ResourceConfig(Users.class, Session.class);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(context);
            }
        });
        final AccountService accountService = context.get(AccountService.class);
        final ServletHolder servletHolder = new ServletHolder(new ServletContainer(config));
        contextHandler.addServlet(servletHolder, "/*");
        contextHandler.addServlet(new ServletHolder(new GameWebSocketServlet(accountService, gameMechanic)), "/game");
        server.start();
        server.join();
    }
}