// Network.h

#ifndef _NETWORK_h
#define _NETWORK_h

#include "arduino.h"
#include <RF24.h>
#include <nRF24L01.h>
#include "Queue.h"
#include "Storage.h"
#include "Sensors.h"

#define DefaultTX 0;
#define DefaultRX 1;

extern Sensors E;
extern RF24 radio;
extern Queue Q;
extern Storage S;

class Network
{
public:

	uint8_t PreBuffer[18];
	uint8_t Buffer[18];
	uint8_t Ack[14];
	void FeedAck();
	void RelaysAck();
	void DefAck();
	bool ReadRF();
	void SensorsAck();
	void begin();

private:

	uint8_t ID = 1;
	uint32_t AddressTX = 0xABCDABCD71LL;
	uint32_t AddressRX = 0x544d52687CLL;
};

#endif

