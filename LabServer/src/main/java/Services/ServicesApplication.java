package Services;

import model.Competition;
import model.Match;
import model.Ticket;
import model.User;
import persistance.jdbc.RepositoryCompetition;
import persistance.jdbc.RepositoryMatch;
import persistance.jdbc.RepositoryTicket;
import persistance.jdbc.RepositoryUser;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mihai on 21.03.2017.
 */
public class ServicesApplication implements IServerServices {
    private RepositoryMatch repositoryMatch;
    private RepositoryCompetition repositoryCompetition;
    private RepositoryTicket repositoryTicket;
    private ArrayList<IServicesClient> clients;

    public ServicesApplication(RepositoryMatch repositoryMatch, RepositoryCompetition repositoryCompetition, RepositoryTicket repositoryTicket, RepositoryUser repositoryUser) {
        this.repositoryMatch = repositoryMatch;
        this.repositoryCompetition = repositoryCompetition;
        this.repositoryTicket = repositoryTicket;
        this.repositoryUser = repositoryUser;
        clients = new ArrayList<>();
    }

    private RepositoryUser repositoryUser;

    public ArrayList<Competition> getAllCompetition(){
        return repositoryCompetition.getAll();
    }

    @Override
    public User validateUser(String username, String password) throws LabException{
        User user = repositoryUser.validateUser(username, password);
        if (user == null){
            throw new LabException("User invalid!");
        }
        return user;
    }

    @Override
    public void logOut() throws LabException{

    }

    @Override
    public synchronized void setClient(IServicesClient client) {
        clients.add(client);
    }

    public synchronized ArrayList<Match> getAllMatchesFromCompetition(Integer idCompetition){
        ArrayList<Match> res = repositoryMatch.getAllMatchFromCompetition(idCompetition);
        for (Match m:res){
            m.setAvaibleSeats(getAvaibleSeats(idCompetition, m));
        }
        return res;
    }

    public synchronized Integer getTicketNumbersForMatch(Integer idMatch){
        return repositoryTicket.getTicketsNumberForTheMatch(idMatch);
    }

    public synchronized void addTicket(Integer numbers, Integer id, String customerName){
        for (Integer i=1; i<=numbers; i++){
            repositoryTicket.add(new Ticket(id,customerName));
        }
        notifyFriendsLoggedIn();
    }

    public synchronized Integer getAvaibleSeats(Integer idCompetition, Match m){
        return repositoryCompetition.getCompetition(idCompetition).getSeats() - getTicketNumbersForMatch(m.getId());
    }

    public synchronized ArrayList<Match> searchMatch(String name, Integer idCompetition){
        ArrayList<Match> res = new ArrayList<>();
        getAllMatchesFromCompetition(idCompetition).stream()
                .filter(x->(name.equals("") || x.getName().contains(name)) && x.getAvaibleSeats() != 0)
                .forEach(x->res.add(x));
        res.sort(Comparator.comparing(x -> getAvaibleSeats(idCompetition, x)));
        return res;
    }

    private final int defaultThreadsNo=5;
    private void notifyFriendsLoggedIn(){
        //System.out.println(clients.size());
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(IServicesClient sc : clients){
            executor.execute(() -> {
                System.out.println("Notifying.");
                try {
                    sc.initializeMatchObservableList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
/*
        for(IServicesClient sc : clients){
            sc.initializeMatchObservableList();
        }*/
    }
}
