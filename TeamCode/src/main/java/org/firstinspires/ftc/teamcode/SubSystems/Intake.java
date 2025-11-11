package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class Intake {
    private String name = "intake";
    DcMotorEx intake;
    private VoltageSensor voltageSensor;
    private double intakePow;
    public Intake(HardwareMap hw)
    {
        intake = hw.get(DcMotorEx.class, name);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        voltageSensor = hw.voltageSensor.iterator().next();
    }
    public Intake(HardwareMap hw, String name)
    {
        intake = hw.get(DcMotorEx.class, name);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        voltageSensor = hw.voltageSensor.iterator().next();
    }

    public void setPower(double power)
    {
        intake.setPower(power);
    }
    public void intakeToggle(double power){
        intakePow = (intakePow == power) ? 0: power;
        intake.setPower(intakePow);
    }

    public double getVoltage()
    {
        return voltageSensor.getVoltage();
    }

    public double getPower()
    {
        return intake.getPower();
    }
    public String getData() {
        return "Intake power: " + intake.getPower();
    }

}
