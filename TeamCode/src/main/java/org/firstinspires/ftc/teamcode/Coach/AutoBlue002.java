package org.firstinspires.ftc.teamcode.Coach;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Interface.IDrive;
@Disabled
@Autonomous(name = "Coach: AutoBlue002", group = "Coach")
public class AutoBlue002 extends LinearOpMode {

    private IDrive _Drive;

    @Override
    public void runOpMode() throws InterruptedException {

        Initialize();

        telemetry.addData("=D", "Ready to start!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.FORWARD, 24, .5, 10);

            // Turn 90 degrees to the left
            _Drive.Left(90, .25);

            // Drive Forward for 60 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.FORWARD, 60, .5, 10);

            // break will exit the loop for us
            break;
        }
    }

    private void Initialize(){
        // Initialize driving
        _Drive = new DriveWithEncoders(hardwareMap, telemetry);
    }
}
