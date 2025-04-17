package house.strategy;

import appliances.UsableObject;
import appliances.devices.Fridge;
import creatures.entities.Entity;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Strategy for normal mode operations.
 */
public class NormalModeStrategy implements Strategy {
    
    @Override
    public void findActivity(Entity entity) throws IOException {
        // Check hunger level first
        if (entity.getCurrentActionProgress() >= 5) {
            entity.increaseHungerLevel();
            entity.stopCurrentAction();
        }
        
        // If currently using an object, continue using it
        if (entity.getCurrentObject() != null) {
            entity.increaseProgress();
            return;
        }
        
        // If hungry, find a fridge
        List<UsableObject> roomDevices = entity.getCurrentRoom().getDevices();
        if (new Random().nextInt(10) > 7) { // 30% chance to get hungry
            for (UsableObject device : roomDevices) {
                if (device instanceof Fridge) {
                    entity.useStuff(device);
                    return;
                }
            }
        }
        
        // Otherwise, randomly select an available device
        if (!roomDevices.isEmpty() && new Random().nextBoolean()) {
            int deviceIndex = new Random().nextInt(roomDevices.size());
            entity.useStuff(roomDevices.get(deviceIndex));
        } else {
            entity.waiting();
        }
    }
    
    @Override
    public String getName() {
        return "Normal Mode";
    }
}