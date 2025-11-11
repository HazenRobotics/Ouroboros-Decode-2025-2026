package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LogitechCam;
import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
import org.firstinspires.ftc.teamcode.SubSystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
@TeleOp(group = "A LeTeleOP", name = "Auto Align Plan")
public class TestAutoAlign extends LinearOpMode {
    GamepadEvents controller1, controller2;
    StarterRobot robot;
    LogitechCam camera;
    @Override
    public void runOpMode() throws InterruptedException {
        camera = new LogitechCam();
        robot = new StarterRobot(hardwareMap, controller1, controller2);
        camera.init(hardwareMap, telemetry);

        while (opModeInInit() && !isStopRequested()) {
            camera.update();
            AprilTagDetection blue = camera.getTagBySpecificId(20);
            AprilTagDetection tag21 = camera.getTagBySpecificId(21);
            AprilTagDetection tag22 = camera.getTagBySpecificId(22);
            AprilTagDetection tag23 = camera.getTagBySpecificId(23);
            AprilTagDetection red = camera.getTagBySpecificId(24);

            if (tag21 != null) {
                telemetry.addLine("Detected Tag ID: 21");
            } else if (tag22 != null) {
                telemetry.addLine("Detected Tag ID: 22");
            } else if (tag23 != null) {
                telemetry.addLine("Detected Tag ID: 23");
            }else {
                telemetry.addLine("No tag detected yet...");
            }

            telemetry.update();
        }

        while(opModeIsActive())
        {
            camera.update();

            // Decide action based on tag
            AprilTagDetection blue = camera.getTagBySpecificId(20);
            AprilTagDetection tag21 = camera.getTagBySpecificId(21);
            AprilTagDetection tag22 = camera.getTagBySpecificId(22);
            AprilTagDetection tag23 = camera.getTagBySpecificId(23);
            AprilTagDetection red = camera.getTagBySpecificId(24);


            if(blue != null)
            {
                 camera.disPlayDetectionTelementry(blue);
                double x = blue.ftcPose.x;
                double y = blue.ftcPose.y;
                double z = blue.ftcPose.z;

                //idk what the values are, test them out
                double x_offset = 70;
                double y_offset = 60;

                if (Math.abs(x) > x_offset) {
                    // Strafe left or right
                    if (x > 0) {

                        robot.drive(0, 0.2, 0);
                    } else {
                        robot.drive(0, -0.2, 0);
                    }
                    //drive forward
                } else if (z > y_offset + 0.05) {
                    robot.drive(0.2, 0, 0);
                } else if (z < y_offset - 0.05) {
                    robot.drive(-0.2, 0, 0);
                } else {
                    robot.drive(0,0,0);
                }
            } else {
                // idk for turning
                robot.drive(0,0,0.1);
            }
            }

        }
    }
}
