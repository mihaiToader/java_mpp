package networking.objectprotocol;

import Services.IServerServices;
import Services.IServicesClient;
import Services.LabException;
import model.User;
import networking.dto.DTOUtils;
import networking.dto.UserDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 4:04:43 PM
 */
public class ChatClientObjectWorker implements Runnable, IServicesClient {
    private IServerServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ChatClientObjectWorker(IServerServices serverApp, Socket connection) {
        this.server = serverApp;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.setClient(this);
    }

    public void run() {
        while(connected){
            try {
                System.out.println("Try to read request");
                Object request=input.readObject();
                System.out.println("Get request:" + request);
                Object response=handleRequest((Request)request);
                if (response!=null){
                   sendResponse((Response) response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private Response handleRequest(Request request){
        Response response=null;
        if (request instanceof LoginRequest){
            UserDTO u = ((LoginRequest) request).getUser();
            try {
                User user = server.validateUser(u.getUsername(),u.getPasswd());
                response = new LoginResponse(DTOUtils.getDTO(user));
            } catch (LabException e) {
                //connected = false;
                response = new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof LogoutRequest){
            connected=false;
            response = new OkResponse();
        }
        if (request instanceof  GetCompetitionsRequest){
            response = new GetCompetitionResponse(server.getAllCompetition());
        }
        if (request instanceof  GetAllMatchesFromCompetitionRequest){
            response = new GetAllMatchesFromCompetitionResponse(server.getAllMatchesFromCompetition(((GetAllMatchesFromCompetitionRequest) request).getIdCompetition()));
        }
        if (request instanceof  GetTicketNumberRequest){
            response = new GetTicketNumberResponse(server.getTicketNumbersForMatch(((GetTicketNumberRequest) request).getIdMatch()));
        }
        if (request instanceof GetMatchesFilterRequest){
            String name = ((GetMatchesFilterRequest) request).getName();
            Integer id = ((GetMatchesFilterRequest) request).getIdCompetition();
            response = new GetMatchesFilterResponse(server.searchMatch(name,id));
        }
        if (request instanceof SellTicketRequest){
            Integer number = ((SellTicketRequest) request).getNumber();
            Integer id = ((SellTicketRequest) request).getId();
            String name = ((SellTicketRequest) request).getName();
            server.addTicket(number,id,name);
            //response = new OkResponse();
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("Sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void initializeMatchObservableList() {
        try {
            sendResponse(new ReloadMatchesUpdate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
