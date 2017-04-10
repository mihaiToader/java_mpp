package networking.objectprotocol;

/**
 * Created by Mihai on 03.04.2017.
 */
public class GetAllMatchesFromCompetitionRequest implements Request{
    Integer idCompetition;

    public GetAllMatchesFromCompetitionRequest(Integer id) {
        this.idCompetition = id;
    }

    public Integer getIdCompetition() {
        return idCompetition;
    }
}
