package networking.dto;

import java.io.Serializable;

/**
 * Created by Mihai on 03.04.2017.
 */
public class CompetitionDTO implements Serializable{

    private Integer id;
    private Integer seats;
    private String name;
    private Double ticketCost;

    public CompetitionDTO(Integer id, Integer seats, String name, Double ticketCost) {
        this.id = id;
        this.seats = seats;
        this.name = name;
        this.ticketCost = ticketCost;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSeats() {
        return seats;
    }

    public String getName() {
        return name;
    }

    public Double getTicketCost() {
        return ticketCost;
    }
}
