char buffer;
int led = 13;

void setup(){
    Serial.begin(9600);  // set rate to 9600bps for serial data communication
    Serial.print("Start:");  // display the output
    pinMode(led, OUTPUT);  // set pin 13 for output
}

void loop(){
    while(Serial.available()) {     // check if there is data in serial port
      delay(10);  // delay for 10 milliseconds
      buffer = Serial.read();   // read input data into buffer
      if (buffer == 'H') {
         digitalWrite(led, HIGH);   // light up the LED
         Serial.print(buffer);
        } else if (buffer == 'L') {
                 digitalWrite(led, LOW);  // turn off the LED
                 Serial.print(buffer);
        }
    }
}
