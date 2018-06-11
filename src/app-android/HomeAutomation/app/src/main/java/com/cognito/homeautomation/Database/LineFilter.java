package com.cognito.homeautomation.Database;

import android.widget.ListView;

import com.cognito.homeautomation.Database.TOutlet;

public class LineFilter{

    private int DeviceID;
    private String Name;
    private boolean State0;
    private boolean State1;
    private boolean State2;
    private float Temperature;
    private float Humidity;
    private float Energy;
    private String Locale;
    private boolean State3;
    private boolean State4;
    private boolean State5;
    private boolean State6;
    private boolean State7;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int deviceID) {
        DeviceID = deviceID;
    }

    public boolean isState0() {
        return State0;
    }

    public void setState0(boolean state0) {
        State0 = state0;
    }

    public boolean isState1() {
        return State1;
    }

    public void setState1(boolean state1) {
        State1 = state1;
    }

    public boolean isState2() {
        return State2;
    }

    public void setState2(boolean state2) {
        State2 = state2;
    }

    public float getTemperature() {
        return Temperature;
    }

    public void setTemperature(float temperature) {
        Temperature = temperature;
    }

    public float getHumidity() {
        return Humidity;
    }

    public void setHumidity(float humidity) {
        Humidity = humidity;
    }

    public float getEnergy() {
        return Energy;
    }

    public void setEnergy(float energy) {
        Energy = energy;
    }

    public String getLocale() {
        return Locale;
    }

    public void setLocale(String locale) {
        Locale = locale;
    }

    public boolean isState3() {
        return State3;
    }

    public void setState3(boolean state3) {
        State3 = state3;
    }

    public boolean isState4() {
        return State4;
    }

    public void setState4(boolean state4) {
        State4 = state4;
    }

    public boolean isState5() {
        return State5;
    }

    public void setState5(boolean state5) {
        State5 = state5;
    }

    public boolean isState6() {
        return State6;
    }

    public void setState6(boolean state6) {
        State6 = state6;
    }

    public boolean isState7() {
        return State7;
    }

    public void setState7(boolean state7) {
        State7 = state7;
    }
}
