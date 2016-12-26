int led =13;
void setup() {
   pinMode(led,OUTPUT);  // set pin 13 for output
   
}

void loop() {
    digitalWrite(led,HIGH); // turn on the LED
    delay(1000); // delay for 1000 milliseconds
    digitalWrite(led,LOW);  // turn off the LED
    delay(1000);
}
