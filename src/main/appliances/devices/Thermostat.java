package appliances.devices;

import appliances.UsableObject;
import appliances.StuffType;
import appliances.state.RestingState;
import house.Room;
import appliances.state.*;

/**
 * Represents a thermostat device in the smart home.
 */
public class Thermostat extends UsableObject {
    private int targetTemperature = 22; // Default temperature in Celsius
    private int currentTemperature = 22;
    private String mode = "Auto"; // Auto, Heat, Cool, Off
    private boolean schedulingEnabled = false;
    
    public Thermostat(String name, Room room) {
        super(name, room, StuffType.THERMOSTAT);
        this.state = new RestingState(this);
    }
    
    public void setTargetTemperature(int temperature) {
        if (temperature >= 15 && temperature <= 30) {
            this.targetTemperature = temperature;
            addEventToReport(name + " target temperature set to " + temperature + "°C");
            notifyObserver();
        }
    }
    
    public void setMode(String mode) {
        if (mode.equals("Auto") || mode.equals("Heat") || mode.equals("Cool") || mode.equals("Off")) {
            this.mode = mode;
            addEventToReport(name + " mode set to " + mode);
            notifyObserver();
        }
    }
    
    public void enableScheduling() {
        schedulingEnabled = true;
        addEventToReport(name + " scheduling enabled");
        notifyObserver();
    }
    
    public void disableScheduling() {
        schedulingEnabled = false;
        addEventToReport(name + " scheduling disabled");
        notifyObserver();
    }
    
    /**
     * Update the current temperature based on environmental factors and system operation.
     * For simulation purposes.
     */
    public void updateCurrentTemperature() {
        if (!isOn()) {
            return;
        }
        
        // Simple logic to simulate temperature changes
        if (mode.equals("Heat") && currentTemperature < targetTemperature) {
            currentTemperature++;
        } else if (mode.equals("Cool") && currentTemperature > targetTemperature) {
            currentTemperature--;
        } else if (mode.equals("Auto")) {
            if (currentTemperature < targetTemperature) {
                currentTemperature++;
            } else if (currentTemperature > targetTemperature) {
                currentTemperature--;
            }
        }
        
        addEventToReport(name + " current temperature: " + currentTemperature + "°C");
        notifyObserver();
    }
    
    public int getTargetTemperature() {
        return targetTemperature;
    }
    
    public int getCurrentTemperature() {
        return currentTemperature;
    }
    
    public String getMode() {
        return mode;
    }
    
    public boolean isSchedulingEnabled() {
        return schedulingEnabled;
    }
}