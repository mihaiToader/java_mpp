package networking.objectprotocol;

/**
 * Created by Mihai on 04.04.2017.
 */
public class SellTicketRequest implements Request{
    private Integer number;
    private Integer id;
    private String name;

    public SellTicketRequest(Integer number, Integer id, String name) {
        this.number = number;
        this.id = id;
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
