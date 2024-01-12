package org.firstinspires.ftc.teamcode.comp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.comp.DriveWithEncoders;
import org.firstinspires.ftc.teamcode.Interface.IDrive;

@Autonomous(name = "AutoRed", group = "competition")
@Config
public class AutoRed extends LinearOpMode {

    private IDrive _Drive;
    public static double DriveDistance = 24;
    public static double Power = .3;
    public static double StopDistance = 10;

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
            _Drive.Intake(IDrive.Direction.FORWARD,4000,.3);
            sleep(1000);
 //           _Drive.Straight(IDrive.Direction.BACKWARD,6,Power,StopDistance);

            // break will exit the loop for us
            break;
        }
    }

    private void Initialize(){
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        // Initialize driving
        _Drive = new DriveWithEncoders(hardwareMap, telemetry);
    }
}
