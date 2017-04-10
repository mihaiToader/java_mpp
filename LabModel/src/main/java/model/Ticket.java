package model;

/**
 * Created by Mihai on 14.03.2017.
 */
public class Ticket implements HasId{
    private Integer id;
    private Integer idMatch;
    private String customerName;

    public Ticket(Integer id, Integer idMatch, String customerName) {
        this.id = id;
        this.idMatch = idMatch;
        this.customerName = customerName;
    }

    public Ticket(Integer idMatch, String customerName) {
        this.id = null;
        this.idMatch = idMatch;
        this.customerName = customerName;
    }

    public Ticket() {
        this(-1,-1,"");
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Integer idMatch) {
        this.idMatch = idMatch;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
