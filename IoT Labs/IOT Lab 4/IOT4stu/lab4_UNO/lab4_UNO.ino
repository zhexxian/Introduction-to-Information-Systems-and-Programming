#include <PinChangeInt.h>
#include <PinChangeIntConfig.h>

#include <eHealth.h>
#include <eHealthDisplay.h>

int cont = 0;

void setup() {
  Serial.begin(9600);
  eHealth.initPulsioximeter();
  eHealth.initPositionSensor();
  //Attach the interruptions for using the pulsioximeter.   
  PCintPort::attachInterrupt(6, readPulsioximeter, RISING);
}

void loop() {

  int bpm = eHealth.getBPM();  // get heart bits per minutes
  int oxySat = eHealth.getOxygenSaturation(); // get oxygen saturation in blood
  int position= eHealth.getBodyPosition();  // get body position
  Serial.print((uint8_t)bpm);  // print heart bits per minute in serial monitor and send value to XBee module
  Serial.print(',');
  Serial.print((uint8_t)oxySat); 
  Serial.print(',');
  Serial.print((uint8_t)position);  // print body position in serial monitor and send value to XBee module
  Serial.println();
  delay(490);
  
}


//Include always this code when using the pulsioximeter sensor
//=========================================================================
void readPulsioximeter(){  

  cont ++;

  if (cont == 50) { //Get only of one 50 measures to reduce the latency
    eHealth.readPulsioximeter();  // read current values of sensors
    cont = 0;
  }
}

