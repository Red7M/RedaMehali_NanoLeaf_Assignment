# RedaMehali_NanoLeaf_Assignment
Single view displays list of devices where each device has 2 basic controls: an on/off switch, and a brightness slider. 

Functionality: 
Global switch shows On- if at least one of the devices are on. In the other hand, Off- if all devices are off. Also, toggling on/off should turn all devices on/off

The average of all device sliders (changing the brightness of one device changes the global slider to account for the change in average)
Sliding the global slider changes all device brightness to match the global value. 

Structure:- Project is written fully in kotlin using an MVVM architecture where: 
- View: MyDevicesActivityScreen and MyDevicesAdapter class
- ViewModel: DevicesViewModel class
- Model: CommandCenterRepository class

- View: MyDevicesActivityScreen class Contains all devices in a recyclerview list. User can toggle global switch, global average brightness, or individual device switches and brightness. Also, MyDevicesAdapter class is responsible for creating single view items, and binding each single item (device) to the view. Class also contains various adapter related methods to set, get, add, edit data within adapters' list.

- Model: CommandCenterRepository is a command center class that handle all of devices requests. It prints the updated device state to the console through Log.info() messages. For example, turning the device off will  print “Device 12345- Turned off”

The Data: MockData class
Contains a string sequence, homelights String array, and different colors. 

Helper class: DevicesHelper provides parsing method, and an ascending order helper method. 
