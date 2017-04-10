package networking.dto;

/**
 * Created by Mihai on 03.04.2017.
 */
public class UnusualDTO {
    private Integer intAtribute;
    private String strAtribute;
    private Object objAtribute;

    public UnusualDTO(Integer intAtribute, String strAtribute, Object objAtribute) {
        this.intAtribute = intAtribute;
        this.strAtribute = strAtribute;
        this.objAtribute = objAtribute;
    }

    public Integer getIntAtribute() {
        return intAtribute;
    }

    public String getStrAtribute() {
        return strAtribute;
    }

    public Object getObjAtribute() {
        return objAtribute;
    }
}
