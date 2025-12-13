package org.firstinspires.ftc.teamcode.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.LogitechCam;
import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "BLuePedroAuto")
public class RedPedroAuto extends LinearOpMode {
    private enum AutoMode { PPG, GPP, PGP }
    private AutoMode autoMode = null;
    private int pathState;
    private Timer actionTimer = new Timer();
    private int actionStep = 0;
    private boolean actionRunning = false;

    LogitechCam camera;
    StarterRobot robot;
    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    //Determine all the position by testing it out;
    //starting position
    private final Pose startPose = new Pose(61.77391304347826,4.173913043478263,Math.toRadians(90));
    //Shooting position
    private final Pose shootingPose = new Pose(60.2,19.199999999999996,Math.toRadians(112));
    //Farside 3 balls
    private final Pose firstLine = new Pose(41.321739130434786,35.686956521739134,Math.toRadians(0));
    private final Pose firstPush = new Pose(18.782608695652172,35.89565217391305,Math.toRadians(0));
    private final Pose firstControl = new Pose(72.83478260869565,38.19130434782609);
    //Middle 3 balls
    private final Pose secondLine = new Pose(40.904347826086955,60.313043478260866,Math.toRadians(0));
    private final Pose secondPush = new Pose(18.782608695652172,60.104347826086965,Math.toRadians(0));
    private final Pose secondControl = new Pose(72.20869565217392,65.5304347826087);
    //Last three balls
    private final Pose thirdLine = new Pose(40.48695652173913,84.31304347826088,Math.toRadians(0));
    private final Pose thirdPush = new Pose(18.782608695652172,83.89565217391304,Math.toRadians(0));
    private final Pose thirdControl = new Pose(75.96521739130435,90.57391304347826);
    private PathChain shoot, firstBall, push1, back1, secondBall, push2, back2, thirdBall, push3, back3;
    public void buildPaths(){
        shoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootingPose))
                .setConstantHeadingInterpolation(Math.toRadians(112))
                .build();

        firstBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, firstLine))
                .setLinearHeadingInterpolation(Math.toRadians(112),Math.toRadians(0))
                .addPath(new BezierLine(firstLine, firstPush))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back1 = follower.pathBuilder()
                .addPath(new BezierLine(firstPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(112))
                .build();

        secondBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, secondLine))
                .setLinearHeadingInterpolation(Math.toRadians(112),Math.toRadians(0))
                .addPath(new BezierLine(secondLine, secondPush))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back2 = follower.pathBuilder()
                .addPath(new BezierLine(secondPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(112))
                .build();

        thirdBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, thirdLine))
                .setLinearHeadingInterpolation(Math.toRadians(112),Math.toRadians(0))
                .addPath(new BezierLine(thirdLine, thirdPush))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

//        back3 = follower.pathBuilder()
//                .addPath(new BezierLine(thirdPush, shootingPose))
//                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(112))
//                .build();
    }

    @Override
    public void runOpMode() {

        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();
        camera = new LogitechCam();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);

        if (camera.getTagBySpecificId(23) != null) {
            autoMode = AutoMode.PPG;
        } else if (camera.getTagBySpecificId(21) != null) {
            autoMode = AutoMode.GPP;
        } else if (camera.getTagBySpecificId(22) != null) {
            autoMode = AutoMode.PGP;
        }

        telemetry.addData("Status", "Initialization Complete");
        telemetry.addData("Start Pose", startPose);
        telemetry.update();

        // Standard LinearOpMode method to wait for the Start button
        waitForStart();

        // 4. Main Autonomous Execution Loop (Replaces your old 'start' and 'loop' methods)
        if (opModeIsActive()) {
            opmodeTimer.resetTimer();
            setPathState(0); // Start the path sequence


            while (opModeIsActive() && !isStopRequested()) {
                follower.update();
                autonomousPathUpdate();

                // Telemetry
                telemetry.addData("path state", pathState);
                telemetry.addData("x", follower.getPose().getX());
                telemetry.addData("y", follower.getPose().getY());
                telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
                telemetry.update();
            }
        }


    }
    public void autonomousPathUpdate() {
        switch (pathState) {

            case 0: // Drive to shooting position
                follower.followPath(shoot);
                setPathState(1);
                break;

            case 1: // Wait until arrived
                if (!follower.isBusy()) {
                    setPathState(2);
                }
                break;

            case 2: // SORT + SHOOT (AFTER ARRIVAL)
                if (autoMode != null) {
                    switch (autoMode) {
                        case PPG: ppg(); break;
                        case GPP: gpp(); break;
                        case PGP: pgp(); break;
                    }
                }
                setPathState(3);
                break;

            case 3:
                follower.followPath(firstBall);
                setPathState(4);
                break;

            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(back1);
                    setPathState(5);
                }
                break;

            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(secondBall);
                    setPathState(6);
                }
                break;

            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(back2);
                    setPathState(7);
                }
                break;

            case 7:
                if (!follower.isBusy()) {
                    follower.followPath(thirdBall);
                    setPathState(8);
                }
                break;
        }
    }
    public void ppg() {
        double t = actionTimer.getElapsedTimeSeconds(); // seconds since last reset

        switch(actionStep) {
            case 0: // Start first shoot
                robot.shoot();
                actionTimer.resetTimer();
                actionStep++;
                break;

            case 1: // Wait for first shoot delay
                if (t > 3) { // LAUNCHER_DELAY
                    robot.shoot(); // second shoot
                    actionTimer.resetTimer();
                    actionStep++;
                }
                break;

            case 2: // Wait for second shoot delay
                if (t > 3) {
                    robot.load(); // load ball
                    actionTimer.resetTimer();
                    actionStep++;
                }
                break;

            case 3: // Wait for load delay
                if (t > 2) { // LOAD_DELAY
                    actionStep = 0;
                    actionRunning = false; // finished sequence
                }
                break;
        }
    }

    public void gpp() {
        double t = actionTimer.getElapsedTimeSeconds();

        switch(actionStep) {
            case 0:
                robot.transfer();
                actionTimer.resetTimer();
                actionStep++;
                break;

            case 1:
                if (t > 3) {
                    robot.transfer();
                    actionTimer.resetTimer();
                    actionStep++;
                }
                break;

            case 2:
                if (t > 3) {
                    robot.load();
                    actionTimer.resetTimer();
                    actionStep++;
                }
                break;

            case 3:
                if (t > 2) {
                    robot.load();
                    actionTimer.resetTimer();
                    actionStep++;
                }
                break;

            case 4:
                if (t > 2) {
                    robot.load();
                    actionStep = 0;
                    actionRunning = false;
                }
                break;
        }
    }

    public void pgp() {
        double t = actionTimer.getElapsedTimeSeconds();

        switch(actionStep) {
            case 0:
                robot.shoot();
                actionTimer.resetTimer();
                actionStep++;
                break;

            case 1:
                if (t > 3) {
                    robot.transfer();
                    actionTimer.resetTimer();
                    actionStep++;
                }
                break;

            case 2:
                if (t > 3) {
                    robot.load();
                    actionTimer.resetTimer();
                    actionStep++;
                }
                break;

            case 3:
                if (t > 2) {
                    robot.load();
                    actionStep = 0;
                    actionRunning = false;
                }
                break;
        }
    }




    public void setPathState(int pState) {
        pathState = pState;
        if (pathTimer != null) {
            pathTimer.resetTimer();
        }
    }
}