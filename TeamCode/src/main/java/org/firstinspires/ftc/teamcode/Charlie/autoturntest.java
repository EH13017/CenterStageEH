package org.firstinspires.ftc.teamcode.Charlie;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Coach.DriveWithEncoders;
import org.firstinspires.ftc.teamcode.Interface.IDrive;
@Disabled
@Autonomous(name = "autoturntest", group = "competition")
@Config
public class autoturntest extends LinearOpMode {

    private IDrive _Drive;

    public static int Degrees = 89;
//    public static double DriveDistance = 24;
//    public static double Power = .25;
//    public static double StopDistance = 10;

    @Override
    public void runOpMode() throws InterruptedException {

        Initialize();

        telemetry.addData("=D", "Ready to start!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

//            _Drive.Straight(IDrive.Direction.FORWARD, DriveDistance, Power, StopDistance);
//            sleep(1000);

            // break will exit the loop for us
            // Turn 90 degrees to the left

//            _Drive.Left(Degrees, .25);

            //TODO: uncomment when working on right.
            _Drive.Right(Degrees, .25);

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
