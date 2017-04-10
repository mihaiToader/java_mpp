import Services.IServerServices;
import Services.ServicesApplication;
import networking.utils.AbstractServer;
import networking.utils.ChatObjectConcurrentServer;
import persistance.jdbc.*;

import java.io.IOException;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Mihai on 03.04.2017.
 */
public class StartServer {
    private static int defaultPort=55555;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/bd.config"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }

        DataBaseManager dbm = new DataBaseManager(serverProps);
        RepositoryUser userRepo=new RepositoryUser(dbm);
        RepositoryCompetition repositoryCompetition = new RepositoryCompetition(dbm);
        RepositoryMatch repositoryMatch = new RepositoryMatch(dbm);
        RepositoryTicket repositoryTicket = new RepositoryTicket(dbm);
        IServerServices chatServerImpl=new ServicesApplication(repositoryMatch, repositoryCompetition, repositoryTicket, userRepo);
        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new ChatObjectConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (networking.utils.ServerException e) {
            e.printStackTrace();
        }
    }
}
