package appliances;

import house.Room;
import appliances.state.DeviceState;
import appliances.observer.DeviceObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing usable objects in the smart home.
 * Implements the Observer pattern.
 */
public abstract class UsableObject {
    protected String name;
    protected DeviceState state;
    protected Room currentRoom;
    protected StuffType type;
    protected boolean isOn = false;
    
    // Observer pattern implementation
    private final List<DeviceObserver> observers = new ArrayList<>();
    private final List<String> eventReport = new ArrayList<>();
    
    public UsableObject(String name, Room room, StuffType type) {
        this.name = name;
        this.currentRoom = room;
        this.type = type;
        room.addDevice(this);
    }
    
    public void turnOn() {
        if (!isOn) {
            isOn = true;
            addEventToReport(name + " turned ON in " + currentRoom.getName());
            notifyObserver();
        }
    }
    
    public void turnOff() {
        if (isOn) {
            isOn = false;
            addEventToReport(name + " turned OFF in " + currentRoom.getName());
            notifyObserver();
        }
    }
    
    public void usingStuff() {
        turnOn();
    }
    
    public void setState(DeviceState state) {
        this.state = state;
        addEventToReport(name + " changed state to " + state.getClass().getSimpleName());
        notifyObserver();
    }
    
    public void addObserver(DeviceObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(DeviceObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyObserver() {
        for (DeviceObserver observer : observers) {
            observer.update(this);
        }
    }
    
    public void addEventToReport(String event) {
        eventReport.add(event);
    }
    
    public List<String> getEventReport() {
        return eventReport;
    }
    
    public String getName() {
        return name;
    }
    
    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    public StuffType getType() {
        return type;
    }
    
    public boolean isOn() {
        return isOn;
    }
    
    public DeviceState getState() {
        return state;
    }
}