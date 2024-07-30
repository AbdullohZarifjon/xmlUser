package uz.Pdp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import uz.Pdp.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    List<User> users = new ArrayList<>();

    private static final String PATH = "src/Users.xml";

    private XmlMapper xmlMapper = new XmlMapper();


    public User register(User user) {
        if (!hasUser(user)) {
            users.add(user);
            write(users);
            return user;
        }return null;
    }

    public User login(String lastname, String password) {
        read();
        for (User user: users) {
            if (user.getLastname().equals(lastname) && user.getPassword().equals(password)) {
                return user;
            }
        }return null;
    }

    public boolean hasUser(User user) {
        read();
        for (User user1: users) {
            if (user1.equals(user)) {
                return true;
            }
        }return false;
    }

    public void write(List<User> users) {
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH))) {
            String xmlContent = xmlMapper.writeValueAsString(users);
            bufferedWriter.write(xmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        File file = new File(PATH);
        if (file.exists() && file.length() > 0) {
            try {
                users = xmlMapper.readValue(file, new TypeReference<>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
