#include "Storage.h"

void Storage::PrintStorage() {
	for (int i = 0; i < End; i++) {
		Serial.println(Storage[i]);
	}
}

bool Storage::IsFull(uint8_t Size) {
	return(End + Size > maxs);
}

uint16_t Storage::Store(uint8_t Buffer[18]) {
	if (End != 0) { End++; }
	uint16_t Index = End;
	uint8_t Size = 0;
	switch (Buffer[3] >> 5) {
	case 0: {
		Size = 5;
		Storage[End] = Buffer[3]; 			//Data
		Storage[End + 1] = Buffer[4];		//Seconds
		Storage[End + 2] = Buffer[5];
		Storage[End + 3] = Buffer[6];
		Storage[End + 4] = Buffer[7];
		break;
	}
	case 1: {
		Size = 6;
		Storage[End] = Buffer[3];			//Data
		Storage[End + 1] = Buffer[4];		//Seconds
		Storage[End + 2] = Buffer[5];
		Storage[End + 3] = Buffer[6];
		Storage[End + 4] = Buffer[7];
		Storage[End + 5] = Buffer[8];		//Seconds Millis
		break;
	}
	case 2: {
		Size = 8;
		Storage[End] = Buffer[3];			//Data
		Storage[End + 1] = Buffer[4];		//Seconds
		Storage[End + 2] = Buffer[5];
		Storage[End + 3] = Buffer[6];
		Storage[End + 4] = Buffer[7];
		Storage[End + 5] = Buffer[9];		//Interval
		Storage[End + 6] = Buffer[10];
		Storage[End + 7] = Buffer[17];	//Extended
		break;
	}
	case 3: {
		Size = 10;
		Storage[End] = Buffer[3];			//Data
		Storage[End + 1] = Buffer[4];		//Seconds
		Storage[End + 2] = Buffer[5];
		Storage[End + 3] = Buffer[6];
		Storage[End + 4] = Buffer[7];
		Storage[End + 5] = Buffer[8];		//Seconds Millis
		Storage[End + 6] = Buffer[9];		//Interval
		Storage[End + 7] = Buffer[10];
		Storage[End + 8] = Buffer[11];		//Interval Millis
		Storage[End + 9] = Buffer[17];		//Extended
		break;
	}
	case 4: {
		Size = 10;
		Storage[End] = Buffer[3];			//Data
		Storage[End + 1] = Buffer[4];		//Seconds
		Storage[End + 2] = Buffer[5];
		Storage[End + 3] = Buffer[6];
		Storage[End + 4] = Buffer[7];
		Storage[End + 5] = Buffer[9];		//Interval
		Storage[End + 6] = Buffer[10];
		Storage[End + 7] = Buffer[12];		//Frequency
		Storage[End + 8] = Buffer[13];
		Storage[End + 9] = Buffer[17];		//Extended
		break;
	}
	case 5: {
		Size = 13;
		Storage[End] = Buffer[3];			//Data
		Storage[End + 1] = Buffer[4];		//Seconds
		Storage[End + 2] = Buffer[5];
		Storage[End + 3] = Buffer[6];
		Storage[End + 4] = Buffer[7];
		Storage[End + 5] = Buffer[8];       //Seconds Millis
		Storage[End + 6] = Buffer[9];		//Interval
		Storage[End + 7] = Buffer[10];
		Storage[End + 8] = Buffer[11];       //Interval Millis
		Storage[End + 9] = Buffer[12];		//Frequency
		Storage[End + 10] = Buffer[13];
		Storage[End + 11] = Buffer[14];		//Frequency Millis
		Storage[End + 12] = Buffer[17];		//Extended
		break;
	}
	case 6: {
		Size = 12;
		Storage[End] = Buffer[3];			//Data
		Storage[End + 1] = Buffer[4];		//Seconds
		Storage[End + 2] = Buffer[5];
		Storage[End + 3] = Buffer[6];
		Storage[End + 4] = Buffer[7];
		Storage[End + 5] = Buffer[9];		//Interval
		Storage[End + 6] = Buffer[10];
		Storage[End + 7] = Buffer[12];		//Frequency
		Storage[End + 8] = Buffer[13];
		Storage[End + 9] = Buffer[15];		//Repeat
		Storage[End + 10] = Buffer[16];
		Storage[End + 11] = Buffer[17];		//Extended
		break;
	}
	case 7: {
		Size = 15;
		Storage[End] = Buffer[3];			//Data
		Storage[End + 1] = Buffer[4];		//Seconds
		Storage[End + 2] = Buffer[5];
		Storage[End + 3] = Buffer[6];
		Storage[End + 4] = Buffer[7];
		Storage[End + 5] = Buffer[8];       //Seconds Millis
		Storage[End + 6] = Buffer[9];		//Interval
		Storage[End + 7] = Buffer[10];
		Storage[End + 8] = Buffer[11];       //Interval Millis
		Storage[End + 9] = Buffer[12];		//Frequency
		Storage[End + 10] = Buffer[13];
		Storage[End + 11] = Buffer[14];		//Frequency Millis
		Storage[End + 12] = Buffer[15];		//Repeat
		Storage[End + 13] = Buffer[16];
		Storage[End + 14] = Buffer[17];		//Extended
		break;
	}
	}
	End += Size - 1;
	return Index;
}

void Storage::Compress(uint16_t CompressStart, uint8_t Size) {
	for (uint16_t i = CompressStart; i < End; i++) {
		Storage[i] = Storage[i + Size];
	}
	End -= Size;
	Full = IsFull(15);
}

void Storage::Errase(uint16_t Index) {
	uint8_t Size;
	switch (Storage[Index] >> 5) {
	case 0: {Size = 5; break; }
	case 1: {Size = 6; break; }
	case 2: {Size = 8; break; }
	case 3: {Size = 10; break; }
	case 4: {Size = 10; break; }
	case 5: {Size = 13; break; }
	case 6: {Size = 12; break; }
	case 7: {Size = 15; break; }
	}
	Compress(Index, Size);
}

uint16_t Storage::Find(uint8_t Buffer[18]) {
	uint16_t Index;
	uint32_t ThisSeconds = ((((uint32_t)Buffer[4]) << 24) +
		(((uint32_t)Buffer[5]) << 16) +
		(((uint32_t)Buffer[6]) << 8) +
		((uint32_t)Buffer[7]));
	while (Index < End) {
		uint32_t StoredSeconds = ((((uint32_t)Storage[Index + 1]) << 24) +
			(((uint32_t)Storage[Index + 2]) << 16) +
			(((uint32_t)Storage[Index + 3]) << 8) +
			((uint32_t)Storage[Index + 4]));
		if (Storage[Index] == Buffer[3] && StoredSeconds == ThisSeconds) {
			return Index;
		}
		else {
			uint8_t Size;
			switch (Storage[Index] >> 5) {
			case 0: {Size = 5; break; }
			case 1: {Size = 6; break; }
			case 2: {Size = 8; break; }
			case 3: {Size = 10; break; }
			case 4: {Size = 10; break; }
			case 5: {Size = 13; break; }
			case 6: {Size = 12; break; }
			case 7: {Size = 15; break; }
			}
			Index += Size;
		}
	}
	return End;
}

void Storage::ErraseAll() {
	for (uint16_t i = 0; i < End; i++) {
		Storage[i] = 0;
	}
	End = 0;
}
