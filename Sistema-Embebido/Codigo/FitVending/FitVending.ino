#include <DHT.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

//Declaracion de pines
//int LED_ROJO = 2;
int TEMPERATURA = 2;
int LED_AZUL = 3;
int BUZZER = 4;
//Pines con PWM
int MOTOR_1 = 5;
int MOTOR_2 = 6;
int MOTOR_3 = 9;
int MOTOR_4 = 10;
//Fin Pines con PWM
int INFRARROJO_1 = 7;
int INFRARROJO_2 = 8;
int INFRARROJO_3 = 11;
int INFRARROJO_4 = 12;
int TIRA_LED = 13;
//Pines destinado al Bluetooth
int RECEPTOR = 0;
int TRANSMISOR = 1;
//Fin Pines destinados al Bluetooth
//Fin declaracion de pines

//Declaracion de variables
int temperatura;
int pedido;
int lightlevel;
int tiempo_inicial=0;
int tiempo_actual=0;
int duracion_buzzer=3000;     
boolean hay_stock = false;         
boolean sonando = false;
boolean estado_reposo = true;
boolean estado_procesandoPedido = false;
boolean estado_esperandoRetiro = false;
int tiempo_inicial_motor=0;
int tiempo_actual_motor=0;
int duracion_motor = 2000;
int tiempo_inicial_buzzer=0;
int tiempo_actual_buzzer=0;
int duracion_buzzer=3000;  
//Fin declaracion de variables
DHT dht(SENSOR, DHT11);
LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup() {
  pinMode(LED_ROJO, OUTPUT);
  pinMode(LED_AZUL, OUTPUT);
  pinMode(INFRARROJO_1,INPUT);
  pinMode(INFRARROJO_2,INPUT);
  pinMode(INFRARROJO_3,INPUT);
  pinMode(INFRARROJO_4,INPUT);
  digitalWrite(LED_ROJO,LOW);
  digitalWrite(LED_AZUL,LOW);
  dht.begin();
  Wire.begin();
  lcd.begin(16, 2);
  lcd.clear();
  lcd.backlight();
  tiempo_inicial_motores=millis();
  tiempo_inicial_buzzer=millis();
}

void loop() {
	if(estado_reposo)
	{
		digitalWrite(LED_ROJO, HIGH);
		lcd.setCursor(1,0);
		lcd.print("Elija producto");
		sensarTemperatura();
		sensarLuz();
		/*
		if(señal de pedido)
		{
			estado_reposo = false;
			estado_procesandoPedido = true;
		}
		*/
	}
	else
	{
		if(estado_procesandoPedido)
		{
			sensarTemperatura();
			sensarLuz();
			switch (producto) 
			{
			  case 1:
				stock = digitalRead(INFRARROJO_1);
				if(validarStock(stock))
				{
					//Girar motor 1
					estado_procesandoPedido = false;
					estado_esperandoRetiro = true;
				}
				else
				{
					estado_procesandoPedido = false;
					estado_reposo = true;
				}
				break;
			  case 2:
				stock = digitalRead(INFRARROJO_2);
				if(validarStock(stock))
				{
					//Girar motor 2
					estado_procesandoPedido = false;
					estado_esperandoRetiro = true;
				}
				else
				{
					estado_procesandoPedido = false;
					estado_reposo = true;
				}
				break;
			  case 3:
			    stock = digitalRead(INFRARROJO_3);
				if(validarStock(stock))
				{
					//Girar motor 3
					estado_procesandoPedido = false;
					estado_esperandoRetiro = true;
				}
				else
				{
					estado_procesandoPedido = false;
					estado_reposo = true;
				}
			    break;
			  default:
				stock = digitalRead(INFRARROJO_4);
				if(validarStock(stock))
				{
					//Girar motor 4
					estado_procesandoPedido = false;
					estado_esperandoRetiro = true;
				}
				else
				{
					estado_procesandoPedido = false;
					estado_reposo = true;
				}
				break;
			}
		}
		else
		{
			sensarTemperatura();
			sensarLuz();
			tiempo_actual_motor = millis() - tiempo_inicial_motor;
			if(tiempo_actual_motor > duracion_motor)
			{
				digitalWrite(LED_ROJO, LOW);
				digitalWrite(LED_AZUL, HIGH);
				lcd.clear();
				lcd.setCursor(1,0);
				lcd.print("Retire producto");
				calcularTemperatura();
				tiempo_actual_buzzer = millis() - tiempo_inicial_buzzer;
				if(sonando)
				{
				  if(tiempo_actual_buzzer > duracion_buzzer)
				  {
					noTone(7);
					sonando = false;
					tiempo_inicial_buzzer = millis();
					estado_esperandoRetiro = false;
					estado_reposo = true;
					digitalWrite(LED_ROJO, HIGH);
					digitalWrite(LED_AZUL, LOW);
					lcd.clear();
				  }
				}
				else
				{
				  tone(7,600);
				  sonando = true;
				}
			}
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
   //Nivel leido del fotoresistor
  lightlevel = analogRead(A3);
  //25 es el valor que ponemos nosotros
  if(lightlevel < 25 )
  {
    digitalWrite(TIRA_LED, HIGH);
  }
  else
  {
    digitalWrite(TIRA_LED, LOW);
  }
}

boolean validarStock(int stock)
{
	if(stock == 0)
	{
		return true:
	}	
	return false;
}
