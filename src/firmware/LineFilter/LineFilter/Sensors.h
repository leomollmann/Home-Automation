// Sensors.h

#ifndef _SENSORS_h
#define _SENSORS_h

#include "arduino.h"
#include <DHT.h>

extern DHT dht;

class Sensors
{
public:

	uint8_t RelayState = 0;
	float Tempr = 0;
	float Hum = 0;
	float Energy = 0;
	void GetRelayState();
	void GetTempAndHum();
	void GetEnergy();
	void begin();
};

#endif

