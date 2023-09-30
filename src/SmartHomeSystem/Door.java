package SmartHomeSystem;

/**
 * The {@code Door} class represents a smart home Door device.
 * It implements the {@link Device} interface and provides methods to control and query the door's status.
 */

public class Door implements Device {

    /*
     * Door class Properties
     * 1) id
     * 2) type
     * 3) status
     */

    private int id;
    private String type;
    private String status;

    public Door(int id, String type, String status) {
        this.id = id;
        this.type = type;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void turnOn(int id) {
        if (this.id == id) {
            System.out.println("Door " + id + " is On.");
        }
    }

    @Override
    public void turnOff(int id) {
        if (this.id == id) {
            System.out.println("Door " + id + " is Off.");
        }
    }

    @Override
    public String DeviceType() {
        return "Door";
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }
}
