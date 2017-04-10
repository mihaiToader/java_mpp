package model;

/**
 * Created by Mihai on 14.03.2017.
 */
public class Match implements HasId{
    private Integer id;
    private String name;
    private Integer idCompetition;
    private Integer avaibleSeats = 0;

    public Match(Integer id, String name, Integer idCompetition) {
        this.id = id;
        this.name = name;
        this.idCompetition = idCompetition;
    }

    public Match(String name, Integer idCompetition) {
        this.id = null;
        this.name = name;
        this.idCompetition = idCompetition;
    }

    public Match() {
        this(-1,"",-1);
    }

    public Match(Integer id, String name, Integer idCompetition, Integer avaibleSeats) {
        this.id = id;
        this.name = name;
        this.idCompetition = idCompetition;
        this.avaibleSeats = avaibleSeats;
    }

    public Integer getAvaibleSeats() {
        return avaibleSeats;
    }

    public void setAvaibleSeats(Integer avaibleSeats) {
        this.avaibleSeats = avaibleSeats;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(Integer idCompetition) {
        this.idCompetition = idCompetition;
    }
}
