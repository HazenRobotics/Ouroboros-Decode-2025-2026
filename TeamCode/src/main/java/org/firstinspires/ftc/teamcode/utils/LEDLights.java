package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Color;
import android.os.Build;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Map;

public class LEDLights {
    Servo LED;
    String stringColor;
    Telemetry telemetry;

    public LEDLights(HardwareMap hw, String name) {
        LED = hw.get(Servo.class, name);
    }

    public LEDLights(HardwareMap hw, Telemetry t) {
        this(hw, "light");
        telemetry = t;
    }

    public void setColor(double color, String theColor) {
        LED.setPosition(color);
        stringColor = theColor;
    }
}