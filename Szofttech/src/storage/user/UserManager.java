package storage.user;

import back.FileManager;
import common.User;
import common.enums.UserType;
import storage.Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManager implements Manager<User> {

    private static final String fileName = "users.dat";
    private FileManager fileManager;
    private List<User> users;

    public UserManager(FileManager fileManager) {
        this.fileManager = fileManager;
        readData();
    }

    @Override
    public void readData() {
        try {
            List<String> datas = fileManager.read(fileName);
            users = new ArrayList<>();

            for ( String data: datas ) {
                String[] dataColumn = data.split(", ");

                User user = new User(
                        dataColumn[0],
                        dataColumn[1],
                        dataColumn[2],
                        dataColumn[3],
                        UserType.valueOf(dataColumn[4])
                );
                users.add(user);
            }
        } catch (IOException e) {
            // TODO Logging
        }
    }

    @Override
    public void delete(User userToDelete) {
        users.removeIf(user -> user.getId().equals(userToDelete.getId()));
        try {
            fileManager.remove(fileName, userToDelete.toString());
        } catch (IOException e) {
            // TODO Logging
        }
    }

    @Override
    public User get(String id) {
        return users
                .stream()
                .filter(user -> user.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public void add(User content) {
        users.add(content);
        System.out.println(content.toString());
        try {
            fileManager.add(fileName, content.toString());
        } catch (IOException e) {
            // TODO Logging
        }
    }

    @Override
    public List<User> list() {
        return users;
    }

    public User getByUsernameAndPassword(String username, String password) {
        return users
                .stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .collect(Collectors.toList())
                .get(0);
    }
}
