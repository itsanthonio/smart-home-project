package appliances.devices;

import appliances.UsableObject;
import appliances.StuffType;
import appliances.state.RestingState;
import house.Room;

/**
 * Represents a door in the smart home.
 */
public class Door extends UsableObject {
    private boolean locked = false;
    
    public Door(String name, Room room) {
        super(name, room, StuffType.DOOR);
        this.state = new RestingState(this);
    }
    
    public void lock() {
        if (!locked) {
            locked = true;
            addEventToReport(name + " locked");
            notifyObserver();
        }
    }
    
    public void unlock() {
        if (locked) {
            locked = false;
            addEventToReport(name + " unlocked");
            notifyObserver();
        }
    }
    
    @Override
    public void turnOn() {
        // For a door, "on" means open
        super.turnOn();
        addEventToReport(name + " opened");
    }
    
    @Override
    public void turnOff() {
        // For a door, "off" means closed
        super.turnOff();
        addEventToReport(name + " closed");
    }
    
    public boolean isLocked() {
        return locked;
    }
}