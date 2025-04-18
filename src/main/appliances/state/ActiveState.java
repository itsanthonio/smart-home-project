package appliances.state;

import appliances.UsableObject;

/**
 * Represents the active state of a device.
 */
public class ActiveState implements DeviceState {
    public ActiveState() {
    }
    
    @Override
    public void handle(UsableObject device) {
        device.turnOn();
    }
    
    @Override
    public String getStateName() {
        return "Active";
    }
}