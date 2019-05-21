#include <Wire.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);
int LED = 2;
int SENSOR = 3;
int SENSOR_PEDIDO = 4;
int BUZZER = 7;
int lightlevel;
int period = 1000;
unsigned long time_now = 0;
int stock;
int pedido;

void setup() {
  pinMode(LED, OUTPUT);
  pinMode(SENSOR,INPUT);
  digitalWrite(LED,LOW);
  Wire.begin();
  lcd.begin(16, 2);
  lcd.clear();
  lcd.backlight();
}

void loop() {
  lcd.setCursor(1,0);
  lcd.print("Elija producto");
  //Nivel leido del fotoresistor
  lightlevel = analogRead(A3);
  //25 es el valor que ponemos nosotros
  if(lightlevel < 25 )
  {
    digitalWrite(LED, HIGH);
  }
  else
  {
    digitalWrite(LED, LOW);
  }
  //Determinamos si hay presencia de producto o no
  stock = digitalRead(SENSOR);
  if(stock == 1)
  {
    lcd.setCursor(2,1);
    lcd.print("No hay stock");
  }
  else
  {
    lcd.setCursor(0,1);
    lcd.print("   Hay stock   ");
  }
  //Prueba de buzzer para que suene al despachar producto
  pedido = digitalRead(SENSOR_PEDIDO);
  if(pedido == 0)
  {
    tone(7, 600);
    delay(3000);
    noTone(7);
  }
}
