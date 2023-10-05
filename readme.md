# Smart Home System
**The Smart Home System is a Java-based application that simulates a smart home system, allowing us to control various smart devices such as lights, thermostats, and door locks via a central hub. Users can set schedules, automate tasks, view device status & reports, and manage triggers.**

## Built with
Java (JDK-20)

## Getting started
- Clone the repository
  ```shell
   git clone https://github.com/deveshparmar/Educational_Initiatives.git
  ```
- Compile the project
  ```shell
  javac src/SmartHomeSystem/*.java src/SmartHomeSystemExceptions/*.java
  ```
- Run the Main.java (Entry Point)
  ```shell
  java -cp src Main
  ```
## Project Structure
- `src/` - Source code directory
  - `SmartHomeSystem/` - Main package for the application
    - `Device.java` - Interface representing smart home devices
    - `DeviceFactory.java` - Factory class for creating devices
    - `DeviceObserver.java` - Interface for observing device updates
    - `DeviceProxy.java` - Proxy class for device access
    - `Light.java` - Class representing light devices
    - `Thermostat.java` - Class representing thermostat devices
    - `Door.java` - Class representing door devices
    - `SmartHomeHub.java` - Main smart home hub class
    - `Trigger.java` - Class for defining automation triggers
    - `Schedule.java` - Class for defining schedule for devices
    - `Main.java` - Application entry point
  - `SmartHomeSystemExceptions/` - Package for custom exceptions
    - `UnsupportedActionException.java` - Custom exception for unsupported actions
    - `InvalidTriggerException.java` - Custom exception for invalid triggers
    - `UnauthorizedAccess` - Custom exception for unauthorized access of device
  
## Features
- Initialize the Smart Home System with different devices, each having a unique ID and type (light, thermostat, door lock).
- Turn devices on/off.
- Schedule devices to turn on/off at a particular time.
- Automate tasks based on triggers.
- Add and remove devices dynamically.
- Custom exceptions for handling unsupported actions and invalid trigger conditions.
- Logging for each component activity

## UML diagram
![image](https://raw.githubusercontent.com/deveshparmar/deveshparmar/main/uml.png)

## Output Screenshots
1) Output
![image](https://raw.githubusercontent.com/deveshparmar/deveshparmar/main/Screenshot%202023-10-04%20191134.png)

2) Logging for error and Custom Exception
![image](https://raw.githubusercontent.com/deveshparmar/deveshparmar/main/Screenshot%202023-10-05%20112409.png)


