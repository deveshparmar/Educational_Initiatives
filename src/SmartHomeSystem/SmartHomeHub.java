package SmartHomeSystem;

import SmartHomeSystem.Exceptions.InvalidTriggerException;
import SmartHomeSystem.Exceptions.UnsupportedActionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The {@code SmartHomeHub} class represents a central hub for controlling smart home devices,
 * scheduling tasks, and managing triggers.
 */

public class SmartHomeHub {

    private static SmartHomeHub currentInstance;
    private List<Device> devices = new ArrayList<>();
    private List<Schedule> schedules = new ArrayList<>();
    private List<Trigger> triggers = new ArrayList<>();

    public SmartHomeHub() {
        currentInstance = this;
    }

    public static SmartHomeHub getInstance() {
        return currentInstance;
    }

    // add new device
    public void addDevice(Device device) {
        devices.add(device);
    }

    // remove device
    public void removeDevice(Device device) {
        devices.remove(device);
    }

    // turn on device
    public void turnOn(int id) throws UnsupportedActionException {
        boolean deviceFound = false;
        for (Device device : devices) {
            if (device instanceof Light && ((Light) device).getId() == id) {
                device.turnOn(id);
                deviceFound = true;
            } else if (device instanceof Thermostat && ((Thermostat) device).getId() == id) {
                device.turnOn(id);
                deviceFound=true;
            } else if (device instanceof Door && ((Door) device).getId() == id) {
                device.turnOn(id);
                deviceFound=true;
            }
        }
        if(!deviceFound){
            throw new UnsupportedActionException("Device not found with id - " + id );
        }
    }

    // turn off device
    public void turnOff(int id) throws UnsupportedActionException {
        boolean deviceFound = false;
        for (Device device : devices) {
            if (device instanceof Light && ((Light) device).getId() == id) {
                device.turnOff(id);
                deviceFound=true;
            } else if (device instanceof Thermostat && ((Thermostat) device).getId() == id) {
                device.turnOff(id);
                deviceFound=true;
            } else if (device instanceof Door && ((Door) device).getId() == id) {
                device.turnOff(id);
                deviceFound=true;
            }
        }
        if(!deviceFound){
            throw new UnsupportedActionException("Device not found with id - " + id );
        }
    }

    // set a schedule for device
    public void setSchedule(int deviceId, String time, String action) throws UnsupportedActionException {
        Device device = findDeviceById(deviceId);
        if(device!=null) {
            Schedule schedule = new Schedule(device, time, action, deviceId);
            schedules.add(schedule);
            System.out.println("{device: " + device.getDeviceType() + ", time: " + time + ", command: " + action + "}");
        }else{
            throw new UnsupportedActionException("Device with ID " + deviceId + " not found.");
        }
    }

//    public void setTimerSchedule(int deviceId, String time, String action) throws UnsupportedActionException {
//        Device device = findDeviceById(deviceId);
//        if(device!=null) {
//            Schedule schedule = new Schedule(device, time, action, deviceId);
//            schedules.add(schedule);
//            System.out.println("{device: " + device.getDeviceType() + ", time: " + time + ", command: " + action + "}");
//
//            try{
//                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//                Date scheduledTime = dateFormat.parse(time);
//
//                Calendar calendar = Calendar.getInstance();
//                long currMillis = calendar.getTimeInMillis();
//
//                calendar.setTime(scheduledTime);
//                long schMillis = calendar.getTimeInMillis();
//                long delay = schMillis - currMillis;
//
//                System.out.println("Current time - " + new Date(currMillis)));
//                System.out.println("Scheduled Time - " + dateFormat.format(scheduledTime));
//                System.out.println(delay);
//
//                if(delay>=0){
//                    Timer timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            schedule.execute();
//                        }
//                    },delay);
//                }else{
//                    throw new UnsupportedActionException("The specified time is in the past.");
//                }
//            }catch (ParseException e){
//                throw new UnsupportedActionException("Invalid time format.");
//            }
//        }else{
//            throw new UnsupportedActionException("Device with ID " + deviceId + " not found.");
//        }
//    }

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
                if(device!=null) {
                    triggers.add(new Trigger(condition, actionType, id));
                }else{
                    throw new UnsupportedActionException("Device with ID " + id + " not found.");
                }
            } else {
                throw new InvalidTriggerException("Invalid Trigger format");
            }
    }

    // execute the schedule for device
    public void executeSchedules() {
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
                    System.out.println("{condition: " + trigger.getCondition() + ", action: " + action + "(" + id + ")}");
                    executeAction(action, device.getDeviceType(), id);
                }
            }
        }
    }

    // execute action on device
    private void executeAction(String action, String deviceType, int id) throws UnsupportedActionException{
        if (action.equals("turnOff")) {
            turnOff(id);
        } else if (action.equals("turnOn")) {
            turnOn(id);
        } else {
            throw new UnsupportedActionException("Unsupported Action - "+ action);
        }
    }

    // prints the status report of all devices
    public String getStatusReport() {
        List<String> deviceList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Device device : devices) {
            if (device instanceof Light) {
                sb.append(device.getDeviceType() + " " + ((Light) device).getId() + " is " + ((Light) device).getStatus() + ".");
            } else if (device instanceof Thermostat) {
                sb.append(device.getDeviceType() + " " + ((Thermostat) device).getId() + " is set to " + ((Thermostat) device).getTemperature() + " degrees.");
            } else if (device instanceof Door) {
                sb.append(device.getDeviceType() + " " + ((Door) device).getId() + " is " + ((Door) device).getStatus() + ".");
            }
        }
        return sb.toString();
    }
}
