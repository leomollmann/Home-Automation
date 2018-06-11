#include "RGB.h"

void RGB::Run() {
	if (T.GlobalMillis % 30 < 5) {
		if (done == false) {

			done = true;
		}
	}
	else done = false;
}

void RGB::SelectMode() {
	switch (mode){
		case 0: { //Default Color
			SetReg(red, green, blue); 
			break;
		}
		case 1: { //Single Color
			SetReg(red, green, blue);
			break;
		}
		case 2: { //Breathe Color
			SetReg(red, green, blue);
			break;
		}
		case 3: { //Rainbow Color
			SetReg(red, green, blue);
			break;
		}
	}
}

void RGB::SetReg(uint8_t Re, uint8_t Gr, uint8_t Bl) {
	analogWrite(3, Re);
	analogWrite(5, Gr);
	analogWrite(6, Bl);
}