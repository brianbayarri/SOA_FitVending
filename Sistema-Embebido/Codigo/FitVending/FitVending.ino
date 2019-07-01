#include <LiquidCrystal_I2C.h>
#include <Wire.h>
#include <DHT.h>
#include <Servo.h>
#include <String.h>
#define  f4    2865    
#define  a4s   2146
#define  c5    1912    
#define  d5    1706
#define  d5s   1608
#define  f5    1433    
#define  a5s   1073
#define  R     0      


int melody2[] = {  f4,  f4, f4,  a4s,   f5,  d5s,  d5,  c5, a5s, f5, d5s,  d5,  c5, a5s, f5, d5s, d5, d5s,   c5};
int beats2[]  = {  21,  21, 21,  128,  128,   21,  21,  21, 128, 64,  21,  21,  21, 128, 64,  21, 21,  21, 128 };

int MAX_COUNT;
long tempo = 10000;
int pause = 1000;
int rest_count = 50;
int toneM = 0;
int beat = 0;
long duration  = 0;
int INFRARROJO_1 = 2;
int INFRARROJO_2 = 3;
int INFRARROJO_3 = 4;
int INFRARROJO_4 = 5;
int LED = 6;
int BUZZER = 7;
int SENSOR_TEMPERATURA = 8;
int TIRA_LED = 13;

Servo SERVO_1;
Servo SERVO_3;
Servo SERVO_4;

int temperatura;
int lightlevel;
int stock;
char state;
String mensajeStock;
boolean tiraOn = false;

DHT dht (SENSOR_TEMPERATURA, DHT11);
LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup() {
  pinMode(LED, OUTPUT);
  pinMode(TIRA_LED, OUTPUT);
  pinMode(BUZZER, OUTPUT);
  pinMode(INFRARROJO_1, INPUT);
  pinMode(INFRARROJO_2, INPUT);
  pinMode(INFRARROJO_3, INPUT);
  pinMode(INFRARROJO_4, INPUT);
  digitalWrite(LED, LOW);
  digitalWrite(TIRA_LED, LOW);
  SERVO_1.attach(9);
  SERVO_4.attach(10);
  dht.begin();
  Wire.begin();
  lcd.begin(16, 2);
  lcd.clear();
  lcd.backlight();
  Serial.begin(38400);  
  Serial.println("MODULO CONECTADO");
  Serial.print("#");
}

void loop() {
  lcd.setCursor(1,0);
  lcd.print("Elija producto");
  sensarTemperatura();
  sensarLuz();
  if(Serial.available())
  {
    state = Serial.read();
    Serial.flush();
    switch(state)
    {
      case '0':
        mensajeStock = validarStock();
        Serial.print(mensajeStock);
        Serial.print("#");
        break;
      case '1':
        expenderProducto(1);
        sensarTemperatura();
        sensarLuz();
        break;
      case '3':
        expenderProducto(3);
        sensarTemperatura();
        sensarLuz();
        break;
      case '4':
        expenderProducto(4);
        sensarTemperatura();
        sensarLuz();
        break;
      case '5':
        if(!tiraOn)
        {
          digitalWrite(TIRA_LED, HIGH);
          tiraOn = true;
        }
        else
        {
          digitalWrite(TIRA_LED, LOW);
          tiraOn = false;
        }
        break;
      case '6':
        sonarStarWars(); 
        sensarTemperatura();
        sensarLuz(); 
        break;
    }
  } 
}

void sensarTemperatura()
{
    temperatura = dht.readTemperature();
    lcd.setCursor(5,1);
    lcd.print(temperatura);
    lcd.print((char)223);
    lcd.print("C");
}

void sensarLuz()
{
  lightlevel = analogRead(A0);
  if(lightlevel < 5)
  {
    digitalWrite(TIRA_LED, HIGH);
  }
  else
  {
    if(!tiraOn)
    {
      digitalWrite(TIRA_LED, LOW);
    }
  }
  delay(1000);
}

String validarStock()
{
  String auxiliar;
  stock = digitalRead(INFRARROJO_1);
  if (stock == 1)
  {
    auxiliar = "1-";
  }
  else
  {
    auxiliar = "0-";
  }
  stock = digitalRead(INFRARROJO_2);
  if (stock == 1)
  {
    auxiliar += "1-";
  }
  else
  {
    auxiliar += "0-";
  }
  stock = digitalRead(INFRARROJO_3);
  if (stock == 1)
  {
    auxiliar += "1-";
  }
  else
  {
    auxiliar += "0-";
  }
  stock = digitalRead(INFRARROJO_4);
  if (stock == 1)
  {
    auxiliar += "1";
  }
  else
  {
    auxiliar += "0";
  }
  return auxiliar;
}

void sonarStarWars()
{
  MAX_COUNT = sizeof(melody2) / 2;
  for (int i = 0; i < MAX_COUNT; i++) 
  {
    toneM = melody2[i];
    beat = beats2[i];
    duration = beat * tempo;
    playTone();
    delayMicroseconds(pause);
  }
}

void playTone() {

  long elapsed_time = 0;
  if (toneM > 0) 
  {
    digitalWrite(TIRA_LED,HIGH);
    while (elapsed_time < duration) 
    {
      digitalWrite(BUZZER,HIGH);
      delayMicroseconds(toneM / 2);
      digitalWrite(BUZZER, LOW);
      delayMicroseconds(toneM / 2);
      elapsed_time += (toneM);
    }
    digitalWrite(TIRA_LED,LOW);
  }
  else 
  {
    for (int j = 0; j < rest_count; j++) 
    {
      delayMicroseconds(duration);
    }
  }
}

void expenderProducto(int producto)
{
  if(producto == 1)
  {
    SERVO_1.write(180);
    delay(900);
    SERVO_1.write(90);
  }
  else
  {
    if(producto == 3)
    {
      SERVO_3.attach(11);
      SERVO_3.write(0);
      delay(800);
      SERVO_3.detach();
    }
    else
    {
      SERVO_4.write(180);
      delay(800);
      SERVO_4.write(0);
    }
  }
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Retire producto");
  tone(7, 600);
  digitalWrite(LED, HIGH);
  delay(3000);
  noTone(7);
  digitalWrite(LED, LOW);
  lcd.clear();
  lcd.setCursor(1,0);
  lcd.print("Elija producto");
}
