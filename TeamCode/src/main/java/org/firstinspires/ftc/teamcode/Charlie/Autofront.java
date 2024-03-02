package org.firstinspires.ftc.teamcode.Charlie;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Coach.DriveWithEncoders;
import org.firstinspires.ftc.teamcode.Interface.IDrive;
@Disabled
@Autonomous(name = "Charlie: Autofront", group = "Charlie")
public class Autofront extends LinearOpMode {

    private IDrive _Drive;

    @Override
    public void runOpMode() throws InterruptedException {

        Initialize();

        telemetry.addData("=D", "Ready to start!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.BACKWARD, 24, .5, 10);

            // Turn 90 degrees to the left
            _Drive.Left(90, .25);

            // Reverse intake to push out pixels
            _Drive.Intake(IDrive.Direction.BACKWARD,4000,.35);

            // Drive Forward for 60 inches or until 10cm from something
//            _Drive.Straight(IDrive.Direction.FORWARD, 60, .5, 10);

            // break will exit the loop for us
            break;
        }

        while (opModeIsActive()) {

            // Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.BACKWARD, 24, .5, 10);


            // Reverse intake to push out pixels
            _Drive.Intake(IDrive.Direction.BACKWARD,4000,.35);

            // Turn 90 degrees to the left
            _Drive.Left(90, .25);


            // Drive Forward for 60 inches or until 10cm from something
//            _Drive.Straight(IDrive.Direction.FORWARD, 60, .5, 10);

            // break will exit the loop for us
            break;
        }


        while (opModeIsActive()) {

            // Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.BACKWARD, 24, .5, 10);

            // Turn 90 degrees to the left
            _Drive.Left(270, .25);

            // Reverse intake to push out pixels
            _Drive.Intake(IDrive.Direction.BACKWARD,4000,.35);



            // Drive Forward for 60 inches or until 10cm from something
//            _Drive.Straight(IDrive.Direction.FORWARD, 60, .5, 10);

            // break will exit the loop for us
            break;
        }














        while (opModeIsActive()) {

            // Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.BACKWARD, 24, .5, 10);

            // Turn 90 degrees to the left
            _Drive.Left(90, .25);

            // Reverse intake to push out pixels
            _Drive.Intake(IDrive.Direction.BACKWARD,4000,.35);

            // Drive Forward for 60 inches or until 10cm from something
//            _Drive.Straight(IDrive.Direction.FORWARD, 60, .5, 10);

            // break will exit the loop for us
            break;
        }

        while (opModeIsActive()) {

            // Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.BACKWARD, 24, .5, 10);


            // Reverse intake to push out pixels
            _Drive.Intake(IDrive.Direction.BACKWARD,4000,.35);

            // Turn 90 degrees to the left
            _Drive.Left(90, .25);


            // Drive Forward for 60 inches or until 10cm from something
//            _Drive.Straight(IDrive.Direction.FORWARD, 60, .5, 10);

            // break will exit the loop for us
            break;
        }


        while (opModeIsActive()) {

            // Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.BACKWARD, 24, .5, 10);

            // Turn 90 degrees to the left
            _Drive.Left(270, .25);

            // Reverse intake to push out pixels
            _Drive.Intake(IDrive.Direction.BACKWARD,4000,.35);



            // Drive Forward for 60 inches or until 10cm from something
//            _Drive.Straight(IDrive.Direction.FORWARD, 60, .5, 10);

            // break will exit the loop for us
            break;
        }
    }

    private void Initialize() {
        // Initialize driving
        _Drive = new DriveWithEncoders(hardwareMap, telemetry);
    }


}

