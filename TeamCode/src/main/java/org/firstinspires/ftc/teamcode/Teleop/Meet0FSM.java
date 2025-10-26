package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Modules.PIDController;
import org.firstinspires.ftc.teamcode.Robot;


@Config
@TeleOp(name = "Meet 0 FSM")
public class Meet0FSM extends LinearOpMode {

    Robot robot;

    boolean hasrun = false;





    public enum states{
        IDLE,
        INTAKE,
        OUTTAKE
    }

    public states currentState = states.IDLE;

    @Override
    public void runOpMode() throws InterruptedException {

        hasrun = false;
        telemetry.addData("Robot status:", "succesfully initiated");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        telemetry.clear();
        telemetry.addData("Robot status", "Started!");
        telemetry.update();

        while(opModeIsActive()){
            telemetry.addData("Current State:", currentState);
            telemetry.update();
            fieldCentricDrive();
        }
    }


    private void FSM(){
        switch(currentState){
            case IDLE:

                if (!hasrun){
                    //Do idle things
                    hasrun = true;
                }

                if (gamepad2.left_trigger > 0.1){
                    currentState = states.INTAKE;
                    hasrun = false;
                }

                if (gamepad2.left_trigger > 0.1){
                    currentState = states.OUTTAKE;
                    hasrun = false;
                }

                break;
            case INTAKE:
                if (!hasrun){
                    //Do intake setup
                    hasrun = true;
                }

                if (gamepad2.x){
                    currentState = states.OUTTAKE;
                    hasrun = false;
                }

                if (gamepad2.b){
                    currentState = states.IDLE;
                    hasrun = false;
                }

                break;
            case OUTTAKE:
                if (!hasrun){
                    //Do outtake setup
                    hasrun = true;
                }

                if (gamepad2.b){
                    currentState = states.IDLE;
                    hasrun = false;
                }

                if (gamepad1.right_bumper) {
                    lockOnTarget();
                }

                if (gamepad2.left_trigger > 0.1){
                    currentState = states.INTAKE;
                    hasrun = false;
                }


                break;
        }
    }

    private void fieldCentricDrive() {
        double slowdown = gamepad1.right_trigger > 0 ? 0.25 : 1;
        double y = -gamepad1.left_stick_y * slowdown;
        double x = gamepad1.left_stick_x * 1.1 * slowdown;
        double rx = gamepad1.right_stick_x * slowdown;

        double heading = robot.getDrivetrain().getRobotHeading(AngleUnit.RADIANS);


        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frWheelPower = (rotY - rotX - rx) / denominator;
        double flWheelPower = (rotY + rotX + rx) / denominator;
        double brWheelPower = (rotY + rotX - rx) / denominator;
        double blWheelPower = (rotY - rotX + rx) / denominator;

        robot.getDrivetrain().setWheelPowers(flWheelPower, frWheelPower, brWheelPower, blWheelPower);

        if (gamepad1.y) {
            robot.getDrivetrain().resetImu();
        }
    }

    private void lockOnTarget() {
        double goalAngle = robot.getCamera().getTagHorizontalAngle(robot.getCamera().getTagDetections().get(0)) * Math.PI / 180; // I literally don't know T-T
        PIDController turnPID = new PIDController(0.05, 0.1, 0.1); // these are random numbers
        turnPID.setDeadZone(0.1); // Also random, idk if this is low
        double calcPow = turnPID.calculate(0, goalAngle); // This feels wrong, someone tell me if it's wrong plz

        double rWheelPower = Math.min(calcPow, 1);
        double lWheelPower = Math.max(-1, -calcPow);

        robot.getDrivetrain().setWheelPowers(lWheelPower, rWheelPower, rWheelPower, lWheelPower);
    }
}
