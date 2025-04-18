package appliances.state;

import appliances.UsableObject;

/**
 * Represents the broken state of a device.
 */
public final class BrokenState implements DeviceState {
   

    public BrokenState(UsableObject device) {
        handle(device);
    }    
    @Override
    public void handle(UsableObject device) {
        device.setState(this);
        device.turnOff();
        device.addEventToReport(device.getName() + " is broken and needs repair");
    }
    
    @Override
    public String getStateName() {
        return "Broken";
    }
}