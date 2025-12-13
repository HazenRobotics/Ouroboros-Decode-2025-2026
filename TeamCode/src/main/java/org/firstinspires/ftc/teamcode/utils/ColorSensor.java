package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Testing.ColorSensorExample2;


public class ColorSensor {
    public enum Color {
        Green,
        Purple,
        None
    }
    DigitalChannel pin0, pin1;
    public ColorSensor(HardwareMap hw) {
        this(hw, "color", "colo1");
    }

    public ColorSensor(HardwareMap hw, String pin0Name, String pin1Name){
        this.pin0 = hw.digitalChannel.get(pin0Name);
        this.pin1 = hw.digitalChannel.get(pin1Name);
    }
    public Color getColor(){
        boolean col0 = this.pin0.getState();
        boolean col1 = this.pin1.getState();
// pin0 = purple
// pin1 = green
        if (col0){
            return Color.Green;
        }
        else if(col1){
            return Color.Purple;
        }
        return Color.None;
    }


}