<p align="center">
  <img src="https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/784ebf8c-be53-4605-aa5b-4d18b61a3296">
</p>

> [!NOTE]
>Automatyka w domu is a simple application designed to establish connection and exchange data with smart devices using the Bluetooth Low Energy (BLE) protocol. 
>The app is written in Kotlin using the Jetpack Compose toolkit. The implementation was done according to the Model-View-ViewModel architectural pattern.

>[!IMPORTANT]
> # Key functionalities
> * Scanning for nearby BLE devices
> * Establishing connection with selected device
> * Defining type of the device (user has 4 options: Smart Band, Smart TV, Smart Light and Toothbrush)
> * After establishing connection with Smart Bands and Toothbrushes user can read devices battery level, date and time displayed on the device, as well as rewrite this date and time
> * After establishing connection with Smart Light Bulb user can turn the light bulb on and off as well as change the light color
> * Unfortunately, no functionalities related to Smart TVs were implemented due to a lack of necessary documentation

>[!WARNING]
> # Known bugs
> * User may try to send an empty array of data to Smart Band or Toothbrush, after which the app may crash

# User interface
## Color Scheme
UI was implemented in two variants of color scheme: light mode and dark mode. Appropriate variant is used based on users phone settings
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/81d7d4d6-1790-433e-a3e6-4738bd8a846f)
## Start Screen
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/6ba5864b-74c9-417c-b51f-9ad54260d00c)
## Main Screen (displaying list of connected devices)
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/66d4b3d8-197c-4fcc-aa75-3a9abb04780c)
## Scan Screen
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/1dfed131-a3f2-466b-9b00-0790d4f83494)
## Selecting device type
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/8e367dd5-5b4a-436f-8952-6c0251b6ca94)
## Retreiving data from Smart Band
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/efd0afd5-f4d9-4910-9482-84fa6ec84d23)
## Retreiving data from Toothbrush
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/32304d82-74fe-452f-93ea-ffe9c8122293)
## Choosing date and time that will be send to smart device
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/aafd2b13-f919-40c2-912b-efa618fa4a9f)
## Smart Light Screen
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/0f788460-8466-4b07-bfff-e964ecafa416)
## Changing light color
![image](https://github.com/Abuhadziar/Automatyka_w_domu/assets/130935744/dd6e16a7-3388-4de2-aeca-2ec782b446cb)

> [!CAUTION]
> This project is part of my engineering thesis and cannot be used for commercial purposes or copied for use in other scientific works.










