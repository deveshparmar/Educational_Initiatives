package SmartHomeSystem;

import SmartHomeSystem.Exceptions.InvalidTriggerException;
import SmartHomeSystem.Exceptions.UnsupportedActionException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code Main} class is the entry point for the Smart Home System application.
 */

public class Main {
    private final static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        try {
            // initialize the smart home hub
            SmartHomeHub smartHomeHub = new SmartHomeHub();

            // initialize the devices
            Device light1 = DeviceFactory.buildDevice(1,"light","off");
            Device thermostat1 = DeviceFactory.buildDevice(2,"thermostat","75");
            Device door1 = DeviceFactory.buildDevice(3,"door","locked");
            Device door2 = DeviceFactory.buildDevice(4,"door","locked");


            // add devices to smart home hub
            smartHomeHub.addDevice(light1);
            smartHomeHub.addDevice(thermostat1);
            smartHomeHub.addDevice(door1);
            smartHomeHub.addDevice(door2);


            // turn on device with given id
            smartHomeHub.turnOn(1);


            // set scheduled task
            smartHomeHub.setSchedule(2, "06:00", "Turn On");

//            set scheduled task with real timer (Commented out)
//
//            smartHomeHub.setTimerSchedule(1,"19:11","Turn On");



            // set triggers
            smartHomeHub.addTrigger("temperature > 70", "turnOff(1)");


            // remove device dynamically
            smartHomeHub.removeDevice(door2);


            // execution of schedule and triggers
            smartHomeHub.executeSchedules();
            smartHomeHub.checkTriggers();


            // print status report
            System.out.println("Status Report - " + smartHomeHub.getStatusReport());

        }catch (UnsupportedActionException | InvalidTriggerException e){
            logger.log(Level.WARNING,e.getMessage());
            System.out.println("Error - " + "Some Error occurred");
        }

        /* To implement - Add more devices and functionalities */

    }
}
