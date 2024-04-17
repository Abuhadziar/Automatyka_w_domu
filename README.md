![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/60b0afea-7262-4e28-adaa-adaf36c1af5a)

Automatyka w domu is a simple application designed to establish connection and exchange data with smart devices using the Bluetooth Low Energy (BLE) protocol. 
The app is written in Kotlin using the Jetpack Compose toolkit. The implementation was done according to the Model-View-ViewModel architectural pattern.
# Key functionalities
* Scanning for nearby BLE devices
* Establishing connection with selected device
* Defining type of the device (user has 4 options: Smart Band, Smart TV, Smart Light and Toothbrush)
* After establishing connection with Smart Bands and Toothbrushes user can read devices battery level, date and time displayed on the device, as well as rewrite this date and time
* After stablishing connection with Smart Light Bulb user can change brightness and light color
* Unfortunately, no functionalities related to Smart TVs were implemented due to a lack of necessary documentation
# Known bugs
* User may try to send an empty array of data to Smart Band or Toothbrush, after which the app may crash
# User interface
## Start Screen
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/aa8fbb0b-19bb-430b-a99d-db73a31b2346)

