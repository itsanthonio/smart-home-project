package appliances.state;

import appliances.UsableObject;

/**
 * Represents the broken state of a device.
 */
public class BrokenState implements DeviceState {
    private final UsableObject device;
    
    public BrokenState(UsableObject device) {
        this.device = device;
    }
    
    @Override
    public void handle(UsableObject device) {
        device.turnOff();
        device.addEventToReport(device.getName() + " is broken and needs repair");
    }
    
    @Override
    public String getStateName() {
        return "Broken";
    }
}