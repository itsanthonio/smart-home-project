package house;

import appliances.UsableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the smart home.
 */
public class Room {
    private final String name;
    private final List<UsableObject> devices = new ArrayList<>();
    
    public Room(String name) {
        this.name = name;
    }
    
    public void addDevice(UsableObject device) {
        devices.add(device);
    }
    
    public void removeDevice(UsableObject device) {
        devices.remove(device);
    }
    
    public List<UsableObject> getDevices() {
        return devices;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Turn on all devices in the room.
     */
    public void turnOnAllDevices() {
        for (UsableObject device : devices) {
            device.turnOn();
        }
    }
    
    /**
     * Turn off all devices in the room.
     */
    public void turnOffAllDevices() {
        for (UsableObject device : devices) {
            device.turnOff();
        }
    }
    
    /**
     * Get all devices of a specific type in the room.
     *
     * @param deviceClass The class of devices to find
     * @return List of devices of the specified type
     */
    public <T extends UsableObject> List<T> getDevicesByType(Class<T> deviceClass) {
        List<T> result = new ArrayList<>();
        for (UsableObject device : devices) {
            if (deviceClass.isInstance(device)) {
                result.add(deviceClass.cast(device));
            }
        }
        return result;
    }
}