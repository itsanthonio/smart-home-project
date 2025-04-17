package house.strategy;

import appliances.devices.AC;
import appliances.devices.Door;
import appliances.devices.Light;
import creatures.entities.Entity;
import house.Room;
import house.SmartHome;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Strategy for vacation mode operations.
 */
public class VacationModeStrategy implements Strategy {
    private final SmartHome smartHome;
    
    public VacationModeStrategy(SmartHome smartHome) {
        this.smartHome = smartHome;
    }
    
    @Override
    public void findActivity(Entity entity) throws IOException {
        // In vacation mode, simulate activity even while away
        
        // Periodically turn some lights on and off to simulate presence
        List<Room> rooms = smartHome.getRooms();
        if (new Random().nextInt(10) > 6) { // 40% chance to toggle lights
            Room randomRoom = rooms.get(new Random().nextInt(rooms.size()));
            List<Light> lights = randomRoom.getDevicesByType(Light.class);
            if (!lights.isEmpty()) {
                Light randomLight = lights.get(new Random().nextInt(lights.size()));
                if (randomLight.isOn()) {
                    randomLight.turnOff();
                } else {
                    randomLight.turnOn();
                }
            }
        }
        
        // Ensure all doors are locked
        for (Room room : rooms) {
            List<Door> doors = room.getDevicesByType(Door.class);
            for (Door door : doors) {
                if (!door.isLocked()) {
                    door.turnOff(); // Close
                    door.lock();    // Lock
                }
            }
        }
        
        // Set ACs to energy-saving mode
        for (Room room : rooms) {
            List<AC> acs = room.getDevicesByType(AC.class);
            for (AC ac : acs) {
                if (ac.isOn()) {
                    // Set to energy-saving temperature
                    ac.setTemperature(26); // Higher in summer to save energy
                }
            }
        }
        
        // The user is away, so they're just waiting
        entity.waiting();
    }
    
    @Override
    public String getName() {
        return "Vacation Mode";
    }
}