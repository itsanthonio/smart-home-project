package appliances.state;

import appliances.UsableObject;

/**
 * Represents the resting state of a device.
 */
public class RestingState implements DeviceState {
    private final UsableObject device;
    
    public RestingState(UsableObject device) {
        this.device = device;
    }
    
    @Override
    public void handle(UsableObject device) {
        device.turnOff();
    }
    
    @Override
    public String getStateName() {
        return "Resting";
    }
}