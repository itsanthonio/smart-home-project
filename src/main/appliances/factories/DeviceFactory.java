package appliances.factories;

import appliances.UsableObject;
import appliances.StuffType;
import appliances.devices.*;
import appliances.observer.DeviceObserver;
import house.Room;
import system.Logger;

/**
 * Factory for creating different types of devices.
 * Implements the Factory pattern.
 */
public class DeviceFactory {
    private static DeviceFactory instance = null;
    private final Logger logger;
    
    private DeviceFactory(Logger logger) {
        this.logger = logger;
    }
    
    public static DeviceFactory getInstance(Logger logger) {
        if (instance == null) {
            instance = new DeviceFactory(logger);
        }
        return instance;
    }
    
    public Light createLight(String name, Room room) {
        Light light = new Light(name, room);
        registerObservers(light);
        return light;
    }
    
    public Fridge createFridge(String name, Room room) {
        Fridge fridge = new Fridge(name, room);
        registerObservers(fridge);
        return fridge;
    }
    
    public TV createTV(String name, Room room) {
        TV tv = new TV(name, room);
        registerObservers(tv);
        return tv;
    }
    
    public AC createAC(String name, Room room) {
        AC ac = new AC(name, room);
        registerObservers(ac);
        return ac;
    }
    
    public Door createDoor(String name, Room room) {
        Door door = new Door(name, room);
        registerObservers(door);
        return door;
    }
    
    public AudioSystem createAudioSystem(String name, Room room) {
        AudioSystem audioSystem = new AudioSystem(name, room);
        registerObservers(audioSystem);
        return audioSystem;
    }
    
    public Computer createComputer(String name, Room room) {
        Computer computer = new Computer(name, room);
        registerObservers(computer);
        return computer;
    }
    
    public Thermostat createThermostat(String name, Room room) {
        Thermostat thermostat = new Thermostat(name, room);
        registerObservers(thermostat);
        return thermostat;
    }
    
    public SecuritySystem createSecuritySystem(String name, Room room) {
        SecuritySystem securitySystem = new SecuritySystem(name, room);
        registerObservers(securitySystem);
        return securitySystem;
    }
    
    public SmartVacuum createSmartVacuum(String name, Room room) {
        SmartVacuum smartVacuum = new SmartVacuum(name, room);
        registerObservers(smartVacuum);
        return smartVacuum;
    }
    
    private void registerObservers(UsableObject device) {
        // Register with log observer
        DeviceObserver logObserver = new appliances.observer.LogObserver(logger);
        device.addObserver(logObserver);
    }
}