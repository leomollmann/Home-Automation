#include "Network.h"

void Network::begin() {
	radio.begin();
	radio.openWritingPipe(AddressTX);
	radio.openReadingPipe(1, AddressRX);
	radio.setAutoAck(0);
	radio.enableDynamicPayloads();
	radio.startListening();
}

void Network::FeedAck() {
	Ack[0] = Buffer[1];
	Ack[1] = (Q.Full & 1) | (S.Full & 2);
	radio.stopListening();
	radio.write(&Ack, 2);
	radio.startListening();
}

void Network::DefAck() {
	Ack[0] = Buffer[1];
	radio.stopListening();
	radio.write(&Ack, 1);
	radio.startListening();
}

void Network::RelaysAck(){
	E.GetRelayState();
	Ack[0] = Buffer[1];
	Ack[1] = E.RelayState;
	radio.stopListening();
	radio.write(&Ack, 2);
	radio.startListening();
}

void Network::SensorsAck() {
	E.GetRelayState();
	E.GetTempAndHum();
	E.GetEnergy();
	Ack[0] = Buffer[1];
	Ack[1] = E.RelayState;
	Ack[2] = (byte) E.Tempr >> 24;
	Ack[3] = (byte) E.Tempr >> 16;
	Ack[4] = (byte) E.Tempr >> 8;
	Ack[5] = (byte) E.Tempr;
	Ack[6] = (byte) E.Hum >> 24;
	Ack[7] = (byte) E.Hum >> 16;
	Ack[8] = (byte) E.Hum >> 8;
	Ack[9] = (byte) E.Hum;
	Ack[10] = (byte) E.Energy >> 24;
	Ack[11] = (byte) E.Energy>> 16;
	Ack[12] = (byte) E.Energy >> 8;
	Ack[13] = (byte) E.Energy;
	radio.stopListening();
	radio.write(&Ack, 14);
	radio.startListening();
}

bool Network::ReadRF() {
	if (radio.available() > 0) {
		radio.read(&Buffer, radio.getDynamicPayloadSize());
		if (Buffer[0] == ID) {
			if (Buffer[1] != PreBuffer[1]) {
				memcpy(PreBuffer, Buffer, sizeof(Buffer));
				return true;
			}
			else {
				DefAck();
				return false;
			}
		}
	}
}