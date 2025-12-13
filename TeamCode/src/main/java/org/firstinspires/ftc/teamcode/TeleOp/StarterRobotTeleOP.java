package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.LogitechCam;
import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(group = "A", name = "LeStarter Robot TeleOP")
public class StarterRobotTeleOP extends LinearOpMode {

    private StarterRobot robot;
    private GamepadEvents controller1, controller2;

    // Pedro + Vision
    private Follower follower;
    private LogitechCam vision;

    // ===== AUTO ALIGN CONFIG =====
    private static final int TARGET_TAG_ID = 20;
    private static final double ALIGN_P = 0.015;      // degree-based
    private static final double ALIGN_TOLERANCE = 1.0;
    private static final double MAX_TURN = 0.6;

    @Override
    public void runOpMode() throws InterruptedException {

        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);

        robot = new StarterRobot(hardwareMap, controller1, controller2);
        Shooter shooter = new Shooter(hardwareMap, "leftShooter");

        // PedroPathing init
        follower = Constants.createFollower(hardwareMap);
        follower.startTeleopDrive();

        // Vision init
        vision = new LogitechCam();
        vision.init(hardwareMap, telemetry);

        telemetry.addLine("Initialized");
        telemetry.addLine("Hold Y to Auto-Align");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            // ================= DRIVE + AUTO ALIGN =================
            vision.update();
            AprilTagDetection tag = vision.getTagBySpecificId(TARGET_TAG_ID);
            boolean autoAlign = gamepad1.y;

            if (autoAlign && tag != null) {

                double yawError = tag.ftcPose.yaw; // degrees

                if (Math.abs(yawError) < ALIGN_TOLERANCE) {
                    follower.setTeleOpDrive(0, 0, 0, true);
                } else {
                    double turn = ALIGN_P * yawError;
                    turn = Math.max(-MAX_TURN, Math.min(MAX_TURN, turn));
                    follower.setTeleOpDrive(0, 0, turn, true);
                }

                telemetry.addData("Auto Align", "ACTIVE");
                telemetry.addData("Yaw Error", yawError);

            } else if (autoAlign) {
                // Y held but tag not visible
                follower.setTeleOpDrive(0, 0, 0, true);
                telemetry.addLine("⚠️ Tag Not Detected");

            } else {
                // Normal Pedro TeleOp drive
                follower.setTeleOpDrive(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x,
                        -gamepad1.right_stick_x,
                        true
                );
            }

            // ================= DRIVER 1 =================
            if (controller1.left_bumper.onPress()) {
                robot.intake();
            }

            if (controller1.right_bumper.onPress()) {
                robot.shoot();
            }

            if (controller1.b.onPress()) {
                robot.transfer();
            }

            if (controller1.a.onPress()) {
                robot.reverseTransfer();
            }

            if (controller1.x.onPress()) {
                robot.load();
            }

            // ================= DRIVER 2 =================
            if (controller2.a.onPress()) {
                robot.setLauncher();
            }

            if (controller2.x.onPress()) {
                robot.setFeeder();
            }

            if (controller2.b.onPress()) {
                robot.setLauncherToZero();
            }

            if (controller2.y.onPress()) {
                robot.setFeederToZero();
            }

            if (controller2.dpad_up.onPress()) {
                robot.increaseV();
            }

            if (controller2.dpad_down.onPress()) {
                robot.decreaseV();
            }

            // ================= SUBSYSTEM UPDATES =================
            robot.updateReverseTransfer();
            robot.updateLoad();
            robot.updateTransfer();

            controller1.update();
            controller2.update();

            // ================= TELEMETRY =================
            telemetry.addLine("Y: Auto Align (HOLD)");
            telemetry.addLine("LB: Intake | RB: Shoot");
            telemetry.addLine("B: Transfer | X: Load | A: Reverse");
            telemetry.addData("Shooter Voltage", shooter.getVoltageNormalizedVelocity(1800));
            telemetry.addData("Voltage", shooter.getVoltage());
            telemetry.addData("Velocity", shooter.getVelocity());

            if (tag != null) {
                vision.disPlayDetectionTelementry(tag);
            }

            telemetry.update();

            // IMPORTANT: Pedro update LAST
            follower.update();
            idle();
        }

        vision.stop();
    }
}
