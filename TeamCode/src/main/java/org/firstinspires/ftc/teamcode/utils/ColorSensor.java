package org.firstinspires.ftc.teamcode.utils;

import android.util.Size;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

import java.util.HashMap;
import java.util.Map;

public class ColorSensor {
    PredominantColorProcessor.Builder myPredominantColorProcessorBuilder;
    VisionPortal.Builder myVisionPortalBuilder;
    PredominantColorProcessor myPredominantColorProcessor;
    VisionPortal myVisionPortal;
    PredominantColorProcessor.Result myResult;
    private static final Map<Double, int[]> colorScale = new HashMap<>();
    static {
        colorScale.put(0.0, new int[]{0, 0, 0});        // Black
        colorScale.put(0.277, new int[]{255, 0, 0});    // Red
        colorScale.put(0.333, new int[]{255, 128, 0});  // Orange
        colorScale.put(0.388, new int[]{255, 255, 0});  // Yellow
        colorScale.put(0.444, new int[]{128, 255, 0});  // Sage
        colorScale.put(0.500, new int[]{0, 255, 0});    // Green
        colorScale.put(0.555, new int[]{0, 255, 128});  // Azure
        colorScale.put(0.611, new int[]{0, 255, 255});  // Blue
        colorScale.put(0.666, new int[]{128, 0, 255});  // Indigo
        colorScale.put(0.722, new int[]{255, 0, 255});  // Violet
        colorScale.put(1.0, new int[]{255, 255, 255});  // White
    }

    public void init(HardwareMap hardwareMap){
        myPredominantColorProcessorBuilder = new PredominantColorProcessor.Builder();
        myPredominantColorProcessorBuilder.setRoi(ImageRegion.asUnityCenterCoordinates(-0.1, 0.1, 0.1, -0.1));

        myPredominantColorProcessorBuilder.setSwatches(
                PredominantColorProcessor.Swatch.ARTIFACT_GREEN,
                PredominantColorProcessor.Swatch.ARTIFACT_PURPLE,
                PredominantColorProcessor.Swatch.RED,
                PredominantColorProcessor.Swatch.BLUE,
                PredominantColorProcessor.Swatch.YELLOW,
                PredominantColorProcessor.Swatch.BLACK,
                PredominantColorProcessor.Swatch.WHITE);

        myPredominantColorProcessor = myPredominantColorProcessorBuilder.build();
        myVisionPortalBuilder = new VisionPortal.Builder();
        myVisionPortalBuilder.addProcessor(myPredominantColorProcessor);
        myVisionPortalBuilder.setCameraResolution(new Size(320, 240));
        myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "ColorSensor"));
        myVisionPortal = myVisionPortalBuilder.build();

    }
    public PredominantColorProcessor.Result getColorAnalysis() {
        return myPredominantColorProcessor.getAnalysis();
    }
    public double getColor() {
        return rgbToFTC(myResult.RGB);
    }
    public static double rgbToFTC(int[] rgb) {
        // Normalize input RGB to [0, 1]
        double r = rgb[0] / 255.0;
        double g = rgb[1] / 255.0;
        double b = rgb[2] / 255.0;
        double minDistance = Double.MAX_VALUE;
        double closestFTC = 0.0;
        // Iterate over the predefined color scale
        for (Map.Entry<Double, int[]> entry : colorScale.entrySet()) {
            double ftc = entry.getKey();
            int[] referenceRGB = entry.getValue();
            // Normalize reference RGB to [0, 1]
            double rRef = referenceRGB[0] / 255.0;
            double gRef = referenceRGB[1] / 255.0;
            double bRef = referenceRGB[2] / 255.0;
            // Compute Euclidean distance
            double distance = Math.sqrt(Math.pow(r - rRef, 2) + Math.pow(g - gRef, 2) + Math.pow(b - bRef, 2));
            // Update closest FTC if this color is nearer
            if (distance < minDistance) {
                minDistance = distance;
                closestFTC = ftc;
            }
        }
        return closestFTC;
    }

    public void getData(Telemetry telemetry) {
        telemetry.addData("Best Match", myResult.closestSwatch);
        telemetry.addLine("RGB   (" + JavaUtil.formatNumber(myResult.RGB[0], 3, 0) + ", " + JavaUtil.formatNumber(myResult.RGB[1], 3, 0) + ", " + JavaUtil.formatNumber(myResult.RGB[2], 3, 0) + ")");
        telemetry.addLine("HSV   (" + JavaUtil.formatNumber(myResult.HSV[0], 3, 0) + ", " + JavaUtil.formatNumber(myResult.HSV[1], 3, 0) + ", " + JavaUtil.formatNumber(myResult.HSV[2], 3, 0) + ")");
        telemetry.addLine("YCrCb (" + JavaUtil.formatNumber(myResult.YCrCb[0], 3, 0) + ", " + JavaUtil.formatNumber(myResult.YCrCb[1], 3, 0) + ", " + JavaUtil.formatNumber(myResult.YCrCb[2], 3, 0) + ")");
    }
}

