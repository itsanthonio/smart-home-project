package house;

import appliances.factories.DeviceFactory;
import creatures.factories.UserFactory;
import creatures.entities.User;
import house.strategy.NormalModeStrategy;
import house.strategy.NightModeStrategy;
import house.strategy.Strategy;
import house.strategy.VacationModeStrategy;
import system.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main controller for the smart home.
 * Implements the Singleton pattern.
 */
public class SmartHome {
    private static SmartHome instance = null;
    
    private final List<Room> rooms = new ArrayList<>();
    private final Logger logger;
    private DeviceFactory deviceFactory;
    private UserFactory userFactory;
    
    private Strategy currentStrategy;
    private String currentMode;
    
    private SmartHome() {
        this.logger = new Logger();
        this.deviceFactory = DeviceFactory.getInstance(logger);
        this.userFactory = UserFactory.getInstance();
        this.currentStrategy = new NormalModeStrategy();
        this.currentMode = "normal";
    }
    
    public static SmartHome getInstance() {
        if (instance == null) {
            instance = new SmartHome();
        }
        return instance;
    }
    
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
    public List<Room> getRooms() {
        return rooms;
    }
    
    public Room getRoomByName(String name) {
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(name)) {
                return room;
            }
        }
        return null;
    }
    
    public void setStrategy(Strategy strategy) {
        this.currentStrategy = strategy;
        logger.log("Smart Home mode changed to: " + strategy.getName());
        
        // Update all users' strategies
        for (User user : userFactory.getUsers()) {
            user.setStrategy(strategy);
        }
    }
    
    public void activateNightMode() {
        setStrategy(new NightModeStrategy(this));
    }
    
    public void activateNormalMode() {
        setStrategy(new NormalModeStrategy());
    }
    
    public void activateVacationMode() {
        setStrategy(new VacationModeStrategy(this));
    }
    
    public void simulateTimeStep() throws IOException {
        // For each time step, every user performs an action
        for (User user : userFactory.getUsers()) {
            user.findActivity();
        }
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public DeviceFactory getDeviceFactory() {
        return deviceFactory;
    }
    
    public UserFactory getUserFactory() {
        return userFactory;
    }
    
    public Strategy getCurrentStrategy() {
        return currentStrategy;
    }

    

    public void setMode(String mode) {
        this.currentMode = mode;
        switch (mode.toLowerCase()) {
            case "night":
                activateNightMode();
                break;
            case "day":
            case "normal":
                activateNormalMode();
                break;
            case "vacation":
                activateVacationMode();
                break;
            case "energysaving":
                // Add energy saving strategy if needed
                logger.log("Energy saving mode not implemented yet");
                break;
            case "away":
                // Add away mode strategy if needed
                logger.log("Away mode not implemented yet");
                break;
            default:
                logger.log("Invalid mode: " + mode + ". Defaulting to normal mode.");
                activateNormalMode();
        }
    }

    public String getCurrentMode() {
        return currentMode;
    }
}