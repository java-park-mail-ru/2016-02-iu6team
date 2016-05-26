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
    private static final String CONFIG = "cfg/server.properties";

    public static void main(String[] args) throws Exception {
        final Configuration configuration; //try catch
        configuration = new Configuration(CONFIG);
        final GameMechanic gameMechanic = new GameMechanic();
        int port = configuration.getPort();
        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');
        final Server server = new Server(port);
        final ServletContextHandler contextHandler = new ServletContextHandler(server, "/api/", ServletContextHandler.SESSIONS);
        final Context context = new Context();
        context.put(AccountService.class, new AccountServiceImpl(configuration.getDbName(),configuration.getDbHost(),
                configuration.getDbPort(),configuration.getDbUsername(),configuration.getDbPassword()));
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