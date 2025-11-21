package org.firstinspires.ftc.teamcode.Practice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.LED;
@TeleOp(name = "LED")
public class ledTesting extends LinearOpMode {
    LED moreLED;
    @Override
    public void runOpMode() throws InterruptedException {
          moreLED = new LED(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            moreLED.turnOn();
        }
    }
}
