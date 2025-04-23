package appliances.devices;

import appliances.StuffType;
import appliances.UsableObject;
import appliances.state .RestingState;
import house.Room;

/**
 * Represents an AC device in the smart home.
 */
public class AC extends UsableObject {
    private int temperature = 22; // Default temperature in Celsius
    private String mode = "Cool"; // Cool, Heat, Fan
    
    public AC(String name, Room room) {
        super(name, room, StuffType.AC);
        this.state = new RestingState(this);
    }
    
    public void setTemperature(int temperature) {
        if (temperature >= 16 && temperature <= 30) {
            this.temperature = temperature;
            addEventToReport(name + " temperature set to " + temperature + "°C");
            notifyObserver();
        }
    }
    
    public void setMode(String mode) {
        if (mode.equals("Cool") || mode.equals("Heat") || mode.equals("Fan")) {
            this.mode = mode;
            addEventToReport(name + " mode set to " + mode);
            notifyObserver();
        }
    }
    
    public int getTemperature() {
        return temperature;
    }
    
    public String getMode() {
        return mode;
    }
}