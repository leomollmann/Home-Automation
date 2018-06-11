package com.cognito.homeautomation.Holders;

public class IDHolder {
    private static IDHolder holder = null;
    public int ID;

    public static IDHolder getHolder(){
        if(holder == null){
            holder = new IDHolder();
        }
        return holder;
    }
}
