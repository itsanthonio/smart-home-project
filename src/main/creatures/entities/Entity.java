package creatures.entities;

import appliances.UsableObject;
import house.Room;
import java.io.IOException;

/**
 * Interface representing an entity in the house.
 */
public interface Entity {
    // Constants for hunger management
    int DEFAULT_HUNGRY_LEVEL = 0;
    int MAX_HUNGRY_LEVEL = 10;

    /**
     * Makes the entity say something.
     */
    void say();

    /**
     * Determines if the entity will break a certain object.
     *
     * @param usableObject the object that might be broken
     * @return true if the entity breaks the object, false otherwise
     */
    boolean chanceBrakeStuff(UsableObject usableObject);

    /**
     * Makes the entity wait.
     */
    void waiting();

    /**
     * Finds an activity for the entity.
     */
    void findActivity() throws IOException;

    /**
     * Makes the entity use a specific object.
     *
     * @param usableObject the object to be used
     */
    void useStuff(UsableObject usableObject);

    /**
     * Increases the entity's hunger level.
     */
    void increaseHungerLevel();

    /**
     * Resets the entity's hunger level.
     */
    void resetHungerLevel();

    /**
     * Gets the object the entity is currently using.
     *
     * @return the object the entity is currently using
     */
    UsableObject getCurrentObject();

    /**
     * Moves the entity to a specific room.
     *
     * @param room the room to move to
     */
    void moveTo(Room room);

    /**
     * Gets the entity's current action progress.
     *
     * @return the entity's current action progress
     */
    int getCurrentActionProgress();

    /**
     * Increases the entity's action progress.
     */
    void increaseProgress();

    /**
     * Stops the entity's current action.
     */
    void stopCurrentAction();

    /**
     * Gets the entity's name.
     *
     * @return the entity's name
     */
    String getName();

    /**
     * Gets the entity's age.
     *
     * @return the entity's age
     */
    int getAge();

    /**
     * Gets the entity's current room.
     *
     * @return the entity's current room
     */
    Room getCurrentRoom();
}