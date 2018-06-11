#include "Sensors.h"

void Sensors::begin() {
	dht.begin();
}

void Sensors::GetRelayState() {
	RelayState = PORTC & B0001111;
}

void Sensors::GetTempAndHum() {
	Tempr = dht.readTemperature();
	Hum = dht.readHumidity();
}

void Sensors::GetEnergy() {
	Energy = analogRead(A4);
}