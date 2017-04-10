package Services;

import model.Competition;
import model.Match;
import model.Ticket;
import model.User;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Mihai on 02.04.2017.
 */
public interface IServerServices {
    ArrayList<Competition> getAllCompetition();

    User validateUser(String username, String password) throws LabException;

    void logOut() throws LabException;

    void setClient(IServicesClient client);

    ArrayList<Match> getAllMatchesFromCompetition(Integer idCompetition);

    Integer getTicketNumbersForMatch(Integer idMatch);

    public void addTicket(Integer numbers, Integer id, String customerName);

    Integer getAvaibleSeats(Integer idCompetition, Match m);

    ArrayList<Match> searchMatch(String name, Integer idCompetition);
}
