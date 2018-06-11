// Queue.h

#ifndef _QUEUE_h
#define _QUEUE_h

#include "arduino.h"
#include "Storage.h"

#define maxq 100

extern Storage S;

class Queue
{
public:

	bool Full = 0;
	uint8_t QueueSize = 0;
	uint16_t Queue[maxq] = { 0 };

	void PrintQueue();
	void EnQueue(uint16_t Index);
	void DeQueue(uint8_t ErraseIndex);
	void Update();
	void Errase(uint16_t Index);
	void ErraseAll();

private:

	uint16_t Index = 0;

	void UpdatePointers(uint8_t Size);
	void StoreSeconds(uint32_t Seconds);
	uint32_t GetInterval(uint8_t ExtInd, uint8_t IntInd1, uint8_t IntInd2);
	uint32_t GetFrequency(uint8_t ExtInd, uint8_t FreInd1, uint8_t FreInd2);
	bool IsFull();

};

#endif

