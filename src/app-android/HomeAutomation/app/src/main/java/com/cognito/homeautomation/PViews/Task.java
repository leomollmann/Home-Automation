package com.cognito.homeautomation.PViews;

public class Task {
    float StartH;
    float StartM;
    float EndH;
    float EndM;
    float RepeatH;
    float RepeatM;
    int Type;

    Task(String Start,String End,String Repeat) {
        if(Start != null){
            this.StartH = Integer.parseInt(Start.substring(0, 2));
            this.StartM = Integer.parseInt(Start.substring(3, 5));
        }
        if(End != null){
            this.EndH = Integer.parseInt(End.substring(0, 2));
            this.EndM = Integer.parseInt(End.substring(3, 5));
        }
        if(Repeat != null){
            this.RepeatH = Integer.parseInt(Repeat.substring(0, 2));
            this.RepeatM = Integer.parseInt(Repeat.substring(3, 5));
        }
        int type = 1;
        if (End != null) type = 2;
        else if (Repeat != null) type = 3;
        this.Type = type;
    }
}