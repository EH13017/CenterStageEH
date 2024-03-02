package org.firstinspires.ftc.teamcode.comp;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.teamcode.Adam.RevBlinkinLedDriver;

import java.util.concurrent.TimeUnit;


@TeleOp(name = "TeleOp Main", group = "Competition")
public class TeleOpMain extends OpMode {
   /*
    * Declare Hardware
    */

   //IMU
   private IMU imu;

   // Wheels
   private DcMotor WheelFrontLeft;
   private DcMotor WheelFrontRight;
   private DcMotor WheelBackLeft;
   private DcMotor WheelBackRight;
   private DcMotor Climber;

   // SlowMode Drive
   private boolean slowModeDriveOn = true;
   private boolean buttonSlowDriveIsPressed = false;
   private final double SLOW_DRIVE = 0.4;
   private final double FAST_DRIVE = 1.0; //0.9;
   private double percentToSlowDrive = SLOW_DRIVE;

   // SineDrive
   private boolean sineDriveOn = true;
   private boolean buttonSineIsPressed = false;
   private double modifyBySine = Math.sin(Math.PI / 4);

   //Arm
   private DcMotor Slide1;
   private DcMotor Slide2;
   private double armPower = 0.5;

   Servo Claw1;
   Servo Claw2;
   TouchSensor Mlimit;


   boolean ClawButtonLeft;
   boolean clawToggle1 = false;
   boolean ClawButtonRight;
   boolean clawToggle2 = false;

   //Drone
   private Servo Drone;

   //Climber

   private double climbpower = 1;
   private boolean isclimbing = false;

   //    Intake
   private DcMotor Intake;
   private double IntakePower = .5;
   private boolean IntakeMoving = false;

   //Claw Rotating

   private Servo Crotate;

   //REV Blinkin
   //RevBlinkinLedDriver blinkinLedDriver;



   @Override
   public void init() {
      resetRuntime();

      // Initialize Wheels
      telemetry.addData("I", "Initializing Wheels");
      telemetry.update();

      imu = hardwareMap.get(IMU.class, "imu");
      // Adjust the orientation parameters to match your robot
      IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
              RevHubOrientationOnRobot.LogoFacingDirection.UP,
              RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
      // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
      imu.initialize(parameters);
      imu.resetYaw();

      WheelFrontLeft = hardwareMap.dcMotor.get("WheelFL");
      WheelFrontRight = hardwareMap.dcMotor.get("WheelFR");
      WheelBackLeft = hardwareMap.dcMotor.get("WheelBL");
      WheelBackRight = hardwareMap.dcMotor.get("WheelBR");
      Climber = hardwareMap.dcMotor.get("Climber");
      Intake = hardwareMap.dcMotor.get("Intake");
      Slide1 = hardwareMap.get(DcMotor.class, "Slide1");
      Slide2 = hardwareMap.get(DcMotor.class, "Slide2");
      Crotate = hardwareMap.servo.get("Crotate");
      Claw1 = hardwareMap.servo.get("Claw1");
      Claw1.setDirection(Servo.Direction.FORWARD);
      Claw2 = hardwareMap.servo.get("Claw2");
      Claw2.setDirection(Servo.Direction.REVERSE);
      Drone = hardwareMap.servo.get("Drone");
      Drone.setPosition(0);
      Mlimit = hardwareMap.touchSensor.get("Mlimit");


      WheelFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      WheelFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      WheelBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      WheelBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      Climber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      Intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      WheelFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      WheelFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      WheelBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      WheelBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      Climber.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      Intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

      WheelFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
      WheelFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
      WheelBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
      WheelBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
      Climber.setDirection(DcMotorSimple.Direction.FORWARD);
      Intake.setDirection(DcMotorSimple.Direction.REVERSE);

      WheelFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      WheelFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      WheelBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      WheelBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      Climber.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


      // Initialize REV Blinkin
      //blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "Light");


      // Let the user know initialization is complete.
      telemetry.addData("I", "Initialization Complete! :D");
      telemetry.update();
   }


   boolean firstTimeLeft = true;
   boolean firstTimeRight = true;
   boolean FirstTime = true;

   boolean FirstTime2 = true;
   boolean IsSlideMoving = false;
   int Slide1Zero = 0;
   int Slide2Zero = 0;


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
      boolean oneStart = gamepad1.start;

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

      /*
       * Do Stuff Here!
       */

      //Red at end
      long runtime = (long) getRuntime();
      telemetry.addData("Runtime", runtime);
      if (runtime >= TimeUnit.MINUTES.toSeconds(1)+30) {
         //blinkinLedDriver.setPWM(0.6525);
      }

