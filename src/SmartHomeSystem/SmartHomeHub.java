package SmartHomeSystem;

import SmartHomeSystem.Exceptions.InvalidTriggerException;
import SmartHomeSystem.Exceptions.UnsupportedActionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code SmartHomeHub} class represents a central hub for controlling smart home devices,
 * scheduling tasks, and managing triggers.
 */

public class SmartHomeHub {

    private static SmartHomeHub currentInstance;
    private final List<Device> devices = new ArrayList<>();
    private final List<Schedule> schedules = new ArrayList<>();
    private final List<Trigger> triggers = new ArrayList<>();

    private final List<DeviceObserver>observers = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(SmartHomeHub.class.getName());

    public SmartHomeHub() {
        currentInstance = this;
    }

    public static SmartHomeHub getInstance() {
        return currentInstance;
    }

    // add new device
    public void addDevice(Device device) {
        devices.add(device);
        observers.add(device);
    }

    // remove device
    public void removeDevice(Device device) {
        devices.remove(device);
        observers.remove(device);
    }

    private void notifyObservers(String message) {
        for (DeviceObserver observer : observers) {
            observer.update(message);
        }
    }

    // turn on device
    public void turnOn(int id) throws UnsupportedActionException {
        boolean deviceFound = false;
        for (Device device : devices) {
            if (device instanceof Light && ((Light) device).getId() == id) {
                device.turnOn(id);
                deviceFound = true;
                notifyObservers("Light " + id + " is on.");
            } else if (device instanceof Thermostat && ((Thermostat) device).getId() == id) {
                device.turnOn(id);
                deviceFound = true;
                notifyObservers("Thermostat " + id + " is on.");
            } else if (device instanceof Door && ((Door) device).getId() == id) {
                device.turnOn(id);
                deviceFound = true;
                notifyObservers("Door " + id + " is on.");
            }
        }
        if (!deviceFound) {
            logger.log(Level.WARNING,"Device not found with id - " + id);
            throw new UnsupportedActionException("Device not found with id - " + id);
        }
    }

    // turn off device
    public void turnOff(int id) throws UnsupportedActionException {
        boolean deviceFound = false;
        for (Device device : devices) {
            if (device instanceof Light && ((Light) device).getId() == id) {
                device.turnOff(id);
                deviceFound = true;
                notifyObservers("Light " + id + " is off.");
            } else if (device instanceof Thermostat && ((Thermostat) device).getId() == id) {
                device.turnOff(id);
                deviceFound = true;
                notifyObservers("Thermostat " + id + " is off.");
            } else if (device instanceof Door && ((Door) device).getId() == id) {
                device.turnOff(id);
                deviceFound = true;
                notifyObservers("Door " + id + " is off.");
            }
        }
        if (!deviceFound) {
            logger.log(Level.WARNING,"Device not found with id - " + id);
            throw new UnsupportedActionException("Device not found with id - " + id);
        }
    }

    // set a timer schedule for device
    public void setSchedule(int deviceId, String time, String action) throws UnsupportedActionException {
        Device device = findDeviceById(deviceId);
        if (device != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date scheduledTime = format.parse(time);
                Date date = new Date();
                String s = format.format(date);
                Date currTime = format.parse(s);

                long delay = scheduledTime.getTime() - currTime.getTime();

                System.out.println("Scheduled Task - [device: " + device.deviceType() + ", time: " + time + ", command: " + action + "]");
                if (delay >= 0) {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Schedule schedule = new Schedule(device, time, action, deviceId);
                            schedules.add(schedule);
                            try {
                                schedule.execute();
                            } catch (UnsupportedActionException e) {
                                logger.log(Level.WARNING,e.getMessage());
                                throw new RuntimeException(e);
                            }
                            try {
                                System.out.println("Status Report - " + getStatusReport());
                            } catch (UnsupportedActionException e) {
                                logger.log(Level.WARNING,e.getMessage());
                                throw new RuntimeException("Cant get status report");
                            }
                            timer.cancel();
                        }
                    }, delay);

                } else {
                    logger.log(Level.WARNING,"Invalid time");
                    throw new UnsupportedActionException("Invalid time");
                }

            } catch (ParseException e) {
                logger.log(Level.WARNING,e.getMessage());
                throw new UnsupportedActionException("Invalid time format.");
            }
        } else {
            logger.log(Level.WARNING,"Device with ID " + deviceId + " not found.");
            throw new UnsupportedActionException("Device with ID " + deviceId + " not found.");
        }
    }

    private Device findDeviceById(int id) {
        for (Device device : devices) {
            if (device instanceof Light && ((Light) device).getId() == id) {
                return device;
            } else if (device instanceof Thermostat && ((Thermostat) device).getId() == id) {
                return device;
            } else if (device instanceof Door && ((Door) device).getId() == id) {
                return device;
            }
        }
        return null;
    }

    // add trigger for device
    public void addTrigger(String condition, String action) throws InvalidTriggerException, UnsupportedActionException {
        String[] arr = action.split("[()]");
        if (arr.length >= 2) {
            String actionType = arr[0];
            int id = Integer.parseInt(arr[1]);
            Device device = findDeviceById(id);
            if (device != null) {
                triggers.add(new Trigger(condition, actionType, id));
            } else {
                throw new UnsupportedActionException("Device with ID " + id + " not found.");
            }
        } else {
            throw new InvalidTriggerException("Invalid Trigger format");
        }
    }

    // execute the schedule for device
    public void executeSchedules() throws UnsupportedActionException {
        for (Schedule schedule : schedules) {
            schedule.execute();
        }
    }

    // checks and executes trigger
    public void checkTriggers() throws UnsupportedActionException {
        for (Device device : devices) {
            for (Trigger trigger : triggers) {
                if (trigger.isTriggered(device)) {
                    String action = trigger.getAction();
                    int id = trigger.getId();
                    System.out.println("Trigger - [condition: " + trigger.getCondition() + ", action: " + action + "(" + id + ")]");
                    executeAction(action, id);
                }
            }
        }
    }

    // execute action on device
    private void executeAction(String action, int id) throws UnsupportedActionException {
        if (action.equals("turnOff")) {
            turnOff(id);
        } else if (action.equals("turnOn")) {
            turnOn(id);
        } else {
            throw new UnsupportedActionException("Unsupported Action - " + action);
        }
    }

    // prints the status report of all devices
    public String getStatusReport() throws UnsupportedActionException {
        StringBuilder sb = new StringBuilder();
        for (Device device : devices) {
            if (device instanceof Light) {
                sb.append(device.deviceType()).append(" ").append(((Light) device).getId()).append(" is ").append(((Light) device).getStatus()).append(".");
            } else if (device instanceof Thermostat) {
                sb.append(device.deviceType()).append(" ").append(((Thermostat) device).getId()).append(" is set to ").append(((Thermostat) device).getTemperature()).append(" degrees.");
            } else if (device instanceof Door) {
                sb.append(device.deviceType()).append(" ").append(((Door) device).getId()).append(" is ").append(((Door) device).getStatus()).append(".");
            }
            else{
                throw new UnsupportedActionException("Device - "+device.deviceType() + " report doesn't exist");
            }
        }
        return sb.toString();
    }
}
