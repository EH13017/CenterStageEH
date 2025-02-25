package org.firstinspires.ftc.teamcode.Charlie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Coach.DriveWithEncoders;
import org.firstinspires.ftc.teamcode.Interface.IDrive;

@Autonomous(name = "Charlie: Autofrontblueleft", group = "Charlie")
public class Autofrontblueleft extends LinearOpMode {

    private IDrive _Drive;

    @Override
    public void runOpMode() throws InterruptedException {

        Initialize();

        telemetry.addData("=D", "Ready to start!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            /// Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.FORWARD, 28, .3, 10);

            // Turn 90 degrees to the left
            _Drive.Left(90, .3);

            // Reverse intake to push out pixels
            _Drive.Intake(IDrive.Direction.BACKWARD, 4000, .5);

            _Drive.StrafeLeft(3000,.3);

            // Drive Forward for 60 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.FORWARD, 36, .5, 10);

            // break will exit the loop for us
            break;
        }
    }

    private void Initialize(){
        // Initialize driving
        _Drive = new DriveWithEncoders(hardwareMap, telemetry);
    }
}