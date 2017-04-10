package networking.objectprotocol;

/**
 * Created by Mihai on 03.04.2017.
 */
public class GetTicketNumberResponse implements Response{
    private Integer number;

    public GetTicketNumberResponse(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }
}
