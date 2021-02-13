// Pins
// b is the least significant digit. 
const int W_PIN_b = 10;
const int X_PIN_b = 9;
const int Y_PIN_b = 8;
const int Z_PIN_b = 7;

const int LE_PIN = 6; // This pin deals with CD4511 LE. 

// a is the most significant digit. 
const int W_PIN_a = 5;
const int X_PIN_a = 4;
const int Y_PIN_a = 3;
const int Z_PIN_a = 2;

// Values
int binaryOutput_a[] = {0,0,0,0};
int binaryOutput_b[] = {0,0,0,0};
int sensorValue = 0;
int outputValue = 0;

void setup(){
  // Pin setup.
  pinMode(W_PIN_b, OUTPUT);
  pinMode(X_PIN_b, OUTPUT);
  pinMode(Y_PIN_b, OUTPUT);
  pinMode(Z_PIN_b, OUTPUT);
  pinMode(A0, INPUT);
  pinMode(W_PIN_a, OUTPUT);
  pinMode(X_PIN_a, OUTPUT);
  pinMode(Y_PIN_a, OUTPUT);
  pinMode(Z_PIN_a, OUTPUT);
  
  Serial.begin(9600);
}

void loop(){
  // Read the analog in value.
  sensorValue = analogRead(A0);
  
  // Map the input to a value from 0 to 99.
  outputValue = map(sensorValue, 0, 1023, 0, 99);
  
  // Separate the two digits.
  int b = outputValue % 10;
  int a = outputValue / 10;
  
  // Get the binary representation
  for(int i = 3; i >= 0; i--){ // For digit a
    // Shift outputValue by i bits and bitwise & with 0001 to get each bit.
  	int bit = (a >> i) & 1;
    binaryOutput_a[3-i] = bit;
  }
   for(int i = 3; i >= 0; i--){ // For digit b
    // Shift outputValue by i bits and bitwise & with 0001 to get each bit.
  	int bit = (b >> i) & 1;
    binaryOutput_b[3-i] = bit;
  }
  
  // Set output pins
  digitalWrite(W_PIN_a, binaryOutput_a[0]);
  digitalWrite(X_PIN_a, binaryOutput_a[1]);
  digitalWrite(Y_PIN_a, binaryOutput_a[2]);
  digitalWrite(Z_PIN_a, binaryOutput_a[3]);
  digitalWrite(W_PIN_b, binaryOutput_b[0]);
  digitalWrite(X_PIN_b, binaryOutput_b[1]);
  digitalWrite(Y_PIN_b, binaryOutput_b[2]);
  digitalWrite(Z_PIN_b, binaryOutput_b[3]);
  
  strobe();
  
  //serialMonitorDebug();
}

// Performs the strobe on LE_PIN.
void strobe(){
  delay(10);
  digitalWrite(LE_PIN, LOW);
  delay(10);
  digitalWrite(LE_PIN, HIGH);
}

// Prints debug data to the serial monitor.
void serialMonitorDebug(){
  Serial.print("sensor = ");
  Serial.print(sensorValue);
  Serial.print("\t output = ");
  Serial.print(outputValue);
  Serial.print("\t binaryOutput_a = ");
  for(int i = 0; i <= 3; i++){
  	Serial.print(binaryOutput_a[i]);
  }
  Serial.print("\t binaryOutput_b = ");
  for(int i = 0; i <= 3; i++){
  	Serial.print(binaryOutput_b[i]);
  }
  Serial.println();
}