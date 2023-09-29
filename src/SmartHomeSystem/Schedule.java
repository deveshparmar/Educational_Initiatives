package SmartHomeSystem;

/**
 * The {@code Schedule} class represents a scheduled task for a smart home device.
 * It specifies a time and action to be executed on a particular device.
 */

public class Schedule {
    private final Device device;
    private final String time;
    private final String action;
    private final int deviceId;

    public Schedule(Device device, String time, String action, int deviceId) {
        this.device = device;
        this.time = time;
        this.action = action;
        this.deviceId = deviceId;
    }

    // Executes the scheduled action on the specified device
    public void execute() {
        if (action.equals("Turn On")) {
            device.turnOn(deviceId);
        } else if (action.equals("Turn Off")) {
            device.turnOff(deviceId);
        }
    }
}
