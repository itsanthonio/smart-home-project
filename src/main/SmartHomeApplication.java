package main;

import appliances.devices.*;
import creatures.entities.User;
import house.Room;
import house.SmartHome;
import system.Logger;

import java.io.IOException;
import java.util.Scanner;

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
                        simulateTimeStep();
                        break;
                    case 5:
                        viewLogs();
                        break;
                    case 6:
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
        
        smartHome.addRoom(livingRoom);
        smartHome.addRoom(kitchen);
        smartHome.addRoom(bedroom);
        smartHome.addRoom(bathroom);
        
        // Create devices using the factory
        Light livingRoomLight = smartHome.getDeviceFactory().createLight("Living Room Light", livingRoom);
        TV livingRoomTV = smartHome.getDeviceFactory().createTV("Living Room TV", livingRoom);
        Door frontDoor = smartHome.getDeviceFactory().createDoor("Front Door", livingRoom);
        
        Fridge kitchenFridge = smartHome.getDeviceFactory().createFridge("Kitchen Fridge", kitchen);
        Light kitchenLight = smartHome.getDeviceFactory().createLight("Kitchen Light", kitchen);
        
        Light bedroomLight = smartHome.getDeviceFactory().createLight("Bedroom Light", bedroom);
        AC bedroomAC = smartHome.getDeviceFactory().createAC("Bedroom AC", bedroom);
        
        Light bathroomLight = smartHome.getDeviceFactory().createLight("Bathroom Light", bathroom);
        
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
        System.out.println("4. Simulate Time Step");
        System.out.println("5. View Logs");
        System.out.println("6. Exit");
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
        
        // Display devices in the room
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
        
        // Get selected device
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
        }
    }
    
    private static void controlRoom() {
        System.out.println("\n===== ROOM CONTROL =====");
        
        Room selectedRoom = selectRoom();
        if (selectedRoom == null) return;
        
        System.out.println("\n===== CONTROLLING " + selectedRoom.getName() + " =====");
        System.out.println("1. Turn On All Devices");
        System.out.println("2. Turn Off All Devices");
        System.out.print("Enter choice: ");
        
        int choice = getMenuChoice();
        
        switch (choice) {
            case 1:
                selectedRoom.turnOnAllDevices();
                System.out.println("All devices in " + selectedRoom.getName() + " turned ON");
                break;
            case 2:
                selectedRoom.turnOffAllDevices();
                System.out.println("All devices in " + selectedRoom.getName() + " turned OFF");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
    
    private static Room selectRoom() {
        System.out.println("\nAvailable Rooms:");
        for (int i = 0; i < smartHome.getRooms().size(); i++) {
            System.out.println((i + 1) + ". " + smartHome.getRooms().get(i).getName());
        }
        
        System.out.print("\nSelect room (0 to go back): ");
        int roomChoice = getMenuChoice();
        
        if (roomChoice <= 0 || roomChoice > smartHome.getRooms().size()) {
            return null;
        }
        
        return smartHome.getRooms().get(roomChoice - 1);
    }
    
    private static void changeHomeMode() {
        System.out.println("\n===== CHANGE HOME MODE =====");
        System.out.println("1. Normal Mode");
        System.out.println("2. Night Mode");
        System.out.println("3. Vacation Mode");
        System.out.print("Enter choice: ");
        
        int choice = getMenuChoice();
        
        switch (choice) {
            case 1:
                smartHome.activateNormalMode();
                System.out.println("Normal Mode activated");
                break;
            case 2:
                smartHome.activateNightMode();
                System.out.println("Night Mode activated");
                break;
            case 3:
                smartHome.activateVacationMode();
                System.out.println("Vacation Mode activated");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
    
    private static void simulateTimeStep() throws IOException {
        System.out.println("\n===== SIMULATING TIME STEP =====");
        smartHome.simulateTimeStep();
        System.out.println("Time step simulated");
    }
    
    private static void viewLogs() {
        System.out.println("\n===== SYSTEM LOGS =====");
        smartHome.getLogger().printAllLogs();
    }
}