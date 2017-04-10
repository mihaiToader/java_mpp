package networking.dto;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 4:20:27 PM
 */
public class UserDTO implements Serializable{
    private Integer id;
    private String passwd;
    private String name;
    private String username;

    public UserDTO(Integer id) {
        this(id,"","");
    }

    public UserDTO(Integer id, String passwd, String username) {
        this.id = id;
        this.passwd = passwd;
        this.username = username;
        name = "";
    }

    public UserDTO(Integer id, String passwd, String name, String username) {
        this.id = id;
        this.passwd = passwd;
        this.name = name;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    @Override
    public String toString(){
        return "UserDTO["+id+' '+passwd+"]";
    }
}
