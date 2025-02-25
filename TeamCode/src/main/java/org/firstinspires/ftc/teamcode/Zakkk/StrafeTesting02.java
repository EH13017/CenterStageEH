package org.firstinspires.ftc.teamcode.Zakkk;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Coach.DriveWithEncoders;
import org.firstinspires.ftc.teamcode.Interface.IDrive;

@Autonomous(name = "StrafeTesting02", group = "competition")
@Config
@Disabled
public class StrafeTesting02 extends LinearOpMode {

    private IDrive _Drive;
    public static double DriveDistance = 24;
    public static double DriveDistance2 = 5;
    public static double Power = .25;
    public static double StopDistance = 10;
    public static double StrafeDistance = 6;
    public static double IntakeDistance = 5;

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
//            _Drive.Straight(IDrive.Direction.BACKWARD,DriveDistance,Power,StopDistance);
//            sleep(1000);




            // 1 = 46.5, 2 = 42, 3 = 45, 4 = 46, 5 =

            _Drive.StrafeRight(6,Power);
            sleep(1000);

//            _Drive.Intake(IDrive.Direction.FORWARD,IntakeDistance,Power,StopDistance);
//            sleep(1000);
//
//            _Drive.Straight(IDrive.Direction.BACKWARD,DriveDistance2,Power,StopDistance);
//            sleep(1000);

            // break will exit the loop for us
            break;
        }
    }

    private void Initialize(){
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        // Initialize driving
        //_Drive = new StrafeTesting01(hardwareMap, telemetry);
    }
}
