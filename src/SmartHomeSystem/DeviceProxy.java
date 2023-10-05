package SmartHomeSystem;

/**
 * The {@code DeviceProxy} class acts as a proxy for a Device, allowing controlled access based on user authorization.
 */

import SmartHomeSystem.Exceptions.UnauthorizedAccessException;
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

    public void turnOn() throws UnauthorizedAccessException {
        if(authorizeUser()) {
            realDevice.turnOn(realDevice.getId());
        }
        else {
            logger.log(Level.WARNING,"User not authenticated");
            throw new UnauthorizedAccessException("User not authenticated");
        }
    }

    public void turnOff() throws UnauthorizedAccessException {
        if(authorizeUser()) {
            realDevice.turnOff(realDevice.getId());
        }
        else {
            logger.log(Level.WARNING,"User not authenticated");
            throw new UnauthorizedAccessException("User not authenticated");
        }
    }

    public boolean authorizeUser(){
        return "admin".equals(username);
    }
}
