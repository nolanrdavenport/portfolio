// button state variables
int p1b1 = 1;
int p1b2 = 1;
int p2b1 = 1;
int p2b2 = 1;
void setup() {
  // rows
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  // columns
  pinMode(23, OUTPUT);
  pinMode(24, OUTPUT);
  pinMode(25, OUTPUT);
  pinMode(26, OUTPUT);
  pinMode(27, OUTPUT);
  pinMode(28, OUTPUT);
  pinMode(29, OUTPUT);
  pinMode(30, OUTPUT);
  pinMode(31, OUTPUT);
  pinMode(32, OUTPUT);
  pinMode(33, OUTPUT);
  //buttons
  pinMode(37, INPUT_PULLUP);  //p1b1
  pinMode(38, INPUT_PULLUP);  //p1b2
  pinMode(39, INPUT_PULLUP);  //p2b1
  pinMode(40, INPUT_PULLUP);  //p2b2
}

// Display handling variables
int pandb = 0; // [0, 2]

// Game handling variables
// Dimensions of the game
int width = 110;
int height = 90;

// Ball location
float ballx = 50;
float bally = 40;

// Paddle locations
float p1x = 0;
float p1y = 30;
float p2x = 100;
float p2y = 30;

// Ball velocity
float ballxvel = 0.3;
float ballyvel = 0.0;

// Ball location / 10 (for use in actually displaying the ball on the display)
int ballScreenLocationX = 0;
int ballScreenLocationY = 0;

void loop() {
    // Game Loop
    // Read the state of the buttons
    p1b1 = digitalRead(37);
    p1b2 = digitalRead(38);
    p2b1 = digitalRead(39);
    p2b2 = digitalRead(40);


    // Move the paddles based on state of the buttons
    if(p1b1 == LOW && p1y >= 10){
      p1y -= 0.4;
    }
    if(p1b2 == LOW && p1y <= 69.5){
      p1y += 0.4;
    }

    if(p2b2 == LOW && p2y >= 10){
      p2y -= 0.4;
    }
    if(p2b1 == LOW && p2y <= 69.5){
      p2y += 0.4;
    }

    
    // Move the ball based on its x and y velocities
    ballx += ballxvel;
    bally += ballyvel;

    // Calculate ball bouncing or player losing
    if(ballx - 4 > p2x || ballx + 4 < p1x){
      ballx = 50;
      bally = 40;
      ballyvel = 0;
      loseSequence();
    }
    if(ballx > p2x && bally > p2y - 10 && bally < p2y + 20){
        ballxvel = -ballxvel;
        ballxvel -= 0.005;
        int diff = p2y - bally;
        if(diff >= 0 && diff < 5){
          ballyvel = -0.1;
        }else if(diff >= 5 && diff < 10){
          ballyvel = -0.05;
        }else if(diff >= 10 && diff < 15){
          ballyvel = -0.01;
        }else if(diff >= 15 && diff <= 16){
          ballyvel = 0;
        }else if(diff > 16 && diff <= 20){
          ballyvel = 0.01;
        }else if(diff > 20 && diff <= 25){
          ballyvel = 0.05;
        }else if(diff > 25 && diff <= 30){
          ballyvel = 0.1;
        }
    }
    if(ballx < p1x+10 && bally > p1y - 10 && bally < p1y + 20){
        ballxvel = -ballxvel;
        ballxvel += 0.005;
        int diff = p1y - bally;
        if(diff >= 0 && diff < 5){
          ballyvel = -0.1;
        }else if(diff >= 5 && diff < 10){
          ballyvel = -0.05;
        }else if(diff >= 10 && diff < 15){
          ballyvel = -0.01;
        }else if(diff >= 15 && diff <= 16){
          ballyvel = 0;  
        }else if(diff > 16 && diff <= 20){
          ballyvel = 0.01;
        }else if(diff > 20 && diff <= 25){
          ballyvel = 0.05;
        }else if(diff > 25 && diff <= 30){
          ballyvel = 0.1;
        }
    }
    if(bally >= height){
        ballyvel = -ballyvel;
    }
    if(bally <= 0 + 10){
        ballyvel = -ballyvel;
    }

    // Calculate ball location / 10
    ballScreenLocationX = ballx / 10;
    ballScreenLocationY = bally / 10;

    // Code for displaying everything onto our 11x9 display
    // First clear the screen to start off render with a clean slate
    clearScreen();
    // Display one of the three elements at a time because there would be issues with displaying all of them at the same time. 
    if(pandb == 0){
      // p1 paddle
      digitalWrite((p1y/10)+2, HIGH);
      digitalWrite((p1y/10)+3, HIGH);
      digitalWrite((p1y/10)+4, HIGH);
      digitalWrite(23, HIGH);
      pandb = 1;
    }else if(pandb == 1){ 
      // p2 paddle
      digitalWrite((p2y/10)+2, HIGH);
      digitalWrite((p2y/10)+3, HIGH);
      digitalWrite((p2y/10)+4, HIGH);
      digitalWrite(33, HIGH);
      pandb = 2;
    }else{
      // ball
      if(ballScreenLocationX + 23 <= 33 && ballScreenLocationX + 23 >= 23){
        digitalWrite(ballScreenLocationX + 23, HIGH);
      }
      if(ballScreenLocationY + 2 <= 10 && ballScreenLocationY + 2 >= 2){
        digitalWrite(ballScreenLocationY + 2, HIGH);
      }
      pandb = 0;
    }

    // Create a total framerate of 180hz with an effective frame rate of 60hz due to each of the three elements rendering once every third frame. 
    delay(1000/180);
}

// Sets all LEDs to LOW
void clearScreen(){
    for(int r = 2; r < 11; r++){
        for(int c = 23; c < 34; c++){
            digitalWrite(r, LOW);
            digitalWrite(c, LOW);
        }
    }
}

// Sequence that is played when one of the players loses
void loseSequence(){
    // Make the ball go the other direction
    ballxvel = -ballxvel;

    // Make all LEDs go HIGH
    for(int r = 2; r < 11; r++){
        for(int c = 23; c < 34; c++){
            digitalWrite(r, HIGH);
            digitalWrite(c, HIGH);
        }
    }
    // Wait 200 ms
    delay(200);
    // Make all LEDs go LOW
    clearScreen();
    // Wait 200 ms
    delay(200);
    // Make all LEDs go HIGH
    for(int r = 2; r < 11; r++){
        for(int c = 23; c < 34; c++){
            digitalWrite(r, HIGH);
            digitalWrite(c, HIGH);
        }
    }
    // Wait 200 ms
    delay(200);
    // Make all LEDs go LOW
    clearScreen();
    // Set the balls location to the center
    digitalWrite(ballScreenLocationX + 23, HIGH);
    digitalWrite(ballScreenLocationY + 2, HIGH);
    // Wait 200 ms
    delay(200);
}
