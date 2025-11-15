package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.LEDLights;

import java.util.Map;

@TeleOp(name="LED Lights Tester", group="Tests")
public class LEDTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        LEDLights LED1 = new LEDLights(hardwareMap, "LED1");
//        LEDLights LED2 = new LEDLights(hardwareMap, "LED2");
//        LEDLights LED3 = new LEDLights(hardwareMap, "LED3");

        // Convert the static colorScale map into an array for easy stepping
//        Map<Double, int[]> colorMap = LEDLights.getColorScale();
//        Double[] ftcPositions = colorMap.keySet().toArray(new Double[0]);
//
//        int index = 0;

        telemetry.addLine("LED Lights Tester Loaded");
        telemetry.addLine("Press A = Next Color");
        telemetry.addLine("Press B = Previous Color");
        telemetry.addLine("Press X = Show Random RGB Test");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            LED1.setColor(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            // NEXT COLOR
//            if (gamepad1.a) {
//                index = (index + 1) % ftcPositions.length;
//                sleep(200);
//            }
//
//            // PREVIOUS COLOR
//            if (gamepad1.b) {
//                index = (index - 1 + ftcPositions.length) % ftcPositions.length;
//                sleep(200);
//            }
//
//            double servoPos = ftcPositions[index];
//            int[] rgb = colorMap.get(servoPos);
//
//            LED1.setColor(servoPos);
//
//            telemetry.addLine("=== Preset Color Test ===");
//            telemetry.addData("Index", index);
//            telemetry.addData("Servo Position", servoPos);
//            telemetry.addData("RGB", "%d, %d, %d", rgb[0], rgb[1], rgb[2]);
//
//            // RANDOM RGB TEST (nearest match)
//            if (gamepad1.x) {
//                int[] randomRGB = new int[]{
//                        (int) (Math.random() * 256),
//                        (int) (Math.random() * 256),
//                        (int) (Math.random() * 256)
//                };
//
//                double nearest = LEDLights.rgbToFTC(randomRGB);
//                LED1.setColor(nearest);
//
//
//                telemetry.addLine("\n=== Nearest RGB (X pressed) ===");
//                telemetry.addData("Input RGB", "%d, %d, %d",
//                        randomRGB[0], randomRGB[1], randomRGB[2]);
//                telemetry.addData("Nearest Servo Position", nearest);
//                int[] nearestRGB = colorMap.get(nearest);
//                telemetry.addData("Matched RGB", "%d, %d, %d",
//                        nearestRGB[0], nearestRGB[1], nearestRGB[2]);
//            }

            telemetry.update();
        }
    }
}
