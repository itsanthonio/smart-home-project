package appliances.state;

import appliances.UsableObject;

/**
 * Interface representing the state of a device.
 * Implements the State pattern.
 */
public interface DeviceState {
    void handle(UsableObject device);
    String getStateName();
}