package persistance;

import model.User;

/**
 * Created by Mihai on 02.04.2017.
 */
public interface IUserRepository extends IRepository<User>{
    User validateUser(String username, String password);
}
