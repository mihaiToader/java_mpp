package networking.dto;

import model.Match;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mihai on 03.04.2017.
 */
public class MatchesListDTO implements Serializable{
    private ArrayList<MatchDTO> matches;

    public MatchesListDTO(ArrayList<Match> matches) {
        this.matches = new ArrayList<>();
        for (Match m:matches){
            this.matches.add(new MatchDTO(m.getId(),m.getName(),m.getIdCompetition(), m.getAvaibleSeats()));
        }
    }

    public ArrayList<Match> getMatches() {
        ArrayList<Match> res =new ArrayList<>();
        for (MatchDTO m:matches){
            res.add(new Match(m.getId(),m.getName(),m.getIdCompetition(), m.getAvaibleSeats()));
        }
        return res;
    }
}
