#include "Queue.h"
#include "Storage.h"

void Queue::PrintQueue() {
	uint8_t Size;
	for (int i = 0; i < QueueSize; i++) {
		switch (S.Storage[Queue[i]] >> 5) {
		case 0: {Size = 5; break; }
		case 1: {Size = 6; break; }
		case 2: {Size = 8; break; }
		case 3: {Size = 10; break; }
		case 4: {Size = 10; break; }
		case 5: {Size = 13; break; }
		case 6: {Size = 12; break; }
		case 7: {Size = 15; break; }
		}
		for (int j = 0; j < Size; j++) {
			Serial.println(S.Storage[Queue[i] + j]);
		}
		Serial.println("---------");
	}
}

bool Queue::IsFull() {
	return (QueueSize >= maxq - 1);
}

void Queue::EnQueue(uint16_t SIndex) {
	for (int i = QueueSize; i >= 0; i--) {
		if (i == 0) {
			Queue[0] = SIndex;
		}
		else {
			uint32_t ThisSeconds = ((((uint32_t)S.Storage[SIndex + 1]) << 24) +
				(((uint32_t)S.Storage[SIndex + 2]) << 16) +
				(((uint32_t)S.Storage[SIndex + 3]) << 8) +
				((uint32_t)S.Storage[SIndex + 4]));
			uint32_t OtherSeconds = ((((uint32_t)S.Storage[Queue[i - 1] + 1]) << 24) +
				(((uint32_t)S.Storage[Queue[i - 1] + 2]) << 16) +
				(((uint32_t)S.Storage[Queue[i - 1] + 3]) << 8) +
				((uint32_t)S.Storage[Queue[i - 1] + 4]));
			if (ThisSeconds < OtherSeconds) {
				Queue[i] = Queue[i - 1];
			}
			else {
				uint8_t ThisMillis = 0;
				uint8_t OtherMillis = 0;
				if (S.Storage[SIndex] & B00100000 == 1) { ThisMillis = S.Storage[Queue[i] + 5]; }
				if (S.Storage[Queue[i - 1]] & B00100000 == 1) { OtherMillis = S.Storage[Queue[i - 1] + 5]; }
				if (ThisSeconds == OtherSeconds && ThisMillis < OtherMillis) {
					Queue[i] = Queue[i - 1];
				}
				else {
					Queue[i] = SIndex;
					i = -1;
				}
			}
		}
	}
	QueueSize++;
}

void Queue::DeQueue(uint8_t ErraseIndex) {
	for (uint8_t i = ErraseIndex; i <= QueueSize; i++) {
		if (Queue[i + 1] >= 0 && Queue[i + 1] < 0) {
			Queue[i] = 0;
		}
		else {
			Queue[i] = Queue[i + 1];
		}
	}
	QueueSize--;
}

void Queue::UpdatePointers(uint8_t Size) {
	for (int i = 0; i < QueueSize; i++) {
		if (Queue[i] > Index) {
			Queue[i] -= Size;
		}
	}
}

void Queue::StoreSeconds(uint32_t Seconds) {
	S.Storage[Index + 1] = Seconds >> 24;
	S.Storage[Index + 2] = Seconds >> 16;
	S.Storage[Index + 3] = Seconds >> 8;
	S.Storage[Index + 4] = Seconds;
}

uint32_t Queue::GetInterval(uint8_t ExtInd, uint8_t IntInd1, uint8_t IntInd2) {
	return (((uint32_t)S.Storage[Index + ExtInd] & B11110000) << 12) + (((uint32_t)S.Storage[Index + IntInd1]) << 8) + ((uint32_t)S.Storage[Index + IntInd2]);
}

uint32_t Queue::GetFrequency(uint8_t ExtInd, uint8_t FreInd1, uint8_t FreInd2) {
	return (((uint32_t)S.Storage[Index + ExtInd] & B00001111) << 16) + (((uint32_t)S.Storage[Index + FreInd1]) << 8) + ((uint32_t)S.Storage[Index + FreInd2]);
}

