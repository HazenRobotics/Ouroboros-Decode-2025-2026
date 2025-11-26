package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

import org.firstinspires.ftc.teamcode.utils.ColorSensor;
import org.firstinspires.ftc.teamcode.utils.LEDLights;

@TeleOp (group = "test", name = "colorSensor Test")
public class ColorSensorTester extends LinearOpMode {
    ColorSensor colorSensor;
    LEDLights lights[];
    @Override
    public void runOpMode() throws InterruptedException {
        lights = new LEDLights[3];
        lights[0] = new LEDLights(hardwareMap, "LED1");
        lights[1] = new LEDLights(hardwareMap, "LED2");
        lights[2] = new LEDLights(hardwareMap, "LED3");

        colorSensor = new ColorSensor(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            ColorSensor.Color color = colorSensor.getColor();

            switch (color){
                case Green:
                    lights[0].setColor(LEDLights.GREEN_WEIGHT);
                    lights[1].setColor(LEDLights.GREEN_WEIGHT);
                    lights[2].setColor(LEDLights.GREEN_WEIGHT);

                    break;
                case Purple:
                    lights[0].setColor(LEDLights.PURPLE_WEIGHT);
                    lights[1].setColor(LEDLights.PURPLE_WEIGHT);
                    lights[2].setColor(LEDLights.PURPLE_WEIGHT);

                    break;
                default:
                    lights[0].setColor(0);
                    lights[1].setColor(0);
                    lights[2].setColor(0);
                    break;
            }
            telemetry.addData("Pin0", colorSensor.getPin0().getState());
            telemetry.addData("Pin1", colorSensor.getPin1().getState());
            telemetry.update();

        }
    }

}
