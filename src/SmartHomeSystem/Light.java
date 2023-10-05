package SmartHomeSystem;

import java.util.logging.Logger;

/**
 * The {@code Light} class represents a smart home light device.
 * It implements the {@link Device} interface and provides methods to control and query the light's status.
 */

public class Light implements Device {

    /*
     * Light Class Properties
     * 1) id
     * 2) status
     * 3) type
     */

    private int id;
    private String status;
    private String type;

    private static final Logger logger = Logger.getLogger(Light.class.getName());

    public Light(int id, String type, String status) {
        this.id = id;
        this.type = type;
        this.status = status;
    }

    @Override
    public void turnOn(int id) {
        if (this.id == id && status.equals("on")) {
            status = "on";
            System.out.println("Light " + id + " is already on.");
        }
        else{
            status = "on";
            System.out.println("Light " + id + " is on.");
        }
    }

    @Override
    public void turnOff(int id) {
        if (this.id == id && status.equals("off")) {
            status = "off";
            System.out.println("Light " + id + " is already off.");
        }else{
            status = "off";
            System.out.println("Light " + id + " is off.");
        }
    }

    @Override
    public String deviceType() {
        return "Light";
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    @Override
    public void update(String message) {
        logger.info("[Received update for Light " + getId() + ": " + message + "]");
    }
}
