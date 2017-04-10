package networking.objectprotocol;

import model.Match;
import networking.dto.MatchesListDTO;

import java.util.ArrayList;

/**
 * Created by Mihai on 03.04.2017.
 */
public class GetAllMatchesFromCompetitionResponse implements Response{
    private MatchesListDTO matches;

    public GetAllMatchesFromCompetitionResponse(ArrayList<Match> matches) {
        this.matches = new MatchesListDTO(matches);
    }

    public ArrayList<Match> getMatches() {
        return matches.getMatches();
    }
}
