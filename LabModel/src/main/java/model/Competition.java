package model;

/**
 * Created by Mihai on 20.03.2017.
 */
public class Competition implements HasId {
    private Integer id;
    private Integer seats;
    private String name;
    private Double ticketCost;

    public Competition(Integer id, Integer seats, String name, Double ticketCost) {
        this.id = id;
        this.seats = seats;
        this.name = name;
        this.ticketCost = ticketCost;
    }


    public Competition(Integer seats, String name, Double ticketCost) {
        this.id = null;
        this.seats = seats;
        this.ticketCost = ticketCost;
        this.name = name;
    }


    public Double getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(Double ticketCost) {
        this.ticketCost = ticketCost;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
