package networking.objectprotocol;

/**
 * Created by Mihai on 04.04.2017.
 */
public class GetMatchesFilterRequest implements Request {
    private String name;
    private Integer idCompetition;

    public GetMatchesFilterRequest(String name, Integer idCompetition) {
        this.name = name;
        this.idCompetition = idCompetition;
    }

    public String getName() {
        return name;
    }

    public Integer getIdCompetition() {
        return idCompetition;
    }
}
