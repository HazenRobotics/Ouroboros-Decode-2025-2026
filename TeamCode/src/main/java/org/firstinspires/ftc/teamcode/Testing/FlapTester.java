package org.firstinspires.ftc.teamcode.Testing;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.Flap;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@TeleOp(group = "A LeTeleOp", name = "LeFlap Test")
public class FlapTester extends LinearOpMode {
    GamepadEvents controller1;
    Flap flap;
    @Override
    public void runOpMode() throws InterruptedException {
        controller1 = new GamepadEvents(gamepad1);
        flap = new Flap(hardwareMap, "frontFlap", "backFlap");
        waitForStart();
        while(opModeIsActive()){
            flap.adjustPosition(controller1.left_trigger.getTriggerValue() - controller1.right_trigger.getTriggerValue());
            controller1.update();
            telemetry.addData("Flap position:", flap.getPosition());
            telemetry.addLine("trigger to adjust position of flap");
            telemetry.update();
        }
    }

}
