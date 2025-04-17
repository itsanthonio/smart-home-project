package creatures.entities;

import house.Room;
import house.strategy.Strategy;
import appliances.UsableObject;
import appliances.devices.Fridge;
import appliances.devices.StuffType;
import appliances.state.BrokenState;
import appliances.state.RestingState;

import java.io.IOException;
import java.util.Random;

/**
 * Class representing a user in the smart home system.
 */
public class User implements Entity {

    private final String name;
    private final int age;
    private Room room;
    private Strategy strategy;

    private int hungerLevel = DEFAULT_HUNGRY_LEVEL;

    private int currentActionProgress = 1;
    protected UsableObject usingObject = null;

    /**
     * Instantiates a new User.
     *
     * @param name user's name
     * @param age  user's age
     * @param room user's location
     */
    public User(String name, int age, Room room) {
        this.name = name;
        this.age = age;
        this.room = room;
    }

    @Override
    public void say() {
        System.out.println("User " + name + " says: I'm in the " + room.getName());
    }

    @Override
    public boolean chanceBrakeStuff(UsableObject usableObject) {
        // 10% chance to break a device
        return new Random().nextInt(10) == 0;
    }

    /**
     * A method that helps a user choose what to do.
     *
     * @return boolean
     */
    public boolean flipCoin() {
        return (new Random()).nextBoolean();
    }

    @Override
    public void waiting() {
        System.out.println("User: " + name + " is waiting for now");
    }

    @Override
    public void findActivity() throws IOException {
        strategy.findActivity(this);
    }

    @Override
    public void useStuff(UsableObject usableObject) {
        if (usableObject != null) {
            System.out.println(this.getName() + " says: I am using " + usableObject.getType());
            moveTo(usableObject.getCurrentRoom());

            if (usableObject.getType() == StuffType.FRIDGE) {
                if (((Fridge) usableObject).isEmpty()) {
                    usableObject.setState(new BrokenState(usableObject));
                    usableObject.addEventToReport("Food in Fridge is over");
                    usableObject.notifyObserver();
                    return;
                }
                resetHungerLevel();
            }
            if (!chanceBrakeStuff(usableObject)) {
                usingObject = usableObject;
                usableObject.usingStuff();
            }
            return;
        }
        waiting();
    }

    public void setUsingObject(UsableObject usingObject) {
        this.usingObject = usingObject;
    }

    @Override
    public void increaseHungerLevel() {
        if (hungerLevel < MAX_HUNGRY_LEVEL) hungerLevel++;
    }

    @Override
    public void resetHungerLevel() {
        hungerLevel = DEFAULT_HUNGRY_LEVEL;
    }

    @Override
    public UsableObject getCurrentObject() {
        return usingObject;
    }

    @Override
    public void moveTo(Room room) {
        this.room = room;
    }

    @Override
    public int getCurrentActionProgress() {
        return currentActionProgress;
    }

    @Override
    public void increaseProgress() {
        currentActionProgress++;
    }

    @Override
    public void stopCurrentAction() {
        currentActionProgress = 1;
        if (getCurrentObject() != null) {
            getCurrentObject().setState(new RestingState(usingObject));
            usingObject = null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public Room getCurrentRoom() {
        return room;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}