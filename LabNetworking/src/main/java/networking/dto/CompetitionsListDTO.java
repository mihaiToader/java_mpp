package networking.dto;

import model.Competition;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mihai on 03.04.2017.
 */
public class CompetitionsListDTO implements Serializable{
    private ArrayList<CompetitionDTO> competitions;

    public CompetitionsListDTO(ArrayList<Competition> competitions) {
        this.competitions = new ArrayList<>();
        fromCompetitionToDTOCompetition(competitions);
    }

    private void fromCompetitionToDTOCompetition(ArrayList<Competition> competitions){
        for (Competition c:competitions){
            this.competitions.add(new CompetitionDTO(c.getId(),c.getSeats(),c.getName(),c.getTicketCost()));
        }
    }


    public ArrayList<Competition> getCompetitions() {
        ArrayList<Competition> res = new ArrayList<>();
        for (CompetitionDTO c:competitions){
            res.add(new Competition(c.getId(),c.getSeats(),c.getName(),c.getTicketCost()));
        }
        return res;
    }
}
