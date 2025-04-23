package appliances.devices;

import appliances.UsableObject;
import appliances.StuffType;
import appliances.state.RestingState;
import appliances.state.*;

import house.Room;

/**
 * Represents a smart vacuum device in the smart home.
 */
public class SmartVacuum extends UsableObject {
    private int batteryLevel = 100; // 0-100
    private String mode = "Auto"; // Auto, Spot, Edge
    private int dustBinLevel = 0; // 0-100
    private Room targetRoom;
    
    public SmartVacuum(String name, Room room) {
        super(name, room, StuffType.SMART_VACUUM);
        this.state = new RestingState(this);
        this.targetRoom = room;
    }
    
    public void setMode(String mode) {
        if (mode.equals("Auto") || mode.equals("Spot") || mode.equals("Edge")) {
            this.mode = mode;
            addEventToReport(name + " mode set to " + mode);
            notifyObserver();
        }
    }
    
    public void startCleaning() {
        if (batteryLevel > 10) {
            turnOn();
            addEventToReport(name + " started cleaning " + targetRoom.getName());
            notifyObserver();
        } else {
            addEventToReport(name + " battery too low to start cleaning");
            notifyObserver();
        }
    }
    
    public void stopCleaning() {
        turnOff();
        addEventToReport(name + " stopped cleaning");
        notifyObserver();
    }
    
    public void goToChargingStation() {
        turnOff();
        addEventToReport(name + " returning to charging station");
        notifyObserver();
    }
    
    public void charge() {
        if (batteryLevel < 100) {
            batteryLevel = Math.min(batteryLevel + 10, 100);
            addEventToReport(name + " charging, battery level: " + batteryLevel + "%");
            notifyObserver();
        } else {
            addEventToReport(name + " fully charged");
            notifyObserver();
        }
    }
    
    public void setTargetRoom(Room room) {
        this.targetRoom = room;
        addEventToReport(name + " target room set to " + room.getName());
        notifyObserver();
    }
    
    public void emptyDustBin() {
        dustBinLevel = 0;
        addEventToReport(name + " dust bin emptied");
        notifyObserver();
    }
    
    /**
     * Simulate cleaning progress
     */
    public void simulateCleaningProgress() {
        if (isOn()) {
            // Simulate battery drain
            batteryLevel = Math.max(0, batteryLevel - 5);
            // Simulate dust collection
            dustBinLevel = Math.min(100, dustBinLevel + 10);
            
            addEventToReport(name + " cleaning in progress - Battery: " + batteryLevel + 
                           "%, Dust Bin: " + dustBinLevel + "%");
            
            if (batteryLevel <= 10) {
                addEventToReport(name + " battery low, returning to charging station");
                goToChargingStation();
            }
            
            if (dustBinLevel >= 90) {
                addEventToReport(name + " dust bin nearly full, needs emptying");
            }
            
            notifyObserver();
        }
    }
    
    public int getBatteryLevel() {
        return batteryLevel;
    }
    
    public String getMode() {
        return mode;
    }
    
    public int getDustBinLevel() {
        return dustBinLevel;
    }
    
    public Room getTargetRoom() {
        return targetRoom;
    }


    
}