/*
 Name:		LineFilter.ino
 Created:	02/06/2017 14:54:52
 Author:	Leonardo Möllmann Schöller

=================== Network Buffer[] reference (Network Input) =====================

 DEFAULT DATA{

	 Buffer[0] DeviceID
	 Buffer[1] PacketID
	 Buffer[2] Operation {[xxx = Relay(only for case 5 and 6), xxxxx = Command]}

 }

 case 1:{ 
 -Return relay states

	  DEFAULT DATA
 }

 case 2:{
 -Return sensors + relays states

	 DEFAULT DATA
 }

 case 3:{
 -Scheuld task (maximum size)

	DEFAULT DATA
	Buffer[3] Data {[]}
	Buffer[4] Seconds
	Buffer[5] Seconds
	Buffer[6] Seconds
	Buffer[7] Seconds
	Buffer[8] Seconds Millis
	Buffer[9] Interval
	Buffer[10] Interval
	Buffer[11] Interval Millis
	Buffer[12] Frequency
	Buffer[13] Frequency
	Buffer[14] Frequency Millis
	Buffer[15] Repeat
	Buffer[16] Repeat
	Buffer[17] Extended
 }

 case 4:{
 -Errase scheulded task

	 DEFAULT DATA
	 Buffer[3] Data
	 Buffer[4] Seconds
	 Buffer[5] Seconds
	 Buffer[6] Seconds
	 Buffer[7] Seconds
	 Buffer[8] Seconds Millis
 }

 case 5:{
 -Turn on single relay

	 DEFAULT DATA
 }

 case 6:{
 -Turn off single relay

	 DEFAULT DATA
 }

 case 7:{
 -Errase all data

	 DEFAULT DATA
 }

 case 8:{
 -Reset arduino

	 DEFAULT DATA
 }

 case 9:{
 -Reboot

	DEFAULT DATA
 }
 
 case 10:{
 -Set offsets

	DEFAULT DATA
	Buffer[3] Time offset
	Buffer[4] Time offset
	Buffer[5] Time offset
	Buffer[6] Time offset
	Buffer[7] Millis offset
	Buffer[8] Millis offset
 }

 case 11:{
  -Set RGB Color

	DEFAULT DATA
	Buffer[3] Mode
	Buffer[4] Color1
	Buffer[5] Color2
 }

 =================== Network Ack[] reference (Network Output) =====================
	
 case 1:{
    Ack[0] = PackedID;
	Ack[1] = RelayState;
 }

 case 2:{
	Ack[0] = PackedID;
	Ack[1] = RelayState;
	Ack[2] = Temp;
	Ack[3] = Temp;
	Ack[4] = Temp;
	Ack[5] = Temp;
	Ack[6] = Hum;
	Ack[7] = Hum;
	Ack[8] = Hum;
	Ack[9] = Hum;
	Ack[10] = Energy;
	Ack[11] = Energy;
	Ack[12] = Energy;
	Ack[13] = Energy;
 }

 case 3:{
	Ack[0] = PackedID;
	Ack[1] = Feedback byte;
 }

 case 4:{
	Ack[0] = PackedID;
	Ack[1] = Feedback byte;
 }

 case 5:{
	Ack[0] = PackedID;
 }

 case 6:{
	Ack[0] = PackedID;
 }

 case 7:{
	Ack[0] = PackedID;
 }

 case 8:{
	Ack[0] = PackedID;
 }

 case 9:{
	Ack[0] = PackedID;
 }

 case 10:{
	Ack[0] = PackedID;
 }

 case 11:{
	Ack[0] = PackedID;
 }

 */

#include "Time.h"
Time T;
#include <DHT.h>
DHT dht(7,DHT22);
#include <RF24.h>
#include <nRF24L01.h>
RF24 radio(9, 10);
#include "Storage.h"
Storage S;
#include "Queue.h"
Queue Q;
#include "Network.h"
Network N;
#include "Sensors.h"
Sensors E;
#include "RGB.h"
RGB rgb;

#define Red "PD3";
#define Green "PD5";
#define Blue "PD6";
#define Temp "PB5";

bool done = false;

