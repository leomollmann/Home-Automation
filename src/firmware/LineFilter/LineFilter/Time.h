// Time.h

#ifndef _TIME_h
#define _TIME_h

#include "arduino.h"

class Time {
public:

	uint32_t GlobalTime;
	uint16_t GlobalMillis;
	void Update();
	void StartCount(uint32_t TOff, uint16_t MOff);

private:

	uint32_t PreviusMillis;
	uint16_t MillisOffset;
};

#endif

