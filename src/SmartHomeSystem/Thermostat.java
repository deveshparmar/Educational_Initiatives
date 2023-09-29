package SmartHomeSystem;

/**
 * The {@code Thermostat} class represents a smart home thermostat device.
 * It implements the {@link Device} interface and provides methods to control and query the thermostat's status.
 */

public class Thermostat implements Device {

    /*
     * Thermostat class Properties
     * 1) id
     * 2) temperature
     * 3) type
     */

    private int id;
    private int temperature;
    private String type;

    public Thermostat(int id, String type, int temperature) {
        this.id = id;
        this.type = type;
        this.temperature = temperature;
    }

    @Override
    public void turnOn(int id) {
        if (this.id == id) {
            System.out.println("Thermostat " + id + " is On.");
        }
    }

    @Override
    public void turnOff(int id) {
        if (this.id == id) {
            System.out.println("Thermostat " + id + " is Off.");
        }
    }

    @Override
    public String getDeviceType() {
        return "Thermostat";
    }

    public int getTemperature() {
        return temperature;
    }

    public int getId() {
        return id;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
