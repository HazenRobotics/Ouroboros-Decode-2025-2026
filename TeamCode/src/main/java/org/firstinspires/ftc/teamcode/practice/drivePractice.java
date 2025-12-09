//package org.firstinspires.ftc.teamcode.practice;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//@TeleOp(name = "driver")
//public class drivePractice extends LinearOpMode{
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
//
//        leftMotor.setPower(y);
//        rightMotor.setPower(y);
//
//        double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
//        double rx = gamepad1.right_stick_x;
//
//        leftMotor.setPower(y + rx);
//        rightMotor.setPower(y - rx);
//
//        double y = -gamepad1.left_stick_y;
//        double x = gamepad1.left_stick_x;
//        double rx = gamepad1.right_stick_x;
//
//        frontLeftMotor.setPower(y + x + rx);
//        backLeftMotor.setPower(y - x + rx);
//        frontRightMotor.setPower(y - x - rx);
//        backRightMotor.setPower(y + x - rx);
//        double y = -gamepad1.left_stick_y;
//        double x = gamepad1.left_stick_x * 1.1;
//        double rx = gamepad1.right_stick_x;
//
//
//        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//        double frontLeftPower = (y + x + rx) / denominator;
//        double backLeftPower = (y - x + rx) / denominator;
//        double frontRightPower = (y - x - rx) / denominator;
//        double backRightPower = (y + x - rx) / denominator;
//    }
//}