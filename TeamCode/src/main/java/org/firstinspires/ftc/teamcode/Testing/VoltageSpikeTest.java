package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

public class VoltageSpikeTest extends LinearOpMode {
    Intake intake;
    GamepadEvents controller;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = new Intake(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        waitForStart();
        while(opModeIsActive())
        {
            if(controller.left_bumper.onPress())
            {
                intake.intakeToggle(0.7);
            }


            telemetry.addData("Voltage", intake.getVoltage());

            telemetry.update();
            controller.update();


        }
    }
}
