package org.firstinspires.ftc.teamcode.Coach;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Interface.IDrive;

@Autonomous(name = "Charlie: Autofrontbluemiddle", group = "Charlie")
public class Autofrontbluemiddle extends AutonBase {

    private IDrive _Drive;

    private int parkingPosition= -1;
    private int lastParkingPosition = -1;
    RobotCameraHandler robotCameraHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        boolean left = false;
        boolean middle = false;
        boolean right = true;

        Initialize();

        telemetry.addData("=D", "Ready to start!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            //check that the camera is open and working
            if (robotCameraHandler.frontCameraIsFaulty.get() && robotCameraHandler.enableFrontCamera)
                telemetry.addData("Status", "Front camera fault...Please restart");
            else
                telemetry.addData("Status", "Front Camera initialized");

            try {
                parkingPosition = robotCameraHandler.spikeLocationDetectionPipeline.getSpikeLocation().get();
                telemetry.addData("Parking Position", parkingPosition);
            } catch (NullPointerException npe) {
                telemetry.addData("cameras", npe.getMessage());
            }
            telemetry.update();

            // Call the correct auto code based on the location of the cube
            switch(parkingPosition){
                case 1:
                    SpikeLeft();
                    break;
                case 2:
                    SpikeMiddle();
                    break;
                case 3:
                    SpikeRight();
                    break;
                default:
                    // Do nothing
            }

            // The auto code is completed....exit
            break;
        }
    }

    private void Initialize(){
        initialize();

        // Initialize driving
        _Drive = new DriveWithEncoders(hardwareMap, telemetry);
    }

    private void SpikeRight(){
        /// Drive Forward for 24 inches or until 10cm from something
        _Drive.Straight(IDrive.Direction.FORWARD, 24, .3, 10);

        // Turn 90 degrees to the left
        _Drive.Right(90, .3);

        // Reverse intake to push out pixels
        _Drive.Intake(IDrive.Direction.BACKWARD, 4000, .35);

        // Drive Forward for 60 inches or until 10cm from something
        _Drive.Straight(IDrive.Direction.BACKWARD, 36, .5, 10);

    }

    private void SpikeLeft(){
        /// Drive Forward for 24 inches or until 10cm from something
        _Drive.Straight(IDrive.Direction.FORWARD, 28, .3, 10);

        // Turn 90 degrees to the left
        _Drive.Left(90, .3);

        // Reverse intake to push out pixels
        _Drive.Intake(IDrive.Direction.BACKWARD, 4000, .5);

        _Drive.StrafeLeft(3000,.3);

        // Drive Forward for 60 inches or until 10cm from something
        _Drive.Straight(IDrive.Direction.FORWARD, 36, .5, 10);

    }

    private void SpikeMiddle(){

            /// Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.FORWARD, 24, .3, 10);

            // Reverse intake to push out pixels
            _Drive.Intake(IDrive.Direction.BACKWARD, 4000, .35);

            // Turn 90 degrees to the left
            _Drive.Left(90, .3);

            // Drive Forward for 60 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.FORWARD, 24, .5, 10);

    }
}