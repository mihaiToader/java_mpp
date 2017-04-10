package networking.objectprotocol;

import networking.dto.UserDTO;

/**
 * Created by Mihai on 03.04.2017.
 */
public class LoginResponse implements Response {
    private UserDTO user;

    public LoginResponse(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
