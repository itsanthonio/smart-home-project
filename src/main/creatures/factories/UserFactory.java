package creatures.factories;

import creatures.entities.User;
import house.Room;
import house.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating users in the smart home.
 * Implements the Singleton pattern.
 */
public class UserFactory {

    private static UserFactory instance = null;

    private UserFactory() {
    }

    /**
     * List of all users
     */
    private final List<User> users = new ArrayList<>();

    /**
     * Returns the instance of the User Factory.
     *
     * @return instance
     */
    public static UserFactory getInstance() {
        if (instance == null) instance = new UserFactory();
        return instance;
    }

    /**
     * Factory method that creates a user and adds it to the list.
     *
     * @param name user's name
     * @param age  user's age
     * @param room user's room
     * @return user
     */
    public User create(String name, int age, Room room, Strategy defaultStrategy) {
        User user = new User(name, age, room);
        user.setStrategy(defaultStrategy);
        users.add(user);
        return user;
    }

    /**
     * Returns list of all users.
     *
     * @return List of users
     */
    public List<User> getUsers() {
        return users;
    }
}