package appliances.devices;

import appliances.StuffType;
import appliances.UsableObject;
import appliances.state.RestingState;
import house.Room;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a security system in the smart home.
 */
public class SecuritySystem extends UsableObject {
    private boolean armed = false;
    private String mode = "Home"; // Home, Away, Night
    private final List<String> alerts = new ArrayList<>();
    private boolean motionDetected = false;
    
    public SecuritySystem(String name, Room room) {
        super(name, room, StuffType.SECURITY_CAMERA);
        this.state = new RestingState(this);
    }
    
    public void arm() {
        if (!armed) {
            armed = true;
            addEventToReport(name + " armed in " + mode + " mode");
            notifyObserver();
        }
    }
    
    public void disarm() {
        if (armed) {
            armed = false;
            addEventToReport(name + " disarmed");
            notifyObserver();
        }
    }
    
    public void setMode(String mode) {
        if (mode.equals("Home") || mode.equals("Away") || mode.equals("Night")) {
            this.mode = mode;
            addEventToReport(name + " mode set to " + mode);
            notifyObserver();
        }
    }
    
    public void detectMotion() {
        motionDetected = true;
        if (armed) {
            String alert = "Motion detected in " + currentRoom.getName() + "!";
            alerts.add(alert);
            addEventToReport("ALERT: " + alert);
            notifyObserver();
        }
    }
    
    public void clearMotionDetection() {
        motionDetected = false;
        addEventToReport(name + " motion detection cleared");
        notifyObserver();
    }
    
    public void triggerAlarm() {
        if (armed) {
            String alert = "Security alarm triggered in " + currentRoom.getName() + "!";
            alerts.add(alert);
            addEventToReport("CRITICAL ALERT: " + alert);
            notifyObserver();
        }
    }
    
    @Override
    public void turnOn() {
        super.turnOn();
        addEventToReport(name + " surveillance activated");
    }
    
    @Override
    public void turnOff() {
        super.turnOff();
        disarm();
        addEventToReport(name + " surveillance deactivated");
    }
    
    public List<String> getAlerts() {
        return new ArrayList<>(alerts);
    }
    
    public void clearAlerts() {
        alerts.clear();
        addEventToReport(name + " alerts cleared");
        notifyObserver();
    }
    
    public boolean isArmed() {
        return armed;
    }
    
    public String getMode() {
        return mode;
    }
    
    public boolean isMotionDetected() {
        return motionDetected;
    }
}