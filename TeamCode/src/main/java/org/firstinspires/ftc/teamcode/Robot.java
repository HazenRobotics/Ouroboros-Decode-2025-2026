package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Modules.Camera;
import org.firstinspires.ftc.teamcode.Modules.Drivetrain;
import org.firstinspires.ftc.teamcode.Modules.Slides;

public class Robot {

    HardwareMap hardwareMap;

    Drivetrain drivetrain;
    Camera camera;

    public Robot (HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;

        drivetrain = new Drivetrain(hardwareMap);
        camera = hardwareMap.get(Camera.class, "camera");
    }

    public Drivetrain getDrivetrain(){
        return drivetrain;
    }
    public Camera getCamera() {
        return camera;
    }

}
