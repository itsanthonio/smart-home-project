package appliances.observer;

import appliances.UsableObject;

/**
 * Observer interface for device state changes.
 */
public interface DeviceObserver {
    void update(UsableObject device);
}