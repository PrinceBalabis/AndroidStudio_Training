BluetoothLeGattAndroidMarshmallow
Optimized Bluetooth Le Gatt Android Sample Project for Marshmallow+
The difference for Bluetooth between Marshmallow and previous
Android versions is that Marshmallow requires Location permission(on top of the regular Bluetooth permissions)
to access Bluetooth. This project also implements "Runtime Permission check", which shows
a pop-up on boot and gives the option of the user to "allow" the permission.

BasicAndroidSendToBluetoothLE
Should work on Marshmallow 6.0+
Basic sample to send data from Android to Bluetooth LE peripheral.
Great for testing for instance Arduino-connected Bluetooth(UART)-modules like the HM-10