void Queue::Update() {
	Index = Queue[0];
	uint32_t Seconds = ((((uint32_t)S.Storage[Index + 1]) << 24) +
		(((uint32_t)S.Storage[Index + 2]) << 16) +
		(((uint32_t)S.Storage[Index + 3]) << 8) +
		((uint32_t)S.Storage[Index + 4]));
	switch (S.Storage[Index] >> 5) {
	case 0: {
		DeQueue(0);
		UpdatePointers(5);
		S.Compress(Index, 5);
		Full = IsFull();
		break;
	}
	case 1: {
		DeQueue(0);
		UpdatePointers(6);
		S.Compress(Index, 6);
		Full = IsFull();
		break;
	}
	case 2: {
		if ((S.Storage[Index] & 1) == 0) {
			S.Storage[Index] ^= 3;

			uint32_t Interval = GetInterval(7, 5, 6);
			Seconds += Interval;
			StoreSeconds(Seconds);

			DeQueue(0);
			EnQueue(Index);
		}
		else {
			DeQueue(0);
			UpdatePointers(8);
			S.Compress(Index, 8);
			Full = IsFull();
		}
		break;
	}
	case 3: {
		if ((S.Storage[Index] & 1) == 0) {
			S.Storage[Index] ^= 3;

			uint32_t Interval = GetInterval(9, 6, 7);
			Seconds += Interval;
			uint16_t SM = S.Storage[Index + 5];
			uint16_t IM = S.Storage[Index + 8];
			if (SM + IM > 255) { Seconds++; }
			StoreSeconds(Seconds);

			S.Storage[Index + 5] += S.Storage[Index + 8];

			DeQueue(0);
			EnQueue(Index);
		}
		else {
			DeQueue(0);
			UpdatePointers(10);
			S.Compress(Index, 10);
			Full = IsFull();
		}
		break;
	}
	case 4: {
		if ((S.Storage[Index] & 1) == 0) {
			S.Storage[Index] ^= 3;

			uint32_t Interval = GetInterval(9, 5, 6);
			Seconds += Interval;
			StoreSeconds(Seconds);

			DeQueue(0);
			EnQueue(Index);
		}
		else {
			S.Storage[Index] ^= 3;

			uint32_t Frequency = GetFrequency(9, 7, 8);
			Seconds += Frequency;
			StoreSeconds(Seconds);

			DeQueue(0);
			EnQueue(Index);
		}
		break;
	}
	case 5: {
		if ((S.Storage[Index] & 1) == 0) {
			S.Storage[Index] ^= 3;

			uint32_t Interval = GetInterval(12, 6, 7);
			Seconds += Interval;
			uint16_t SM = S.Storage[Index + 5];
			uint16_t IM = S.Storage[Index + 8];
			if (SM + IM > 255) { Seconds++; }
			StoreSeconds(Seconds);

			S.Storage[Index + 5] += S.Storage[Index + 8];

			DeQueue(0);
			EnQueue(Index);
		}
		else {
			S.Storage[Index] ^= 3;

			uint32_t Frequency = GetFrequency(12, 9, 10);
			Seconds += Frequency;
			uint16_t SM = S.Storage[Index + 5];
			uint16_t FM = S.Storage[Index + 11];
			if (SM + FM > 255) { Seconds++; }
			StoreSeconds(Seconds);

			S.Storage[Index + 5] += S.Storage[Index + 11];

			DeQueue(0);
			EnQueue(Index);
		}
		break;
	}
	case 6: {
		uint16_t Repeat = ((uint16_t)S.Storage[Index + 9] << 8) +
			((uint16_t)S.Storage[Index + 10]);
		if (Repeat > 0) {
			if ((S.Storage[Index] & 1) == 0) {
				S.Storage[Index] ^= 3;

				uint32_t Interval = GetInterval(11, 5, 6);
				Seconds += Interval;
				StoreSeconds(Seconds);

				Repeat--;
				S.Storage[Index + 9] = Repeat >> 8;
				S.Storage[Index + 10] = Repeat;

				DeQueue(0);
				EnQueue(Index);
			}
			else {
				S.Storage[Index] ^= 3;

				uint32_t Frequency = GetFrequency(11, 7, 8);
				Seconds += Frequency;
				StoreSeconds(Seconds);

				DeQueue(0);
				EnQueue(Index);
			}
		}
		else {
			DeQueue(0);
			UpdatePointers(12);
			S.Compress(Index, 12);
			Full = IsFull();
		}
		break;
	}
	case 7: {
		uint16_t Repeat = ((uint16_t)S.Storage[Index + 12] << 8) +
			((uint16_t)S.Storage[Index + 13]);
		if (Repeat > 0) {
			if ((S.Storage[Index] & 1) == 0) {
				S.Storage[Index] ^= 3;

				uint32_t Interval = GetInterval(14, 6, 7);
				Seconds += Interval;
				uint16_t SM = S.Storage[Index + 5];
				uint16_t IM = S.Storage[Index + 8];
				if (SM + IM > 255) { Seconds++; }
				StoreSeconds(Seconds);

				S.Storage[Index + 5] += S.Storage[Index + 8];

				Repeat--;
				S.Storage[Index + 12] = Repeat >> 8;
				S.Storage[Index + 13] = Repeat;

				DeQueue(0);
				EnQueue(Index);
			}
			else {
				S.Storage[Index] ^= 3;

				uint32_t Frequency = GetFrequency(14, 9, 10);
				Seconds += Frequency;
				uint16_t SM = S.Storage[Index + 5];
				uint16_t FM = S.Storage[Index + 11];
				if (SM + FM > 255) { Seconds++; }
				StoreSeconds(Seconds);

				S.Storage[Index + 5] += S.Storage[Index + 11];

				DeQueue(0);
				EnQueue(Index);
			}
		}
		else {
			DeQueue(0);
			UpdatePointers(15);
			S.Compress(Index, 15);
			Full = IsFull();
		}
		break;
	}
	}
}

void Queue::Errase(uint16_t Index) {
	for (uint8_t i = 0; i < QueueSize; i++) {
		if (Queue[i] == Index) {
			DeQueue(i);
			i = QueueSize;
		}
	}
}

void Queue::ErraseAll() {
	for (uint16_t i = 0; i < QueueSize; i++) {
		Queue[i] = 0;
	}
	QueueSize = 0;
}