void setup() {
	pinMode(2, OUTPUT); //Outlets Control
	pinMode(4, OUTPUT); 
	pinMode(7, OUTPUT); 
	pinMode(8, OUTPUT); 
	pinMode(9, OUTPUT);
	pinMode(10, OUTPUT);
	pinMode(11, OUTPUT);
	pinMode(12, OUTPUT);
	pinMode(A0, INPUT); //Energy Inputs
	pinMode(A1, INPUT);
	pinMode(A2, INPUT);
	pinMode(A3, INPUT);
	pinMode(A4, INPUT);
	pinMode(A5, INPUT);
	pinMode(A6, INPUT);
	pinMode(A7, INPUT);
	pinMode(3, OUTPUT); //R
	pinMode(5, OUTPUT); //G
	pinMode(6, OUTPUT); //B
	pinMode(13, INPUT); //DHT 
	N.begin();
	E.begin();
}

void Reboot() {
	delay(1000);
}

void Command() {
	switch ((N.Buffer[2] & B00011111)) {
		case 1: {
			N.RelaysAck();
			break;
		}
		case 2: {
			N.SensorsAck();
			break;
		}
		case 3: {
			N.FeedAck();
			Q.EnQueue(S.Store(N.Buffer));
			break;
		}
		case 4: {
			N.FeedAck();
			uint16_t Index = S.Find(N.Buffer);
			Q.Errase(Index);
			S.Errase(Index);
			break;
		}
		case 5: {
			N.DefAck();
			PortWrite(N.Buffer[2] >> 5, 1);
			break;
		}
		case 6: {
			N.DefAck();
			PortWrite(N.Buffer[2] >> 5, 0);
			break;
		}
		case 7: {
			N.DefAck();
			Q.ErraseAll();
			S.ErraseAll();
			break;
		}
		case 8: {
			N.DefAck();
			Q.ErraseAll();
			S.ErraseAll();
			Reboot();
			break;
		}
		case 9: {
			N.DefAck();
			Reboot();
			break;
		}
		case 10: {
			N.DefAck();
			T.StartCount(((uint32_t)N.Buffer[3] << 24) + 
				((uint32_t)N.Buffer[4] << 16) + 
				((uint32_t)N.Buffer[5] << 8) + 
				((uint32_t)N.Buffer[6]), 
				((uint16_t)N.Buffer[7] << 8) + 
				((uint16_t)N.Buffer[8]));
			break; 
		}
	}
}

void PortWrite(uint8_t Port, bool State) {
	switch (Port) {
	case 0: { State ? PORTD |= _BV(PD2) : PORTD &= ~_BV(PD2); break; }
	case 1: { State ? PORTD |= _BV(PD4) : PORTD &= ~_BV(PD4); break; }
	case 2: { State ? PORTD |= _BV(PD7) : PORTD &= ~_BV(PD7); break; }
	case 3: { State ? PORTB |= _BV(PB0) : PORTB &= ~_BV(PB0); break; }
	case 4: { State ? PORTD |= _BV(PB1) : PORTB &= ~_BV(PB1); break; }
	case 5: { State ? PORTD |= _BV(PB3) : PORTB &= ~_BV(PB2); break; }
	case 6: { State ? PORTD |= _BV(PB3) : PORTB &= ~_BV(PB3); break; }
	case 7: { State ? PORTB |= _BV(PB4) : PORTB &= ~_BV(PB4); break; }
	}
}

void loop() {
	T.Update();
	if (N.ReadRF() == true) {Command();}
	if (Q.QueueSize > 0) {
		if (T.GlobalTime >= ((((uint32_t)S.Storage[Q.Queue[0] + 1]) << 24) + 
			(((uint32_t)S.Storage[Q.Queue[0] + 2]) << 16) + 
			(((uint32_t)S.Storage[Q.Queue[0] + 3]) << 8) + 
			((uint32_t)S.Storage[Q.Queue[0] + 4]))) {
			if ((byte)((T.GlobalMillis * 255) / 999) >= (S.Storage[Q.Queue[0] + 5])) {
				PortWrite((S.Storage[Q.Queue[0]] & B00011100) >> 2, (S.Storage[Q.Queue[0]] & B00000010) >> 1);
				Q.Update();
			}
		}
	}
	rgb.Run();
}