package networking.dto;

import java.io.Serializable;

/**
 * Created by Mihai on 03.04.2017.
 */
public class MatchDTO implements Serializable{
    private Integer id;
    private String name;
    private Integer idCompetition;
    private Integer avaibleSeats;

    public MatchDTO(Integer id, String name, Integer idCompetition, Integer avaibleSeats) {
        this.id = id;
        this.name = name;
        this.idCompetition = idCompetition;
        this.avaibleSeats = avaibleSeats;
    }

    public MatchDTO(Integer id, String name, Integer idCompetition) {
        this.id = id;
        this.name = name;
        this.idCompetition = idCompetition;
    }

    public Integer getAvaibleSeats() {
        return avaibleSeats;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getIdCompetition() {
        return idCompetition;
    }
}
