package com.cognito.homeautomation.PViews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;

import com.cognito.homeautomattion.R;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class TaskWheel extends PApplet {
    Task TaskObj;
    PImage img;
    PGraphics Mask;
    boolean taskCreated;
    private int scale = 10;
    private int size = 108*scale;
    private int innerRadi;
    private int centerRadi;
    private int textRadi;
    private int outerRadi;
    private int timeBarSize;
    private int Xo;
    private int Yo;

    public void settings() {
        size(size, size);
    }

    public void setup() {
        Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.stripes);
        img = new PImage(icon);
        textSize(30);
        textAlign(CENTER);
        TaskObj = new Task("17:00","23:00",null);
        Mask = createGraphics(size, size, JAVA2D);
        innerRadi = 39*scale;
        centerRadi = 33*scale;
        textRadi = 36*scale;
        outerRadi = 60*scale;
        timeBarSize = 20*scale;
        Xo = size/2;
        Yo = size/3;
    }

    public void draw() {
        noStroke();
        background(13,13,13);
        fill(50);
        ellipse(Xo,Yo,outerRadi,outerRadi);
        switch(TaskObj.Type){
            case 2:{
                float Start = (((((TaskObj.StartH)*60)+TaskObj.StartM)/1440)*TWO_PI+HALF_PI);
                float End = ((((TaskObj.EndH*60)+TaskObj.EndM)/1440)*TWO_PI+HALF_PI);
                float StartXRatio = cos(Start);
                float StartYRatio = sin(Start);
                float EndXRatio = cos(End);
                float EndYRatio = sin(End);
                Mask.beginDraw();
                Mask.arc(Xo,Yo,outerRadi,outerRadi,Start,End);
                Mask.endDraw();
                img.mask(Mask);
                image(img,0,0);
                stroke(100,0,200);
                strokeWeight(3);
                fill(100,0,255,0);
                arc(Xo,Yo,outerRadi,outerRadi,Start,End);
                noStroke();
                fill(100,80,255);
                arc(Xo,Yo,innerRadi,innerRadi,Start,End);
                stroke(100,0,255);
                strokeWeight(3);
                line(55*StartXRatio+Xo,55*StartYRatio+Yo,textRadi*StartXRatio+Xo,textRadi*StartYRatio+Yo);
                line(55*EndXRatio+Xo,55*EndYRatio+Yo,textRadi*EndXRatio+Xo,textRadi*EndYRatio+Yo);
                strokeWeight(1);
                String startH = "", startM = "", endH = "", endM = "";
                if(TaskObj.StartH < 10.0) startH = "0"; startH += round(TaskObj.StartH);
                if(TaskObj.StartM < 10.0) startM = "0"; startM += round(TaskObj.StartM);
                if(TaskObj.EndH < 10.0) endH = "0"; endH += round(TaskObj.EndH);
                if(TaskObj.EndM < 10.0) endM = "0"; endM += round(TaskObj.EndM);
                text(startH+":"+startM,innerRadi*StartXRatio+Xo,innerRadi*StartYRatio+Yo);
                text(endH+":"+endM,innerRadi*EndXRatio+Xo,innerRadi*EndYRatio+Yo);
                noStroke();
                break;
            }
        }
        fill(13,13,13);
        ellipse(Xo,Yo,centerRadi,centerRadi);
    }

    public void updateValues(String Start,String End,String Repeat){
        TaskObj = new Task(Start,End,Repeat);
        taskCreated = true;
    }
}
