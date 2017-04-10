package networking.objectprotocol;

/**
 * Created by Mihai on 03.04.2017.
 */
public class GetTicketNumberRequest implements Request {
    private Integer idMatch;

    public GetTicketNumberRequest(Integer idMatch) {
        this.idMatch = idMatch;
    }

    public Integer getIdMatch() {
        return idMatch;
    }
}
