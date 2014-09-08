package classesthatrunthings;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

/**
 * This little class just makes it easy to run the web classesthatrunthings in the embedded Jetty contain so you don't have to worry
 * about having an classesthatrunthings server to get things going.
 *
 * Fire it up and go to http://localhost:9091
 */
public class RunApp {

    public static void main(String[] args) throws Exception {
        System.out.println("Working directory: " + new File("./").getAbsolutePath().toString());
        Server server = new Server(9091);

        WebAppContext context = new WebAppContext();
        context.setDescriptor("/WEB-INF/web.xml");
        context.setResourceBase("src/main/webapp");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        context.addServlet(DefaultServlet.class, "/*");
        server.setHandler(context);

        server.start();
        server.join();

    }

}
