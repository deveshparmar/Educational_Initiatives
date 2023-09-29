package SmartHomeSystem;

/**
 * The {@code Device} interface represents a smart home device that can be controlled.
 * Implementing classes should provide methods to turn the device on, turn it off,
 * and get the device's type.
 */

public interface Device {
    void turnOn(int id);
    void turnOff(int id);
    String getDeviceType();
}
