package appliances.devices;

import appliances.StuffType;
import appliances.UsableObject;
import appliances.state.RestingState;
import house.Room;

/**
 * Represents a light device in the smart home.
 */
public class Light extends UsableObject {
    private int brightness = 50; // 0-100
    
    public Light(String name, Room room) {
        super(name, room, StuffType.LIGHT);
        this.state = new RestingState(this);
    }
    
    public void setBrightness(int brightness) {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
            addEventToReport(name + " brightness set to " + brightness + "%");
            notifyObserver();
        }
    }
    
    public int getBrightness() {
        return brightness;
    }
}