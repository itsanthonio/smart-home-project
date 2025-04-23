package appliances.devices;

import appliances.UsableObject;
import appliances.StuffType;
import appliances.state.*;
import appliances.state.RestingState;
import house.Room;

/**
 * Represents an audio system device in the smart home.
 */
public class AudioSystem extends UsableObject {
    private int volume = 20; // 0-100
    private String mode = "Stereo"; // Stereo, Surround, Party
    private String currentTrack = "None";
    
    public AudioSystem(String name, Room room) {
        super(name, room, StuffType.AUDIO_SYSTEM);
        this.state = new RestingState(this);
    }
    
    public void setVolume(int volume) {
        if (volume >= 0 && volume <= 100) {
            this.volume = volume;
            addEventToReport(name + " volume set to " + volume);
            notifyObserver();
        }
    }
    
    public void setMode(String mode) {
        if (mode.equals("Stereo") || mode.equals("Surround") || mode.equals("Party")) {
            this.mode = mode;
            addEventToReport(name + " mode set to " + mode);
            notifyObserver();
        }
    }
    
    public void playTrack(String trackName) {
        this.currentTrack = trackName;
        addEventToReport(name + " now playing: " + trackName);
        notifyObserver();
    }
    
    public void stopPlayback() {
        this.currentTrack = "None";
        addEventToReport(name + " playback stopped");
        notifyObserver();
    }
    
    public int getVolume() {
        return volume;
    }
    
    public String getMode() {
        return mode;
    }
    
    public String getCurrentTrack() {
        return currentTrack;
    }
}