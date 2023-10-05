package SmartHomeSystem;

public class DeviceProxy {
    private Device realDevice;
    private String username;

    public DeviceProxy(Device realDevice, String username) {
        this.realDevice = realDevice;
        this.username = username;
    }

    public void turnOn(){
        if(authorizeUser())
            realDevice.turnOn(realDevice.getId());
        else
            System.out.println("unauth access");
    }

    public void turnOff(){
        if(authorizeUser())
            realDevice.turnOff(realDevice.getId());
        else
            System.out.println("unauth access");
    }

    public boolean authorizeUser(){
        return "admin".equals(username);
    }
}
