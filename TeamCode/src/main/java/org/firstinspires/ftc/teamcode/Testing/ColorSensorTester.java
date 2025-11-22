package org.firstinspires.ftc.teamcode.Testing;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.ColorSensor;

@TeleOp (group = "test", name = "colorSensor")
public class ColorSensorTester extends OpMode {
    ColorSensor colorSensor = new ColorSensor();
    @Override
    public void init() {
        colorSensor.init(hardwareMap);
    }

    @Override
    public void loop() {
        colorSensor.getColor();
        colorSensor.getData(telemetry);
    }



}
