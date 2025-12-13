package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Flap {
    Servo frontFlap, backFlap;
    private double position;
    private double frontPosition;
    private double backPosition;
    private double backStop = 0.406;
    private double backDown = 0.2995;
    private double frontDown = 0.4434;
    private double frontUp = 0.222;
    public Flap(HardwareMap hw, String frontName, String backName){
        frontFlap = hw.get(Servo.class, frontName);
        backFlap = hw.get(Servo.class, backName);
    }
    public void setFrontFlap(double position){
        frontFlap.setPosition(position);
    }

    public void setBackFlap(double position){
        backFlap.setPosition(position);
    }
    public double getPosition(){
        return position;
    }

    public void frontBlock(){
        frontFlap.setPosition(frontDown);
    }
    public void frontGo(){
        frontFlap.setPosition(frontUp);
    }
    public void backBlock(){
        backFlap.setPosition(backStop);
    }
    public void backDown(){
        backFlap.setPosition(backDown);
    }
    public void frontToggle(){
        frontPosition = (frontPosition == frontUp) ? frontDown : frontUp;
        frontFlap.setPosition(frontPosition);
    }
    public void backToggle(){
        backPosition = (backPosition == backStop) ? backDown: backStop;
        backFlap.setPosition(backPosition);
    }
    public double getFrontPosition(){
        return frontFlap.getPosition();
    }
    public double getBackPosition(){
        return backFlap.getPosition();
    }
    public void adjustPosition(double increment)
    {
        position += increment*0.0005;
        frontFlap.setPosition(position);
        backFlap.setPosition(position);
    }
}
