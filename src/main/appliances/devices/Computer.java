package appliances.devices;
import appliances.state.*;

import appliances.UsableObject;
import appliances.StuffType;
import appliances.state.RestingState;
import house.Room;

/**
 * Represents a computer device in the smart home.
 */
public class Computer extends UsableObject {
    private boolean sleepMode = false;
    private int cpuUsage = 0; // 0-100
    private int memoryUsage = 0; // 0-100
    private String currentApplication = "None";
    
    public Computer(String name, Room room) {
        super(name, room, StuffType.COMPUTER);
        this.state = new RestingState(this);
    }
    
    public void enterSleepMode() {
        if (!sleepMode) {
            sleepMode = true;
            addEventToReport(name + " entered sleep mode");
            notifyObserver();
        }
    }
    
    public void exitSleepMode() {
        if (sleepMode) {
            sleepMode = false;
            addEventToReport(name + " exited sleep mode");
            notifyObserver();
        }
    }
    
    public void runApplication(String appName) {
        this.currentApplication = appName;
        // Simulate resource usage change
        this.cpuUsage = Math.min(cpuUsage + 20, 100);
        this.memoryUsage = Math.min(memoryUsage + 15, 100);
        addEventToReport(name + " running application: " + appName);
        notifyObserver();
    }
    
    public void closeApplication() {
        addEventToReport(name + " closed application: " + currentApplication);
        this.currentApplication = "None";
        // Simulate resource usage decrease
        this.cpuUsage = Math.max(cpuUsage - 20, 0);
        this.memoryUsage = Math.max(memoryUsage - 15, 0);
        notifyObserver();
    }
    
    @Override
    public void turnOn() {
        super.turnOn();
        if (sleepMode) {
            exitSleepMode();
        }
    }
    
    @Override
    public void turnOff() {
        super.turnOff();
        currentApplication = "None";
        cpuUsage = 0;
        memoryUsage = 0;
    }
    
    public boolean isInSleepMode() {
        return sleepMode;
    }
    
    public int getCpuUsage() {
        return cpuUsage;
    }
    
    public int getMemoryUsage() {
        return memoryUsage;
    }
    
    public String getCurrentApplication() {
        return currentApplication;
    }
}