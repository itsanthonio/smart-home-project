package appliances.devices;

import appliances.UsableObject;
import appliances.StuffType;
import appliances.state.RestingState;
import house.Room;

/**
 * Represents a TV device in the smart home.
 */
public class TV extends UsableObject {
    private int channel = 1;
    private int volume = 20;
    
    public TV(String name, Room room) {
        super(name, room, StuffType.TV);
        this.state = new RestingState(this);
    }
    
    public void setChannel(int channel) {
        if (channel > 0) {
            this.channel = channel;
            addEventToReport(name + " channel changed to " + channel);
            notifyObserver();
        }
    }
    
    public void setVolume(int volume) {
        if (volume >= 0 && volume <= 100) {
            this.volume = volume;
            addEventToReport(name + " volume set to " + volume);
            notifyObserver();
        }
    }
    
    public int getChannel() {
        return channel;
    }
    
    public int getVolume() {
        return volume;
    }
}