
void setup() {
    Serial.begin(9600);  // set rate to 9600bps for serial data communication
}

void loop() {
    Serial.print('H');  // display output and send data to XBee module
    delay(1000); // delay for 1000 milliseconds
    Serial.print('L');
    delay(1000);
}
