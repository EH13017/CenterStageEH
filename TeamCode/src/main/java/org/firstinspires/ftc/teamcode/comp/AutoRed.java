package org.firstinspires.ftc.teamcode.comp;

import android.util.Log;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.Coach.RobotCameraHandler;
import org.firstinspires.ftc.teamcode.Coach.RobotHardwareMap;
import org.firstinspires.ftc.teamcode.Coach.SpikeLocationDetectionPipeline;
import org.firstinspires.ftc.teamcode.comp.DriveWithEncoders;
import org.firstinspires.ftc.teamcode.Interface.IDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.concurrent.atomic.AtomicBoolean;

@Autonomous(name = "AutoRed", group = "competition")
@Config
public class AutoRed extends LinearOpMode {

    private IDrive _Drive;
    public static double DriveDistance = 24;
    public static double Power = .3;
    public static double StopDistance = 10;
    public OpenCvCamera frontCVCCamera;

    @Override
    public void runOpMode() throws InterruptedException {

        Initialize();

        telemetry.addData("=D", "Ready to start!");
        telemetry.update();

        waitForStart();


        while (opModeIsActive()) {

            // Drive Forward for 24 inches or until 10cm from something
            /*
            [Blue/Red] [A=Crowd Side/B=Stage Side] [1=First Tile/2=Second Tile]
            Blue A1 = DD = 1.75 / P = .18 | Turn 90D Left | DD = 106 / P = .25
            Blue A2 = DD = 25.5 / P = .18 | Turn 90D Left | DD = 106 / P = .25

             */


            _Drive.Straight(IDrive.Direction.FORWARD,DriveDistance,Power,StopDistance);
            sleep(500);
            _Drive.StrafeLeft(6000,.4);
            sleep(500);
//            _Drive.Intake(IDrive.Direction.FORWARD,4000,.35);
//            sleep(1000);
 //           _Drive.Straight(IDrive.Direction.BACKWARD,6,Power,StopDistance);

            // break will exit the loop for us
            break;
        }
    }

    RobotHardwareMap robotHardwareMap;
    public AtomicBoolean frontCameraIsFaulty = new AtomicBoolean(false);
    SpikeLocationDetectionPipeline spikeLocationDetectionPipeline;
    public AtomicBoolean frontCameraIsOpen = new AtomicBoolean(false);
    LinearOpMode opMode;

    public void Initialize(){
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        // Initialize driving
        _Drive = new DriveWithEncoders(hardwareMap, telemetry);

        //====== Create 1 viewports
        int cameraMonitorViewId = robotHardwareMap.baseHMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", robotHardwareMap.baseHMap.appContext.getPackageName());

        //split for both camera
        int[] viewportContainerIds = OpenCvCameraFactory.getInstance()
                .splitLayoutForMultipleViewports(
                        cameraMonitorViewId, //The container we're splitting
                        2, //The number of sub-containers to create
                        OpenCvCameraFactory.ViewportSplitMethod.HORIZONTALLY);

        //frontCVCCamera = OpenCvCameraFactory.getInstance().createWebcam(RobotHardwareMap.frontCamera, viewportContainerIds[1]);
        frontCVCCamera.showFpsMeterOnViewport(false);

        frontCameraIsFaulty.set(false);
        frontCVCCamera.openCameraDeviceAsync(frontCameraOpenListener);

    }


    /**
     * Async camera listener for front camera
     */
    OpenCvCamera.AsyncCameraOpenListener frontCameraOpenListener
            = new OpenCvCamera.AsyncCameraOpenListener() {
        @Override
        public void onOpened() {
            spikeLocationDetectionPipeline = new SpikeLocationDetectionPipeline(opMode.telemetry);
            frontCVCCamera.setPipeline(spikeLocationDetectionPipeline);
            frontCVCCamera.startStreaming(
                    320,
                    240,
                    OpenCvCameraRotation.UPRIGHT);
            frontCameraIsOpen.set(true);
        }

        @Override
        public void onError(int errorCode) {
            /*
             * This will be called if the camera could not be opened
             */
            frontCameraIsFaulty.set(true);
        }
    };


}
