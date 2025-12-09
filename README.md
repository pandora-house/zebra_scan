# zebra_scan

## source
https://techdocs.zebra.com/datawedge/latest/guide/samples/basicintent1/

### DataWedge app settings

### Associate the profile with this sample app:
a. Tap Associated Apps.\
b. In the top right menu, select New app/activity.\
c. Select com.your_app\
d. Select *.

### Confirm the following settings:
The profile is enabled\
Barcode input is enabled\
Intent output is enabled

### Configure the intent output as follows:
Intent action: com.boss.zebra.ACTION (Must match the value defined in the strings.xml file. This is an implicit intent that is sent by DataWedge. The application must register a broadcast receiver with the given action in order to receive the intent.)\
Intent category: (leave blank)\
Intent delivery: Broadcast intent