      //Shoot Drone
      if (gamepad2.right_stick_button) {
         Drone.setPosition(90);
      }

      // Moving Climber up and down
      if (twoButtonB && isclimbing == false) { // Button B moves Climber up.
         climbup();
         isclimbing = true;

      } else if (twoButtonA && isclimbing == false) { // Button A moves Climber down.
         climbdown();
         isclimbing = true;

      } else if (isclimbing && (twoButtonB || twoButtonA)) {
         //do nothing while climbing

      } else {
         climberstop();
         isclimbing = false;
      }

      if (Mlimit.isPressed()) {
         telemetry.addData("Slide1 mlimit is true", Slide1.getCurrentPosition());
         telemetry.addData("Slide2 mlimit is true", Slide2.getCurrentPosition());
      }

      if (Mlimit.isPressed() && FirstTime) {
         FirstTime = false;
         Slide1Zero = Slide1.getCurrentPosition();
         Slide2Zero = Slide2.getCurrentPosition();
      }

      if (gamepad2.left_stick_button){
         FirstTime2 = true;
      }

      if (FirstTime2) {
         FirstTime2 = false;
         Slide1.setPower(-0.4);
         Slide2.setPower(-0.4);
         IsSlideMoving = true;
         telemetry.update();
      } else{
         telemetry.addLine("At zero position");
      }

      if (IsSlideMoving) {
         if (Slide1.getCurrentPosition() <= Slide1Zero) {

            FirstTime2 = false;
            IsSlideMoving = false;
            Slide1.setPower(0);
            Slide2.setPower(0);
         }
      }
      // Moving Arm up and down
      if (IsSlideMoving == false || gamepad2.dpad_up || gamepad2.dpad_down) {
         IsSlideMoving = false;
         if (twoPadUp) {
            slidesUp(armPower);
         } else if (twoPadDown) {
            slidesDown(armPower);
         } else {
            slidesStop();
         }
      }

      //Claw
      ClawButtonLeft = gamepad2.dpad_left;


      if (ClawButtonLeft == false && firstTimeLeft == false){
         firstTimeLeft = true;
      }

      if (ClawButtonLeft && firstTimeLeft){
         firstTimeLeft = false;
         clawToggle1 = !clawToggle1;
         if (clawToggle1){
            Claw1.setPosition(0.46);
         } else {
            Claw1.setPosition(0);
         }
      }

      ClawButtonRight = gamepad2.dpad_right;


      if (ClawButtonRight == false && firstTimeRight == false){
         firstTimeRight = true;
      }

      if (ClawButtonRight && firstTimeRight){
         firstTimeRight = false;
         clawToggle2 = !clawToggle2;
         if (clawToggle2){
            Claw2.setPosition(0.46);
         } else {
            Claw2.setPosition(0);
         }
      }

      telemetry.addData("Claw1", Claw1.getPosition());
      telemetry.addData("Claw2", Claw2.getPosition());
//
      //Intake
      if (twoButtonX && IntakeMoving == false){ // Moves intake forward
         IntakeForward();
         IntakeMoving = true;
      } else if (twoButtonY && IntakeMoving == false) { // Moves intake backwards
         IntakeBackward();
         IntakeMoving = true;
      } else if (IntakeMoving && (twoButtonX || twoButtonY)) {
         //do nothing while climbing

      } else { // Stops intake
         IntakeStop();
         IntakeMoving = false;
      }


      // Drive Controls
      if (oneStart) {imu.resetYaw();}
      fieldCentric(oneLeftStickYPower, oneLeftStickXPower, oneRightStickXPower);
      ToggleSineDrive(oneButtonB);

      // Slow Controls
      ToggleSlowModeDrive(oneButtonA);

      telemetry.update();

