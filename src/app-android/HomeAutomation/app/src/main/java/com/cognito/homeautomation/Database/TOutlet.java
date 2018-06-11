package com.cognito.homeautomation.Database;

public class TOutlet {

    private int DeviceID;
    private String Name;
    private boolean State0;
    private boolean State1;
    private boolean State2;
    private float Temperature;
    private float Humidity;
    private float Energy;
    private String Locale;
    private String Plug1;
    private String Plug2;
    private String Plug3;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    private int UserID;

    public String getPlug1() {
        return Plug1;
    }

    public void setPlug1(String plug1) {
        Plug1 = plug1;
    }

    public String getPlug2() {
        return Plug2;
    }

    public void setPlug2(String plug2) {
        Plug2 = plug2;
    }

    public String getPlug3() {
        return Plug3;
    }

    public void setPlug3(String plug3) {
        Plug3 = plug3;
    }

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
}
