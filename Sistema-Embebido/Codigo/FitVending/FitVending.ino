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
#define hayStock 1
#define maxVel 180
#define stop 90

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
int nivel_luz = 5;
Servo SERVO_1;
Servo SERVO_3;
Servo SERVO_4;

int temperatura;
int lightlevel;
int stock;
int producto;
int tiempo_inicial=0;
int tiempo_actual=0;
int duracion_buzzer=3000;    
int duracion_servo = 800;          
boolean sonando = false;
boolean girando = false;
char state;
String mensajeStock;
boolean tiraOn = false;
boolean milisrunning = false;

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
  tiempo_inicial=millis();
}

void loop() {
  lcd.setCursor(1,0);
  lcd.print("Elija producto");
  sensarTemperatura();
  sensarLuz();
  if(milisrunning)
  {
	  expenderProducto();
  }
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
        expenderProducto();
		producto = 1;
        sensarTemperatura();
        sensarLuz();
        break;
      case '3':
        expenderProducto();
		producto = 3;
        sensarTemperatura();
        sensarLuz();
        break;
      case '4':
        expenderProducto();
		producto = 4;
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
  if(lightlevel < nivel_luz)
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
}

String validarStock()
{
  String auxiliar;
  stock = digitalRead(INFRARROJO_1);
  if (stock == hayStock)
  {
    auxiliar = "1-";
  }
  else
  {
    auxiliar = "0-";
  }
  stock = digitalRead(INFRARROJO_2);
  if (stock == hayStock)
  {
    auxiliar += "1-";
  }
  else
  {
    auxiliar += "0-";
  }
  stock = digitalRead(INFRARROJO_3);
  if (stock == hayStock)
  {
    auxiliar += "1-";
  }
  else
  {
    auxiliar += "0-";
  }
  stock = digitalRead(INFRARROJO_4);
  if (stock == hayStock)
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
      digitalWrite(BUZZER, LOW);
      elapsed_time += (toneM);
    }
    digitalWrite(TIRA_LED,LOW);
  }
}

void expenderProducto()
{
  if(producto == 1)
  {
	tiempo_actual = millis() - tiempo_inicial;
	if(girando)
	{
		if(tiempo_actual > duracion_servo)
		{
			SERVO_1.write(stop);
			girando = false;
			tiempo_inicial = millis();
      milisrunning = false;
		}
	}
	else
	{
		SERVO_1.write(maxVel);
		girando = true;
	}
    
  }
  else
  {
    if(producto == 3)
    {
		tiempo_actual = millis() - tiempo_inicial;
		if(girando)
		{
			if(tiempo_actual > duracion_servo)
			{
				SERVO_3.detach();
				girando = false;
				tiempo_inicial = millis();
        milisrunning = false;
			}
		}
		else
		{
			SERVO_3.attach(11);
			SERVO_3.write(stop);
			girando = true;
		}
	}
	else
	{
		tiempo_actual = millis() - tiempo_inicial;
		if(girando)
		{
			if(tiempo_actual > duracion_servo)
			{
				SERVO_4.write(stop);
				girando = false;
        milisrunning = false;
				tiempo_inicial = millis();
			}
		}
		else
		{
			SERVO_4.write(maxVel);
			girando = true;
		}
	}
  }
  tiempo_actual = millis() - tiempo_inicial;
  if(sonando)
  {
	  if(tiempo_actual > duracion_buzzer)
	  {
		  noTone(7);
		  digitalWrite(LED, LOW);
		  lcd.clear();
		  lcd.setCursor(1,0);
		  lcd.print("Elija producto");
		  sonando = false;
		  tiempo_inicial = millis();
	  }
  }
  else
  {
	  lcd.clear();
	  lcd.setCursor(0,0);
	  lcd.print("Retire producto");
	  tone(7, 600);
	  digitalWrite(LED, HIGH);
	  sonando = true;
  }
  
}