      //CLAW ROTATOR
      if (twoBumperLeft) {
         Crotate.setPosition(-1);
      }
      else if (twoBumperRight) {
         Crotate.setPosition(1);
      }
      else {
         Crotate.setPosition(0.5);
      }

   }


   /*
    * Methods
    */

   //https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
   //******************************************************************
   // Get the inputs from the controller for power [ PRO ]
   //******************************************************************
   private void ProMotorControl(double left_stick_y, double left_stick_x, double right_stick_x) {
      double powerLeftY = left_stick_y;   // DRIVE : Backward -1 <---> 1 Forward
      double powerLeftX = -left_stick_x * -1; // STRAFE:     Left -1 <---> 1 Right
      double powerRightX = right_stick_x; // ROTATE:     Left -1 <---> 1 Right

      double r = Math.hypot(powerLeftX, powerLeftY);
      double robotAngle = Math.atan2(powerLeftY, powerLeftX) - Math.PI / 4;
      double leftX = powerRightX;
      final double v1 = r * Math.cos(robotAngle) / modifyBySine + leftX;
      final double v2 = r * Math.sin(robotAngle) / modifyBySine - leftX;
      final double v3 = r * Math.sin(robotAngle) / modifyBySine + leftX;
      final double v4 = r * Math.cos(robotAngle) / modifyBySine - leftX;

      WheelFrontLeft.setPower(v1 * percentToSlowDrive);
      WheelFrontRight.setPower(v2 * percentToSlowDrive);
      WheelBackLeft.setPower(v3 * percentToSlowDrive);
      WheelBackRight.setPower(v4 * percentToSlowDrive);

      telemetry.addData("Wheel Front Left", v1 * percentToSlowDrive);
      telemetry.addData("Wheel Front Right", v2 * percentToSlowDrive);
      telemetry.addData("Wheel Back Left", v3 * percentToSlowDrive);
      telemetry.addData("Wheel Back Right", v4 * percentToSlowDrive);
   }

   private void PowerMotorControl(double FL,double BL, double FR, double BR){
      final double v1 = FL / modifyBySine;
      final double v2 = BL / modifyBySine;
      final double v3 = FR / modifyBySine;
      final double v4 = BR / modifyBySine;

      WheelFrontLeft.setPower(v1 * percentToSlowDrive);
      WheelFrontRight.setPower(v2 * percentToSlowDrive);
      WheelBackLeft.setPower(v3 * percentToSlowDrive);
      WheelBackRight.setPower(v4 * percentToSlowDrive);

      telemetry.addData("Wheel Front Left", v1 * percentToSlowDrive);
      telemetry.addData("Wheel Front Right", v2 * percentToSlowDrive);
      telemetry.addData("Wheel Back Left", v3 * percentToSlowDrive);
      telemetry.addData("Wheel Back Right", v4 * percentToSlowDrive);
   }

   private void fieldCentric(double left_stick_y, double left_stick_x, double right_stick_x) {
      double y = left_stick_y;   // DRIVE : Backward -1 <---> 1 Forward
      double x = left_stick_x;   // STRAFE:     Left -1 <---> 1 Right
      double rx = right_stick_x; // ROTATE:     Left -1 <---> 1 Right

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
      double backLeftPower = (rotY - rotX - rx) / denominator;
      double frontRightPower = (rotY - rotX + rx) / denominator;
      double backRightPower = (rotY + rotX - rx) / denominator;

      PowerMotorControl(frontLeftPower, backLeftPower, frontRightPower, backRightPower);

      telemetry.addData("rotX", rotX);
      telemetry.addData("rotY", rotY);
      telemetry.addData("angle", botHeading);
   }


   private void ToggleSlowModeDrive(boolean button) {
      if (button && !buttonSlowDriveIsPressed) {
         buttonSlowDriveIsPressed = true;
         slowModeDriveOn = !slowModeDriveOn;
      }
      if (!button) {
         buttonSlowDriveIsPressed = false;
      }

      if (slowModeDriveOn) {
         percentToSlowDrive = SLOW_DRIVE;
         telemetry.addData("Drive Mode", "Slow: " + percentToSlowDrive + "% Power");
      } else {
         percentToSlowDrive = FAST_DRIVE;
         telemetry.addData("Drive Mode", "Fast: " + percentToSlowDrive + "% Power");
      }
   }


   private void ToggleSineDrive(boolean button) {
      if (button && !buttonSineIsPressed) {
         buttonSineIsPressed = true;
         sineDriveOn = !sineDriveOn;
      }
      if (!button) {
         buttonSineIsPressed = false;
      }

      if (sineDriveOn) {
         modifyBySine = Math.sin(Math.PI / 4);
         telemetry.addData("Sine Drive", "ON");
      } else {
         modifyBySine = 1;
         telemetry.addData("Sine Drive", "OFF");
      }
   }

   private void climbup() {

      Climber.setPower(climbpower);

   }


   private void climbdown() {

      Climber.setPower(-climbpower);

   }


   private void climberstop() {

      Climber.setPower(0);

   }

   //Arm
   public void slidesUp(double power) {
      Slide1.setPower(power);
      Slide2.setPower(power);
   }

   public void slidesDown(double power) {
      Slide1.setPower(-power);
      Slide2.setPower(-power);
   }

   public void slidesStop() {
      Slide1.setPower(0);
      Slide2.setPower(0);
   }


//
   private void IntakeForward() {
      Intake.setPower(IntakePower);
   }

   private void IntakeBackward() {
      Intake.setPower(-IntakePower);
   }
   private void IntakeStop() {
      Intake.setPower(0);
   }

}


