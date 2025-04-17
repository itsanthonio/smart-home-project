package house.strategy;

import appliances.UsableObject;
import appliances.devices.Door;
import appliances.devices.Light;
import creatures.entities.Entity;
import house.Room;
import house.SmartHome;

import java.io.IOException;
import java.util.List;

/**
 * Strategy for night mode operations.
 */
public class NightModeStrategy implements Strategy {
    private final SmartHome smartHome;
    
    public NightModeStrategy(SmartHome smartHome) {
        this.smartHome = smartHome;
    }
    
    @Override
    public void findActivity(Entity entity) throws IOException {
        // In night mode, the user is mostly inactive
        entity.waiting();
        
        // Apply night mode settings to the home
        List<Room> rooms = smartHome.getRooms();
        for (Room room : rooms) {
            // Turn off all lights
            List<Light> lights = room.getDevicesByType(Light.class);
            for (Light light : lights) {
                light.turnOff();
            }
            
            // Lock all doors
            List<Door> doors = room.getDevicesByType(Door.class);
            for (Door door : doors) {
                door.turnOff(); // Close the door
                door.lock();    // Lock the door
            }
        }
    }
    
    @Override
    public String getName() {
        return "Night Mode";
    }
}