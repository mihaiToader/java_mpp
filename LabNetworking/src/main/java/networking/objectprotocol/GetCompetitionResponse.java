package networking.objectprotocol;

import model.Competition;
import networking.dto.CompetitionsListDTO;

import java.util.ArrayList;

/**
 * Created by Mihai on 03.04.2017.
 */
public class GetCompetitionResponse implements Response {
    public CompetitionsListDTO competitions;

    public GetCompetitionResponse(ArrayList<Competition> competitions) {
        this.competitions = new CompetitionsListDTO(competitions);
    }

    public ArrayList<Competition> getCompetitions() {
        return competitions.getCompetitions();
    }
}
