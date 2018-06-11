// RGB.h

#ifndef _RGB_h
#define _RGB_h

#include "arduino.h"
#define DefRed 0;
#define DefGreen 160;
#define DefBlue 255;

class RGB
{
	public:
		void Run();
		void Update(uint8_t Mode);
	private:
		void SelectMode();
		void SetReg(uint8_t Re, uint8_t Gr, uint8_t Bl);
		uint8_t Red0, Red1, Green0, Green1, Blue0, Blue1;
		uint8_t mode = 0;
		uint8_t Rstep, Gstep, Bstep;
		uint8_t red, green, blue;
};

#endif

