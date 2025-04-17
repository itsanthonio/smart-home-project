package appliances.observer;

import appliances.UsableObject;
import system.Logger;

/**
 * Observer that logs device state changes.
 */
public class LogObserver implements DeviceObserver {
    private final Logger logger;
    
    public LogObserver(Logger logger) {
        this.logger = logger;
    }
    
    @Override
    public void update(UsableObject device) {
        String status = device.isOn() ? "ON" : "OFF";
        String location = device.getCurrentRoom().getName();
        String message = device.getName() + " is now " + status + " in " + location;
        
        if (device.getState() != null) {
            message += " (State: " + device.getState().getStateName() + ")";
        }
        
        logger.log(message);
    }
}