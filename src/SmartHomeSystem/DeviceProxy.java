package SmartHomeSystem;

import SmartHomeSystem.Exceptions.UnauthorizedAccess;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceProxy {
    private final Device realDevice;
    private final String username;
    private static final Logger logger = Logger.getLogger(DeviceProxy.class.getName());

    public DeviceProxy(Device realDevice, String username) {
        this.realDevice = realDevice;
        this.username = username;
    }

    public void turnOn() throws UnauthorizedAccess {
        if(authorizeUser()) {
            realDevice.turnOn(realDevice.getId());
        }
        else {
            logger.log(Level.WARNING,"User not authenticated");
            throw new UnauthorizedAccess("User not authenticated");
        }
    }

    public void turnOff() throws UnauthorizedAccess {
        if(authorizeUser()) {
            realDevice.turnOff(realDevice.getId());
        }
        else {
            logger.log(Level.WARNING,"User not authenticated");
            throw new UnauthorizedAccess("User not authenticated");
        }
    }

    public boolean authorizeUser(){
        return "admin".equals(username);
    }
}
