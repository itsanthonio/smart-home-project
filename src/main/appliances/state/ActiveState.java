package appliances.state;

import appliances.UsableObject;

/**
 * Represents the active state of a device.
 */
public class ActiveState implements DeviceState {
    private final UsableObject device;
    
    public ActiveState(UsableObject device) {
        this.device = device;
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