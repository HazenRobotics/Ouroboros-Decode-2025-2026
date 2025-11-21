package org.firstinspires.ftc.teamcode.SubSystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LED {
    RevBlinkinLedDriver led1, led2, led3;
    private final String name1 = "LED1", name2 = "LED2", name3 = "LED3";

    RevBlinkinLedDriver.BlinkinPattern[] lights = {
            RevBlinkinLedDriver.BlinkinPattern.WHITE,
            RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN,
            RevBlinkinLedDriver.BlinkinPattern.RED,
    };

    public LED(HardwareMap hw)
    {
        led1 = hw.get(RevBlinkinLedDriver.class, name1);
        led2 = hw.get(RevBlinkinLedDriver.class, name2);
        led3 = hw.get(RevBlinkinLedDriver.class, name3);

    }

    public void turnOn() {
         led1.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
         led2.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
         led3.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
    }

//    public void rangeToggle()
//    {
//        //If Arraylist 1, switch to 2, and vice versa
//        //Maybe use enums
//        led.setPattern();
//    }
}
