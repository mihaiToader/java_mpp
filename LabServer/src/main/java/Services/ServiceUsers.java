package Services;

import model.User;
import persistance.jdbc.RepositoryUser;

/**
 * Created by Mihai on 21.03.2017.
 */
public class ServiceUsers {
    private RepositoryUser repositoryUser;

    public ServiceUsers(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    public User validateUser(String username, String password){
        return repositoryUser.validateUser(username, password);
    }

}
