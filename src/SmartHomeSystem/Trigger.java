package SmartHomeSystem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code Trigger} class represents a trigger condition and action for automating tasks in a smart home system.
 */

public class Trigger {
    private String condition;
    private String action;
    private int id;
    private static Logger logger = Logger.getLogger(Trigger.class.getName());

    public Trigger(String condition, String action, int id) {
        this.condition = condition;
        this.action = action;
        this.id = id;
    }

    // check if trigger condition is met or not
    public boolean isTriggered(Device device) {
        if (!condition.isEmpty()) {
            String[] arr = condition.split(" ");
            if (arr.length == 3) {
                String property = arr[0];
                String operator = arr[1];
                int value = Integer.parseInt(arr[2]);

                if (device instanceof Thermostat && "temperature".equals(property)) {
                    int deviceTemperature = ((Thermostat) device).getTemperature();

                    switch (operator) {
                        case ">" -> {
                            return deviceTemperature > value;
                        }
                        case "<" -> {
                            return deviceTemperature < value;
                        }
                        case ">=" -> {
                            return deviceTemperature >= value;
                        }
                        case "<=" -> {
                            return deviceTemperature <= value;
                        }
                        case "==" -> {
                            return deviceTemperature == value;
                        }
                        default -> {
                            logger.log(Level.WARNING,"Unsupported operator: " + operator);
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String getAction() {
        return action;
    }

    public String getCondition() {
        return condition;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{condition: " + condition + ", action: " + action + "}";
    }
}
