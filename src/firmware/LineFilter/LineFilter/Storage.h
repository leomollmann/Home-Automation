// Storage.h

#ifndef _STORAGE_h
#define _STORAGE_h

#include "arduino.h"

#define maxs 1200

class Storage
{
public:

	bool Full = 0;
	uint16_t End = 0;
	uint8_t Storage[maxs] = { 0 };

	void PrintStorage();
	void Errase(uint16_t Index);
	uint16_t Store(uint8_t Buffer[18]);
	uint16_t Find(uint8_t Buffer[18]);
	void Compress(uint16_t CompressStart, uint8_t Size);
	void ErraseAll();

private:

	bool IsFull(uint8_t Size);

};

#endif

