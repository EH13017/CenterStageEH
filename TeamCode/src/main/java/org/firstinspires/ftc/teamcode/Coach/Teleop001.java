package org.firstinspires.ftc.teamcode.Coach;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.util.Encoder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Disabled
@TeleOp(name = "TeleOp Teleop001", group = "Coach")
//@Disabled
public class Teleop001 extends OpMode {

    /*
     * Declare Hardware
     */

    // Wheels
    private DcMotor WheelFrontLeft;
    private DcMotor WheelFrontRight;
    private DcMotor WheelBackLeft;
    private DcMotor WheelBackRight;

    private Encoder leftEncoder, rightEncoder, frontEncoder;

    // SlowMode Drive
    private boolean slowModeDriveOn = true;
    private boolean buttonSlowDriveIsPressed = false;
    private final double SLOW_DRIVE = 0.4;
    private final double FAST_DRIVE = 1.0; //0.9;
    private double percentToSlowDrive = SLOW_DRIVE;

    // SineDrive
    private boolean sineDriveOn = true;
    private boolean buttonSineIsPressed = false;
    private double modifyBySine = Math.sin(Math.PI/4);

    private Orientation lastAngles = new Orientation();
//   // REV Blinkin
//   private RevBlinkinLedDriver LED;

    private IMU imu;

    @Override
    public void init() {

        // Initialize Wheels
        telemetry.addData("I", "Initializing Wheels");
        telemetry.update();



        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor WheelFrontLeft = hardwareMap.dcMotor.get("WheelFL");
        DcMotor WheelBackLeft = hardwareMap.dcMotor.get("WheelBL");
        DcMotor WheelFrontRight = hardwareMap.dcMotor.get("WheelFR");
        DcMotor WheelBackRight = hardwareMap.dcMotor.get("WheelBR");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        WheelFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        WheelBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

//        WheelFrontLeft = hardwareMap.dcMotor.get("WheelFL");
//        WheelFrontRight = hardwareMap.dcMotor.get("WheelFR");
//        WheelBackLeft = hardwareMap.dcMotor.get("WheelBL");
//        WheelBackRight = hardwareMap.dcMotor.get("WheelBR");
//
//        WheelFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        WheelFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        WheelBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        WheelBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        WheelFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        WheelFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        WheelBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        WheelBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        WheelFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
//        WheelFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        WheelBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
//        WheelBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        WheelFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        WheelFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        WheelBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        WheelBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                DriveConstants.LOGO_FACING_DIR, DriveConstants.USB_FACING_DIR));
        imu.initialize(parameters);

        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "WheelFL"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "WheelBR"));
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "WheelFR"));


