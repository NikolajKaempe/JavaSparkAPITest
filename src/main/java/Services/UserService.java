package Services;

import Models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    Dao<User,String> userDao;
    private Map<String, User> users = new HashMap<>();
    private int amountOfUsers = 0;
    public UserService(ConnectionSource connectionSource)
    {
        ///userDao = DaoManager.createDao(connectionSource, User.class);
    }

    public List<User> getAllUsers() { return new ArrayList<>(users.values()); }

    public User getUser(String id) {
        return users.get(id);
    }

    public User createUser(String name, String email) {
        failIfInvalid(name, email);
        User user = new User(name, email,this.amountOfUsers++);
        users.put(""+user.getId(), user);
        return user;
    }

    public User updateUser(String id, String name, String email) {
        User user = users.get(id);
        if (user == null) {
            throw new IllegalArgumentException("No user with id '" + id + "' found");
        }
        failIfInvalid(name, email);
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    private void failIfInvalid(String name, String email) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'name' cannot be empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'email' cannot be empty");
        }
    }
}
