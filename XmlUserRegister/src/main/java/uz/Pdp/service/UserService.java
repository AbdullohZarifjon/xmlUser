package uz.Pdp.service;

import uz.Pdp.model.User;
import uz.Pdp.model.wrapper.Cards;
import uz.Pdp.model.wrapper.Users;
import uz.Pdp.service.serviseUtil.XmlJsonUtil;
import uz.Pdp.service.serviseUtil.XmlUtil;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class UserService {
    public String path() {
        File file = new File("src/Users.xml");
        File file1 = new File("src/Users.json");
        if (file.canRead()) {
            return "src/Users.xml";
        } else if (file1.canRead()) {
            return "src/Users.json";
        }throw new RuntimeException("The file does not exist!");
    }

    public User register(User user) {
        Users usersWrapper = readUsers();

        if (!hasUser(user, usersWrapper)) {
            usersWrapper.getUsers().add(user);
            write(usersWrapper, path());
            return user;
        }
        throw new RuntimeException("User already exists");
    }

    public boolean hasUser(User newUser, Users usersWrapper) {
        for (User user: usersWrapper.getUsers()) {
            if (user.getUserName().equals(newUser.getUserName())) {
                return true;
            }
        }
        return false;
    }

    public User login(String userName, String password) {
        return getUser(userName, password, readUsers());
    }

    private User getUser(String userName, String password, Users userWrapper) {
        for (User user: userWrapper.getUsers()) {
            if (user.getUserName().equals(userName) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new RuntimeException("User not found");
    }

    public User getUserCard(UUID userId) {
        List<User> users = readUsers().getUsers();
        for (User user: users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        throw new RuntimeException("Bunday User mavjud emas!");
    }

    public Users readUsers() {
        return XmlJsonUtil.read(Users.class, path());
    }

    public void write(Users users, String path) {
        XmlJsonUtil.write(users, path);
    }
}
