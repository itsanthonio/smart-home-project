package appliances.devices;

import appliances.UsableObject;
import appliances.StuffType;
import appliances.state.RestingState;
import house.Room;

/**
 * Represents a fridge device in the smart home.
 */
public class Fridge extends UsableObject {
    private int temperature = 4; // Default temperature in Celsius
    private int foodItems = 10; // Default food items
    
    public Fridge(String name, Room room) {
        super(name, room, StuffType.FRIDGE);
        this.state = new RestingState(this);
    }
    
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        addEventToReport(name + " temperature set to " + temperature + "°C");
        notifyObserver();
    }
    
    public boolean isEmpty() {
        return foodItems <= 0;
    }
    
    public void consumeFood() {
        if (foodItems > 0) {
            foodItems--;
            addEventToReport("Food item consumed from " + name + ", remaining: " + foodItems);
            notifyObserver();
        } else {
            addEventToReport(name + " is empty!");
            notifyObserver();
        }
    }
    
    public void restockFood(int amount) {
        foodItems += amount;
        addEventToReport(name + " restocked with " + amount + " food items, total: " + foodItems);
        notifyObserver();
    }
    
    public int getFoodItems() {
        return foodItems;
    }
    
    public int getTemperature() {
        return temperature;
    }
}