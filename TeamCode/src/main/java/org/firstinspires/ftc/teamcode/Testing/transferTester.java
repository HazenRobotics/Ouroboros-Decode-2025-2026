package org.firstinspires.ftc.teamcode.Testing;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.Transfer;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@TeleOp(group = "A LeTeleOp", name = "LeFlap Test")

public class transferTester extends LinearOpMode{
    GamepadEvents controller1;
    Transfer transfer;
    @Override
    public void runOpMode() throws InterruptedException {
        controller1 = new GamepadEvents(gamepad1);
        transfer = new Transfer(hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            transfer.setServo(-1);
        }
    }
}