//      // Initialize REV Blinkin
//      telemetry.addData("I", "Initializing Blinkin");
//      telemetry.update();
//
//      LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");
//      setLEDPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);


        // Let the user know initialization is complete.
        telemetry.addData("I", "Initialization Complete! :D");
        telemetry.update();

    }

    @Override
    public void loop() {

        /*
         * Gamepad Controls
         */

        // Gamepad 1
        double oneLeftStickYPower = -gamepad1.left_stick_y;
        double oneLeftStickXPower = gamepad1.left_stick_x;
        double oneRightStickXPower = gamepad1.right_stick_x;
        boolean oneButtonA = gamepad1.a;
        boolean oneButtonB = gamepad1.b;

        // Gamepad 2
        boolean twoButtonA = gamepad2.a;
        boolean twoButtonB = gamepad2.b;
        boolean twoButtonX = gamepad2.x;
        boolean twoButtonY = gamepad2.y;
        boolean twoPadUp = gamepad2.dpad_up;
        boolean twoPadDown = gamepad2.dpad_down;
        boolean twoPadLeft = gamepad2.dpad_left;
        boolean twoPadRight = gamepad2.dpad_right;
        float twoTriggerLeft = gamepad2.left_trigger;
        float twoTriggerRight = gamepad2.right_trigger;
        boolean twoBumperLeft = gamepad2.left_bumper;
        boolean twoBumperRight = gamepad2.right_bumper;
        boolean twoBack = gamepad2.back;
        boolean twoStart = gamepad2.start;

        int leftPos = leftEncoder.getCurrentPosition();
        int rightPos = rightEncoder.getCurrentPosition();
        int frontPos = frontEncoder.getCurrentPosition();


        telemetry.addData("leftPos", leftPos);
        telemetry.addData("rightPos", rightPos);
        telemetry.addData("frontPos", frontPos);
        telemetry.addData("IMU", imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);


        /*
         * Do Stuff Here!
         */

//      // LEDs
//      manageLEDColors();

        // Drive Controls
        ProMotorControl(oneLeftStickYPower, oneLeftStickXPower, oneRightStickXPower);
        ToggleSineDrive(oneButtonB);

        // Slow Controls
        ToggleSlowModeDrive(oneButtonA);

        telemetry.update();

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

    }


    /*
     * Methods
     */

    //https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
    //******************************************************************
    // Get the inputs from the controller for power [ PRO ]
    //******************************************************************
    private void ProMotorControl(double left_stick_y, double left_stick_x, double right_stick_x) {
//        double powerLeftY = -left_stick_y;   // DRIVE : Backward -1 <---> 1 Forward
//        double powerLeftX = left_stick_x*-1; // STRAFE:     Left -1 <---> 1 Right
//        double powerRightX = -right_stick_x; // ROTATE:     Left -1 <---> 1 Right
//
//        double r = Math.hypot(powerLeftX, powerLeftY);
//        double robotAngle = Math.atan2(powerLeftY, powerLeftX) - Math.PI / 4;
//        double leftX = powerRightX;
//        final double v1 = r * Math.cos(robotAngle) / modifyBySine + leftX;
//        final double v2 = r * Math.sin(robotAngle) / modifyBySine - leftX;
//        final double v3 = r * Math.sin(robotAngle) / modifyBySine + leftX;
//        final double v4 = r * Math.cos(robotAngle) / modifyBySine - leftX;
//
//        WheelFrontLeft.setPower(v1* percentToSlowDrive);
//        WheelFrontRight.setPower(v2* percentToSlowDrive);
//        WheelBackLeft.setPower(v3* percentToSlowDrive);
//        WheelBackRight.setPower(v4* percentToSlowDrive);
//
//        telemetry.addData("Wheel Front Left",v1* percentToSlowDrive);
//        telemetry.addData("Wheel Front Right",v2* percentToSlowDrive);
//        telemetry.addData("Wheel Back Left",v3* percentToSlowDrive);
//        telemetry.addData("Wheel Back Right",v4* percentToSlowDrive);

        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad1.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        WheelFrontLeft.setPower(frontLeftPower);
        WheelBackLeft.setPower(backLeftPower);
        WheelFrontRight.setPower(frontRightPower);
        WheelBackRight.setPower(backRightPower);
    }

    private void ToggleSlowModeDrive(boolean button) {
        if (button && !buttonSlowDriveIsPressed) {
            buttonSlowDriveIsPressed = true;
            slowModeDriveOn = !slowModeDriveOn;
        } if (!button) {  buttonSlowDriveIsPressed = false;  }

        if (slowModeDriveOn) {
            percentToSlowDrive = SLOW_DRIVE;
            telemetry.addData("Drive Mode","Slow: " + percentToSlowDrive + "% Power");
        } else {
            percentToSlowDrive = FAST_DRIVE;
            telemetry.addData("Drive Mode","Fast: " + percentToSlowDrive + "% Power");
        }
    }


    private void ToggleSineDrive(boolean button) {
        if (button && !buttonSineIsPressed) {
            buttonSineIsPressed = true;
            sineDriveOn = !sineDriveOn;
        } if (!button) {  buttonSineIsPressed = false;  }

        if (sineDriveOn) {
            modifyBySine = Math.sin(Math.PI/4);
            telemetry.addData("Sine Drive","ON");
        } else {
            modifyBySine = 1;
            telemetry.addData("Sine Drive","OFF");
        }
    }



//   // Here is a file to show how to use the REV Blinkin, along with a complete list of colors:
//   // https://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
//   protected void setLEDPattern(RevBlinkinLedDriver.BlinkinPattern setPattern) {
//      LED.setPattern(setPattern);
//   }
//
//   protected void turnOffLEDPattern() {
//      LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
//   }
//
//   private void manageLEDColors() {
//      if (heightLift == GROUND) {
//         setLEDPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
//      }
//      else if (heightLift == LOW) {
//         setLEDPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
//      }
//      else if (heightLift == MEDIUM) {
//         setLEDPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
//      }
//      else if (heightLift == HIGH) {
//         setLEDPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
//      }
//      else {
//         turnOffLEDPattern();
//      }
//   }
}
