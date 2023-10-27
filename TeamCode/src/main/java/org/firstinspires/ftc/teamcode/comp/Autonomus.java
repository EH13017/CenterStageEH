package org.firstinspires.ftc.teamcode.comp;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Coach.DriveWithEncoders;
import org.firstinspires.ftc.teamcode.Interface.IDrive;

public class Autonomus {@Autonomous(name = "Coach: AutoBlue002", group = "Coach")
public class AutoBlue002 extends LinearOpMode {

    private IDrive _Drive;
    private DcMotor WheelFrontLeft;
    private DcMotor WheelFrontRight;
    private DcMotor WheelBackLeft;
    private DcMotor WheelBackRight;
    private final double SLOW_DRIVE = 0;
    private double percentToSlowDrive = SLOW_DRIVE;
    private final double FAST_DRIVE = .45; //0.9;
    ModernRoboticsI2cRangeSensor rangeSensor;

    @Override
    public void runOpMode() {

        // get a reference to our compass
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_ods");

        WheelFrontLeft = hardwareMap.dcMotor.get("WheelFL");
        WheelFrontRight = hardwareMap.dcMotor.get("WheelFR");
        WheelBackLeft = hardwareMap.dcMotor.get("WheelBL");
        WheelBackRight = hardwareMap.dcMotor.get("WheelBR");

        WheelFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WheelFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WheelBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WheelBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        WheelFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        WheelFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        WheelBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        WheelBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        WheelFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        WheelFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        WheelBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        WheelBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        WheelFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        WheelFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        WheelBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        WheelBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



    }
    @Autonomous(name = "Sensor: MR range sensor", group = "Sensor")
    @Disabled   // comment out or remove this line to enable this OpMode
    public class SensorMRRangeSensor extends LinearOpMode {

         (opModeIsActive()) {
            // get a reference to our compass
            rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

            // wait for the start button to be pressed
            waitForStart();

            while (opModeIsActive()) {
                telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
                telemetry.addData("raw optical", rangeSensor.rawOptical());
                telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
                telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
                telemetry.update();
            }
        }
    public void setSLOW_DRIVE (int rawUltrasonic) {
        if (rawUltrasonic<=10) {
            percentToSlowDrive = SLOW_DRIVE;
            telemetry.addData("Drive Mode","Slow: " + percentToSlowDrive + "% Power");
        } else {
            percentToSlowDrive = FAST_DRIVE;
            telemetry.addData("Drive Mode","Fast: " + percentToSlowDrive + "% Power");
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {

        Initialize();

        telemetry.addData("=D", "Ready to start!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive())

            // Drive Forward for 24 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.FORWARD, 24, .5, 10);

            // Turn 90 degrees to the left
            _Drive.Left(90, .25);

            // Drive Forward for 60 inches or until 10cm from something
            _Drive.Straight(IDrive.Direction.FORWARD, 60, .5, 10);
           }
            // break will exit the loop for us
            break;
        }


    private void Initialize(){
        // Initialize driving
        _Drive = new DriveWithEncoders(hardwareMap, telemetry);
    }
}

