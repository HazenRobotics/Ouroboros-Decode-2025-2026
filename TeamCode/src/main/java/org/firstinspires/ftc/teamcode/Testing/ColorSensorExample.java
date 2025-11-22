package org.firstinspires.ftc.teamcode.Testing;

import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

@TeleOp(name = "color (Blocks to Java)")
public class ColorSensorExample extends LinearOpMode {

    /**
     * This OpMode illustrates how to use a video source (camera) as a color sensor
     *
     * A "color sensor" will typically determine the color of the object that it is pointed at.
     *
     * This sample performs the same function, except it uses a video camera to inspect an object or scene.
     * The user may choose to inspect all, or just a Region of Interest (ROI), of the active camera view.
     * The user must also provide a list of "acceptable colors" (Swatches)
     * from which the closest matching color will be selected.
     *
     * To perform this function, a VisionPortal runs a PredominantColorProcessor process.
     * The PredominantColorProcessor process is created first,
     * and then the VisionPortal is built to use this process.
     * The PredominantColorProcessor analyses the ROI and splits the colored pixels into several color-clusters.
     * The largest of these clusters is then considered to be the "Predominant Color"
     * The process then matches the Predominant Color with the closest Swatch and returns that match.
     * The process also returns the actual Predominant Color in three different color spaces: RGB, HSV & YCrCb
     * Each returned color-space value has three components, in the following ranges:
     *    RGB   Red 0-255, Green 0-255, Blue 0-255
     *    HSV   Hue 0-180, Saturation 0-255, Value 0-255
     *    YCrCb Luminance(Y) 0-255, Cr 0-255 (center 128), Cb 0-255 (center 128)
     *
     * To aid the user, a colored rectangle is drawn on the camera preview to show the RegionOfInterest,
     * The Predominant Color is used to paint the rectangle border,
     * so the user can verify that the color is reasonable.
     */
    @Override
    public void runOpMode() {
        PredominantColorProcessor.Builder myPredominantColorProcessorBuilder;
        VisionPortal.Builder myVisionPortalBuilder;
        PredominantColorProcessor myPredominantColorProcessor;
        VisionPortal myVisionPortal;
        PredominantColorProcessor.Result myResult;

        // Build a "Color Sensor" vision processor based on the PredominantColorProcessor class.
        myPredominantColorProcessorBuilder = new PredominantColorProcessor.Builder();
        // - Focus the color sensor by defining a RegionOfInterest (ROI) which you want to inspect.
        //     This can be the entire frame, or a sub-region defined using:
        //     1) standard image coordinates or 2) a normalized +/- 1.0 coordinate system.
        //     Use one form of the ImageRegion class to define the ROI.
        // 10% width/height square centered on screen
        myPredominantColorProcessorBuilder.setRoi(ImageRegion.asUnityCenterCoordinates(-0.1, 0.1, 0.1, -0.1));
        // - Set the list of "acceptable" color swatches (matches).
        //     Only colors that you assign here will be returned.
        //     If you know the sensor will be pointing to one of a few specific colors, enter them here.
        //     Or, if the sensor may be pointed randomly, provide some additional colors that may match the surrounding.
        //     Note that in the example shown below, only some of the available colors are included.
        //     This will force any other colored region into one of these colors.
        //     eg: Green may be reported as YELLOW, as this may be the "closest" match.
        myPredominantColorProcessorBuilder.setSwatches(
                PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                PredominantColorProcessor.Swatch.RED,
                PredominantColorProcessor.Swatch.BLUE,
                PredominantColorProcessor.Swatch.YELLOW,
                PredominantColorProcessor.Swatch.BLACK,
                PredominantColorProcessor.Swatch.WHITE);
        myPredominantColorProcessor = myPredominantColorProcessorBuilder.build();
        // Build a vision portal to run the Color Sensor process.
        myVisionPortalBuilder = new VisionPortal.Builder();
        //  - Add the colorSensor process created above.
        myVisionPortalBuilder.addProcessor(myPredominantColorProcessor);
        //  - Set the desired video resolution.
        //      Since a high resolution will not improve this process, choose a lower resolution that is
        //      supported by your camera. This will improve overall performance and reduce latency.
        myVisionPortalBuilder.setCameraResolution(new Size(320, 240));
        //  - Choose your video source. This may be for a webcam or for a Phone Camera.
        myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam"));
        myVisionPortal = myVisionPortalBuilder.build();
        // Speed up telemetry updates, Just use for debugging.
        telemetry.setMsTransmissionInterval(50);
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        // WARNING:  To be able to view the stream preview on the Driver Station, this code runs in INIT mode.
        while (opModeIsActive() || opModeInInit()) {
            telemetry.addLine("Preview on/off: 3 dots, Camera Stream");
            telemetry.addLine("");
            // Request the most recent color analysis.
            // This will return the closest matching colorSwatch and the predominant color in RGB, HSV and YCrCb color spaces.
            myResult = myPredominantColorProcessor.getAnalysis();
            // Note: to take actions based on the detected color, simply use the colorSwatch or color space value in a comparison or switch.
            //  eg:
            //  or:
            // Display the Color Sensor result.
            telemetry.addData("Best Match", myResult.closestSwatch);
            telemetry.addLine("RGB   (" + JavaUtil.formatNumber(myResult.RGB[0], 3, 0) + ", " + JavaUtil.formatNumber(myResult.RGB[1], 3, 0) + ", " + JavaUtil.formatNumber(myResult.RGB[2], 3, 0) + ")");
        }

    }
}