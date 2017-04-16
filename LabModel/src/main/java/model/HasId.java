package model;

import java.io.Serializable;

/**
 * Created by Mihai on 14.03.2017.
 */
public interface HasId extends Serializable {
    Integer getId();
    void setId(Integer id);
}
