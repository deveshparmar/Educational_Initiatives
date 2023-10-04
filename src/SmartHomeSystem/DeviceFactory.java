package SmartHomeSystem;

public class DeviceFactory {
    public static Device buildDevice(int id,String type,String status){
        return switch (type.toLowerCase()) {
            case "light" -> new Light(id, type, status);
            case "thermostat" -> new Thermostat(id, type, Integer.parseInt(status));
            case "door" -> new Door(id, type, status);
            default -> throw new IllegalArgumentException("Unsupported device type - " + type);
        };
    }
}
