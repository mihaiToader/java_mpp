package networking.objectprotocol;

import Services.IServicesUsers;
import Services.LabException;
import Services.IServicesClient;
import Services.IServerServices;
import model.Competition;
import model.Match;
import model.Ticket;
import model.User;
import networking.dto.DTOUtils;
import networking.dto.UserDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 4:36:34 PM
 */
public class ChatServerObjectProxy implements IServerServices {
    private String host;
    private int port;

    private IServicesClient client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    //private List<Response> responses;
    private BlockingQueue<Response> qresponses;
    private BlockingQueue<Response> qUpdadeResponses;
    private volatile boolean finished;

    public ChatServerObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
        qUpdadeResponses = new LinkedBlockingQueue<>();
    }

    public void setClient(IServicesClient client) {
        this.client = client;
    }

    @Override
    public User validateUser(String username, String password) throws LabException {
        initializeConnection();
        UserDTO udto= DTOUtils.getDTO(new User(-1,username,password));
        sendRequest(new LoginRequest(udto));
        Response response=readResponse();
        if (response instanceof LoginResponse){
            //this.client=client;
            return DTOUtils.getFromDTO(((LoginResponse) response).getUser());
        }
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            //closeConnection();
            throw new LabException(err.getMessage());
        }
        return null;
    }

    @Override
    public void logOut() throws LabException {
        sendRequest(new LogoutRequest(null));
        Response response = readResponse();
        closeConnection();
    }


    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws LabException {
        try {
            System.out.println("Send request: " + request);
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new LabException("Error sending object "+e);
        }

    }

    private Response readResponse() throws LabException {
        Response response=null;
        try{
            /*synchronized (responses){
                responses.wait();
            }
            response = responses.remove(0);    */
            while (qresponses.isEmpty()){
                Thread.sleep(1000);
            }
            System.out.println("Waiting for response:");
            response=qresponses.take();
            System.out.println("Get response: " + response);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws LabException {
         try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
            startUpdateHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void startUpdateHandler(){
        Thread tw=new Thread(new HandleUpdateThread());
        tw.start();
    }

    private void handleUpdate(UpdateResponse update){
        if (update instanceof ReloadMatchesUpdate){
            System.out.println("Handle Update Reload Matches");
            client.initializeMatchObservableList();
        }
    }

    @Override
    public ArrayList<Competition> getAllCompetition() {
        try {
            sendRequest(new GetCompetitionsRequest());
            Response response = readResponse();
            if (response instanceof ErrorResponse){
                System.out.println(((ErrorResponse) response).getMessage());
                return  null;
            }
            else if (response instanceof GetCompetitionResponse){
                return ((GetCompetitionResponse) response).getCompetitions();
            }
        } catch (LabException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Match> getAllMatchesFromCompetition(Integer idCompetition) {
        try {
            sendRequest(new GetAllMatchesFromCompetitionRequest(idCompetition));
            Response response = readResponse();
            if (response instanceof ErrorResponse){
                System.out.println(((ErrorResponse) response).getMessage());
                return  null;
            }
            else if (response instanceof GetAllMatchesFromCompetitionResponse){
                return ((GetAllMatchesFromCompetitionResponse) response).getMatches();
            }
        } catch (LabException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getTicketNumbersForMatch(Integer idMatch) {
        //System.out.println("Request for ticket numbers for match " + idMatch);
        try {
            sendRequest(new GetTicketNumberRequest(idMatch));
            /*Response response = readResponse();
            if (response instanceof ErrorResponse){
                System.out.println(((ErrorResponse) response).getMessage());
                return  null;
            }
            else if (response instanceof GetTicketNumberResponse){
                //System.out.println("Response for ticket number!");
                return ((GetTicketNumberResponse) response).getNumber();
            }*/
        } catch (LabException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addTicket(Integer numbers, Integer id, String customerName){
        //System.out.println("Request for ticket numbers for match " + idMatch);
        try {
            sendRequest(new SellTicketRequest(numbers, id, customerName));
           /* Response response = readResponse();
            if (response instanceof ErrorResponse){
                System.out.println(((ErrorResponse) response).getMessage());
            }
            else if (response instanceof OkResponse){
                System.out.println("Response for add ticket!");
            }*/
        } catch (LabException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer getAvaibleSeats(Integer idCompetition, Match m) {
        return null;
    }

    @Override
    public ArrayList<Match> searchMatch(String name, Integer idCompetition) {
        //System.out.println("Request for ticket numbers for match " + idMatch);
        try {
            sendRequest(new GetMatchesFilterRequest(name, idCompetition));
            Response response = readResponse();
            if (response instanceof ErrorResponse){
                System.out.println(((ErrorResponse) response).getMessage());
                return  null;
            }
            else if (response instanceof GetMatchesFilterResponse){
                //System.out.println("Response for ticket number!");
                return ((GetMatchesFilterResponse) response).getMatches();
            }
        } catch (LabException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    System.out.println("Try to read response!");
                    Object response=input.readObject();
                    System.out.println("Get the response:" +response);
                    if (response instanceof UpdateResponse){
                        try {
                            System.out.println("Add handle response to queue");
                            qUpdadeResponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            System.out.println("Add response to queue");
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SocketException e){
                    System.out.println("Good bye");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    closeConnection();
                }
            }
        }
    }

    private class HandleUpdateThread implements Runnable{
        public void run() {
            while (!finished){
                Response response=null;
                try{
                    while (qUpdadeResponses.isEmpty() && !finished){
                        Thread.sleep(1000);
                    }
                    if (!finished){
                        System.out.println("Waiting for update response:");
                        response=qUpdadeResponses.take();
                        System.out.println("Get update response: " + response);
                        handleUpdate((UpdateResponse)response);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
