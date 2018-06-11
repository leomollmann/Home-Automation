#include "Time.h"

void Time::Update()
{
	GlobalMillis = (millis() + MillisOffset) % 1000;
	if (GlobalMillis > PreviusMillis){
		GlobalTime++;
	}
	PreviusMillis = GlobalMillis;
}

void Time::StartCount(uint32_t TOff, uint16_t MOff)
{
	GlobalTime = TOff;
	MillisOffset = MOff;
}
