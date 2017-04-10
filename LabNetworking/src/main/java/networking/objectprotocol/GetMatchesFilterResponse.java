package networking.objectprotocol;

import model.Match;
import networking.dto.MatchesListDTO;

import java.util.ArrayList;

/**
 * Created by Mihai on 04.04.2017.
 */
public class GetMatchesFilterResponse implements Response{
    private MatchesListDTO matches;

    public GetMatchesFilterResponse(ArrayList<Match> matches) {
        this.matches = new MatchesListDTO(matches);
    }

    public ArrayList<Match> getMatches(){
        return matches.getMatches();
    }
}
