package main;

import appliances.devices.*;
import creatures.entities.User;
import house.Room;
import house.SmartHome;
import java.io.IOException;
import java.util.Scanner;
import system.Logger;

/**
 * Main application class for the Smart Home system.
 */
public class SmartHomeApplication {

    private static final SmartHome smartHome = SmartHome.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeSmartHome();
        
        System.out.println("Welcome to Smart Home Controller!");
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getMenuChoice();
            
            try {
                switch (choice) {
                    case 1:
                        controlDevice();
                        break;
                    case 2:
                        controlRoom();
                        break;
                    case 3:
                        changeHomeMode();
                        break;

                    case 4:
                        viewLogs();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Exiting Smart Home Controller. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
   private static void initializeSmartHome() {
    Logger logger = smartHome.getLogger();
    logger.log("Initializing Smart Home...");
    
    // Create rooms
    Room livingRoom = new Room("Living Room");
    Room kitchen = new Room("Kitchen");
    Room bedroom = new Room("Bedroom");
    Room bathroom = new Room("Bathroom");
    Room office = new Room("Home Office");
    Room garage = new Room("Garage");
    
    smartHome.addRoom(livingRoom);
    smartHome.addRoom(kitchen);
    smartHome.addRoom(bedroom);
    smartHome.addRoom(bathroom);
    smartHome.addRoom(office);
    smartHome.addRoom(garage);
    
    // Create devices using the factory
    Light livingRoomLight = smartHome.getDeviceFactory().createLight("Living Room Light", livingRoom);
    TV livingRoomTV = smartHome.getDeviceFactory().createTV("Living Room TV", livingRoom);
    Door frontDoor = smartHome.getDeviceFactory().createDoor("Front Door", livingRoom);
    AudioSystem livingRoomAudio = smartHome.getDeviceFactory().createAudioSystem("Living Room Sound System", livingRoom);
    
    Fridge kitchenFridge = smartHome.getDeviceFactory().createFridge("Kitchen Fridge", kitchen);
    Light kitchenLight = smartHome.getDeviceFactory().createLight("Kitchen Light", kitchen);
    Thermostat kitchenThermostat = smartHome.getDeviceFactory().createThermostat("Kitchen Thermostat", kitchen);
    
    Light bedroomLight = smartHome.getDeviceFactory().createLight("Bedroom Light", bedroom);
    AC bedroomAC = smartHome.getDeviceFactory().createAC("Bedroom AC", bedroom);
    
    Light bathroomLight = smartHome.getDeviceFactory().createLight("Bathroom Light", bathroom);
    
    Computer officeComputer = smartHome.getDeviceFactory().createComputer("Office Computer", office);
    Light officeLight = smartHome.getDeviceFactory().createLight("Office Light", office);
    
    SmartVacuum vacuumBot = smartHome.getDeviceFactory().createSmartVacuum("Vacuum Bot", livingRoom);
    SecuritySystem securitySystem = smartHome.getDeviceFactory().createSecuritySystem("Home Security", livingRoom);
    
    // Create users
    User john = smartHome.getUserFactory().create("John", 35, livingRoom, smartHome.getCurrentStrategy());
    User alice = smartHome.getUserFactory().create("Alice", 32, bedroom, smartHome.getCurrentStrategy());
    
    logger.log("Smart Home initialized with " + smartHome.getRooms().size() + " rooms and " + 
              smartHome.getUserFactory().getUsers().size() + " users");
}
    
    private static void displayMenu() {
        System.out.println("\n===== SMART HOME CONTROLLER =====");
        System.out.println("1. Control Device");
        System.out.println("2. Control Room");
        System.out.println("3. Change Home Mode");
        System.out.println("4. View Logs");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void controlDevice() {
        System.out.println("\n===== DEVICE CONTROL =====");
        
        // Select a room first
        Room selectedRoom = selectRoom();
        if (selectedRoom == null) return;
        
        // Show the devices in the room
        System.out.println("\nDevices in " + selectedRoom.getName() + ":");
        for (int i = 0; i < selectedRoom.getDevices().size(); i++) {
            System.out.println((i + 1) + ". " + selectedRoom.getDevices().get(i).getName() + 
                              " (" + (selectedRoom.getDevices().get(i).isOn() ? "ON" : "OFF") + ")");
        }
        
        System.out.print("\nSelect device (0 to go back): ");
        int deviceChoice = getMenuChoice();
        
        if (deviceChoice <= 0 || deviceChoice > selectedRoom.getDevices().size()) {
            return;
        }
        
        // Get the selected device
        var selectedDevice = selectedRoom.getDevices().get(deviceChoice - 1);
        
        // Device control menu
        System.out.println("\n===== CONTROLLING " + selectedDevice.getName() + " =====");
        System.out.println("1. Turn On");
        System.out.println("2. Turn Off");
        System.out.println("3. Device Specific Actions");
        System.out.print("Enter choice: ");
        
        int actionChoice = getMenuChoice();
        
        switch (actionChoice) {
            case 1:
                selectedDevice.turnOn();
                System.out.println(selectedDevice.getName() + " turned ON");
                break;
            case 2:
                selectedDevice.turnOff();
                System.out.println(selectedDevice.getName() + " turned OFF");
                break;
            case 3:
                performDeviceSpecificActions(selectedDevice);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
    
    private static void performDeviceSpecificActions(appliances.UsableObject device) {
        System.out.println("\n===== DEVICE SPECIFIC ACTIONS =====");
        
        if (device instanceof Light) {
            Light light = (Light) device;
            System.out.print("Enter brightness (0-100): ");
            int brightness = getMenuChoice();
            light.setBrightness(brightness);
            System.out.println("Brightness set to " + brightness);
        } else if (device instanceof TV) {
            TV tv = (TV) device;
            System.out.println("1. Change Channel");
            System.out.println("2. Change Volume");
            System.out.print("Enter choice: ");
            int choice = getMenuChoice();
            
            if (choice == 1) {
                System.out.print("Enter channel number: ");
                int channel = getMenuChoice();
                tv.setChannel(channel);
                System.out.println("Channel changed to " + channel);
            } else if (choice == 2) {
                System.out.print("Enter volume level (0-100): ");
                int volume = getMenuChoice();
                tv.setVolume(volume);
                System.out.println("Volume set to " + volume);
            }
        } else if (device instanceof AC) {
            AC ac = (AC) device;
            System.out.println("1. Set Temperature");
            System.out.println("2. Change Mode");
            System.out.print("Enter choice: ");
            int choice = getMenuChoice();
            
            if (choice == 1) {
                System.out.print("Enter temperature (16-30): ");
                int temp = getMenuChoice();
                ac.setTemperature(temp);
                System.out.println("Temperature set to " + temp + "°C");
            } else if (choice == 2) {
                System.out.println("Available modes: Cool, Heat, Fan");
                System.out.print("Enter mode: ");
                String mode = scanner.nextLine();
                ac.setMode(mode);
                System.out.println("Mode changed to " + mode);
            }
        } else if (device instanceof Door) {
            Door door = (Door) device;
            System.out.println("1. Lock Door");
            System.out.println("2. Unlock Door");
            System.out.print("Enter choice: ");
            int choice = getMenuChoice();
            
            if (choice == 1) {
                door.lock();
                System.out.println("Door locked");
            } else if (choice == 2) {
                door.unlock();
                System.out.println("Door unlocked");
            }
        } else if (device instanceof Fridge) {
            Fridge fridge = (Fridge) device;
            System.out.println("1. Check Food Items");
            System.out.println("2. Restock Food");
            System.out.println("3. Consume Food");
            System.out.print("Enter choice: ");
            int choice = getMenuChoice();
            
            if (choice == 1) {
                System.out.println("Food items in fridge: " + fridge.getFoodItems());
            } else if (choice == 2) {
                System.out.print("Enter amount to restock: ");
                int amount = getMenuChoice();
                fridge.restockFood(amount);
                System.out.println("Restocked " + amount + " food items");
            } else if (choice == 3) {
                fridge.consumeFood();
                System.out.println("Consumed 1 food item");
            }
        } else if (device instanceof Thermostat) {
            Thermostat thermostat = (Thermostat) device;
            System.out.println("1. Set Target Temperature");
            System.out.println("2. Set Mode");
            System.out.println("3. Toggle Scheduling");
            System.out.print("Enter choice: ");
            int choice = getMenuChoice();
            
            if (choice == 1) {
                System.out.print("Enter target temperature (15-30): ");
                int temp = getMenuChoice();
                thermostat.setTargetTemperature(temp);
                System.out.println("Target temperature set to " + temp + "°C");
            } else if (choice == 2) {
                System.out.println("Available modes: Auto, Heat, Cool, Off");
                System.out.print("Enter mode: ");
                String mode = scanner.nextLine();
                thermostat.setMode(mode);
                System.out.println("Mode changed to " + mode);
            } else if (choice == 3) {
                if (thermostat.isSchedulingEnabled()) {
                    thermostat.disableScheduling();
                    System.out.println("Scheduling disabled");
                } else {
                    thermostat.enableScheduling();
                    System.out.println("Scheduling enabled");
                }
            }
        } else if (device instanceof AudioSystem) {
            AudioSystem audioSystem = (AudioSystem) device;
            System.out.println("1. Set Volume");
            System.out.println("2. Set Mode");
            System.out.println("3. Play Track");
            System.out.println("4. Stop Playback");
            System.out.print("Enter choice: ");
            int choice = getMenuChoice();
            
            if (choice == 1) {
                System.out.print("Enter volume level (0-100): ");
                int volume = getMenuChoice();
                audioSystem.setVolume(volume);
                System.out.println("Volume set to " + volume);
            } else if (choice == 2) {
                System.out.println("Available modes: Stereo, Surround, Party");
                System.out.print("Enter mode: ");
                String mode = scanner.nextLine();
                audioSystem.setMode(mode);
                System.out.println("Mode changed to " + mode);
            } else if (choice == 3) {
                System.out.print("Enter track name: ");
                String track = scanner.nextLine();
                audioSystem.playTrack(track);
                System.out.println("Now playing: " + track);
            } else if (choice == 4) {
                audioSystem.stopPlayback();
                System.out.println("Playback stopped");
            }
        } else if (device instanceof Computer) {
            Computer computer = (Computer) device;
            System.out.println("1. Run Application");
            System.out.println("2. Close Application");
            System.out.println("3. Sleep/Wake");
            System.out.println("4. Check System Status");
            System.out.print("Enter choice: ");
            int choice = getMenuChoice();
            
            if (choice == 1) {
                System.out.print("Enter application name: ");
                String app = scanner.nextLine();
                computer.runApplication(app);
                System.out.println("Running application: " + app);
            } else if (choice == 2) {
                computer.closeApplication();
                System.out.println("Application closed");
            } else if (choice == 3) {
                if (computer.isInSleepMode()) {
                    computer.exitSleepMode();
                    System.out.println("Computer woken from sleep");
                } else {
                    computer.enterSleepMode();
                    System.out.println("Computer entered sleep mode");
                }
            } else if (choice == 4) {
                System.out.println("CPU Usage: " + computer.getCpuUsage() + "%");
                System.out.println("Memory Usage: " + computer.getMemoryUsage() + "%");
                System.out.println("Current Application: " + computer.getCurrentApplication());
            }
        } else if (device instanceof SecuritySystem) {
            SecuritySystem security = (SecuritySystem) device;
            System.out.println("1. Arm/Disarm");
            System.out.println("2. Set Mode");
            System.out.println("3. Simulate Motion Detection");
            System.out.println("4. View Alerts");
            System.out.println("5. Clear Alerts");
            System.out.print("Enter choice: ");
            int choice = getMenuChoice();
            
            if (choice == 1) {
                if (security.isArmed()) {
                    security.disarm();
                    System.out.println("Security system disarmed");
                } else {
                    security.arm();
                    System.out.println("Security system armed in " + security.getMode() + " mode");
                }
            } else if (choice == 2) {
                System.out.println("Available modes: Home, Away, Night");
                System.out.print("Enter mode: ");
                String mode = scanner.nextLine();
                security.setMode(mode);
                System.out.println("Mode set to: " + mode);
            } else if (choice == 3) {
                security.detectMotion();
                System.out.println("Motion detected!");
            } else if (choice == 4) {
                System.out.println("\n===== SECURITY ALERTS =====");
                if (security.getAlerts().isEmpty()) {
                    System.out.println("No alerts");
                } else {
                    for (String alert : security.getAlerts()) {
                        System.out.println("- " + alert);
                    }
                }
            } else if (choice == 5) {
                security.clearAlerts();
                System.out.println("All alerts cleared");
            }
        } else if (device instanceof SmartVacuum) {
        SmartVacuum vacuum = (SmartVacuum) device;
        System.out.println("1. Start Cleaning");
        System.out.println("2. Stop Cleaning");
        System.out.println("3. Go To Charging Station");
        System.out.println("4. Set Mode");
        System.out.println("5. Empty Dust Bin");
        System.out.println("6. Check Status");
        System.out.print("Enter choice: ");
        int choice = getMenuChoice();
        
        if (choice == 1) {
            vacuum.startCleaning();
            System.out.println("Started cleaning in " + vacuum.getMode() + " mode");
        } else if (choice == 2) {
            vacuum.stopCleaning();
            System.out.println("Stopped cleaning");
        } else if (choice == 3) {
            vacuum.goToChargingStation();
            System.out.println("Returning to charging station");
        } else if (choice == 4) {
            System.out.println("Available modes: Auto, Spot, Edge");
            System.out.print("Enter mode: ");
            String mode = scanner.nextLine();
            vacuum.setMode(mode);
            System.out.println("Mode set to: " + mode);
        } else if (choice == 5) {
            vacuum.emptyDustBin();
            System.out.println("Dust bin emptied");
        } else if (choice == 6) {
            System.out.println("Battery Level: " + vacuum.getBatteryLevel() + "%");
            System.out.println("Dust Bin Level: " + vacuum.getDustBinLevel() + "%");
            System.out.println("Current Mode: " + vacuum.getMode());
            System.out.println("Target Room: " + vacuum.getTargetRoom().getName());
        }
    }else {
            System.out.println("No specific actions available for this device type.");
        }
    }
    
    private static Room selectRoom() {
        System.out.println("\n===== ROOMS =====");
        var rooms = smartHome.getRooms();
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ". " + rooms.get(i).getName());
        }
        
        System.out.print("\nSelect room (0 to go back): ");
        int roomChoice = getMenuChoice();
        
        if (roomChoice <= 0 || roomChoice > rooms.size()) {
            return null;
        }
        
        return rooms.get(roomChoice - 1);
    }
    
    private static void controlRoom() throws IOException {
        System.out.println("\n===== ROOM CONTROL =====");
        
        Room selectedRoom = selectRoom();
        if (selectedRoom == null) return;
        
        System.out.println("\n===== CONTROLLING " + selectedRoom.getName() + " =====");
        System.out.println("1. Turn All Devices On");
        System.out.println("2. Turn All Devices Off");
        System.out.println("3. Set Room Temperature");
        System.out.println("4. View Room Status");
        System.out.print("Enter choice: ");
        
        int choice = getMenuChoice();
        
        switch (choice) {
            case 1:
                for (var device : selectedRoom.getDevices()) {
                    device.turnOn();
                }
                System.out.println("All devices in " + selectedRoom.getName() + " turned ON");
                break;
            case 2:
                for (var device : selectedRoom.getDevices()) {
                    device.turnOff();
                }
                System.out.println("All devices in " + selectedRoom.getName() + " turned OFF");
                break;
            case 3:
                System.out.print("Enter temperature (15-30): ");
                int temp = getMenuChoice();
                // Find thermostat or AC in the room
                boolean found = false;
                for (var device : selectedRoom.getDevices()) {
                    if (device instanceof Thermostat) {
                        ((Thermostat) device).setTargetTemperature(temp);
                        found = true;
                        break;
                    } else if (device instanceof AC) {
                        ((AC) device).setTemperature(temp);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    System.out.println("Temperature in " + selectedRoom.getName() + " set to " + temp + "°C");
                } else {
                    System.out.println("No temperature control device found in " + selectedRoom.getName());
                }
                break;
            case 4:
                System.out.println("\n===== " + selectedRoom.getName() + " STATUS =====");
                System.out.println("Devices: " + selectedRoom.getDevices().size());
                int onDevices = 0;
                for (var device : selectedRoom.getDevices()) {
                    if (device.isOn()) onDevices++;
                }
                System.out.println("Devices ON: " + onDevices);
                System.out.println("Devices OFF: " + (selectedRoom.getDevices().size() - onDevices));
                
                // List users in the room
                System.out.println("\nUsers in room:");
                boolean usersFound = false;
                for (User user : smartHome.getUserFactory().getUsers()) {
                    if (user.getCurrentRoom() == selectedRoom) {
                        System.out.println("- " + user.getName());
                        usersFound = true;
                    }
                }
                if (!usersFound) {
                    System.out.println("No users currently in this room");
                }
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
    
    private static void changeHomeMode() {
        System.out.println("\n===== HOME MODE =====");
        System.out.println("Current Mode: " + smartHome.getCurrentMode());
        System.out.println("\n1. Day Mode");
        System.out.println("2. Night Mode");
        System.out.println("3. Away Mode");
        System.out.println("4. Vacation Mode");
        System.out.println("5. Energy Saving Mode");
        System.out.print("Enter choice: ");
        
        int choice = getMenuChoice();
        String newMode = "";
        
        switch (choice) {
            case 1:
                newMode = "Day";
                break;
            case 2:
                newMode = "Night";
                break;
            case 3:
                newMode = "Away";
                break;
            case 4:
                newMode = "Vacation";
                break;
            case 5:
                newMode = "EnergySaving";
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }
        
        smartHome.setMode(newMode);
        System.out.println("Home mode changed to: " + newMode);
    }
    
    
    
    private static void viewLogs() throws IOException {
        System.out.println("\n===== SYSTEM LOGS =====");
        
        var logs = smartHome.getLogger().getLogs();
        
        if (logs.isEmpty()) {
            System.out.println("No logs available");
        } else {
            for (String log : logs) {
                System.out.println(log);
            }
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}