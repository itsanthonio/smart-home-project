# Smart Home

The main goal of this project was to create a simple simulation of a house, in the implementation of which various design patterns were used.

## UseCase diagram
Detail use-case diagram in added folder
## Implementation

The entities there are a house,rooms, various devices and a person.

Appliances have their consumption in active state, idle state, switched off state. Collecting electricity consumption data about devices.

People can perform activities (actions). Individual devices and people are present in the same room at any moment and randomly generate events. Events are accepted and handled by the appropriate person(s). When the device is broken, the resident of the house must examine the documentation for the device - go through the repair manual and take corrective action.



## Design patterns


- 	Factory
-   Observer
-   Singleton
-   Strategy
- 	State    
   


## Types of devices

- class AirConditioner
- class AudioSystem
- class Computer
- class Fridge
- class Lights
- class Thermostats
- class SecuritySystem
- class SmartVacuum
- class TV
- class Doors
