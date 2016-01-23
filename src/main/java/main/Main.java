package main;

import accounts.AccountService;
import accounts.UserProfile;
import dbService.DbService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by aleksandr on 15.01.16.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        DbService dbService = new DbService();
        dbService.printConnectionInfo();
        AccountService accountService = new AccountService(dbService);

        //accountService.addNewUser(new UserProfile("admin"));
        //accountService.addNewUser(new UserProfile("test"));
        MirrorServlet mirrorServlet = new MirrorServlet();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(mirrorServlet), "/mirror");
        context.addServlet(new ServletHolder(new UsersServlet(accountService)), "/api/v1/users");
        context.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}

