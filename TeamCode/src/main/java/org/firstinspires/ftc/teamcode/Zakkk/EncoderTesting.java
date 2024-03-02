package org.firstinspires.ftc.teamcode.Zakkk;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;


@TeleOp(name = "Encoder Testing", group = "Competition")
public class EncoderTesting extends OpMode {

   /*
    * Declare Hardware
    */

   // Wheels
   private DcMotor WheelFrontLeft;
   private DcMotor WheelFrontRight;
   private DcMotor WheelBackLeft;
   private DcMotor WheelBackRight;
   //private DcMotor Climber;

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

//   //Climber
//
//   private double climbpower = 1;
//   private boolean isclimbing = false;
//
////    Intake
//   private DcMotor Intake;
//   private double IntakePower = .5;
//   private boolean IntakeMoving = false;

   //Claw Rotating

   private Servo Crotate;

   // REV Blinkin
//   private RevBlinkinLedDriver LED;


   // Calculate the COUNTS_PER_INCH for your specific drive train.
   // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
   // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
   // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
   // This is gearing DOWN for less speed and more torque.
   // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
   static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
   static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
   static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
   static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
           (WHEEL_DIAMETER_INCHES * 3.1415);
   static final double     DRIVE_SPEED             = 0.6;
   static final double     TURN_SPEED              = 0.5;



   @Override
   public void init() {

      // Initialize Wheels
      telemetry.addData("I", "Initializing Wheels");
      telemetry.update();

      WheelFrontLeft = hardwareMap.dcMotor.get("WheelFL");
      WheelFrontRight = hardwareMap.dcMotor.get("WheelFR");
      WheelBackLeft = hardwareMap.dcMotor.get("WheelBL");
      WheelBackRight = hardwareMap.dcMotor.get("WheelBR");
      Slide1 = hardwareMap.dcMotor.get("Slide1");
      Slide2 = hardwareMap.dcMotor.get("Slide2");
      Crotate = hardwareMap.servo.get("Crotate");
      Crotate.setPosition(0.1);
      Claw1 = hardwareMap.servo.get("Claw1");
      Claw1.setDirection(Servo.Direction.FORWARD);
      Claw1.setPosition(0.46);
      Claw2 = hardwareMap.servo.get("Claw2");
      Claw2.setDirection(Servo.Direction.REVERSE);
      Claw2.setPosition(0.46);
      Mlimit = hardwareMap.touchSensor.get("Mlimit");


      WheelFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      WheelFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      WheelBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      WheelBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      Slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      Slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      WheelFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      WheelFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      WheelBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      WheelBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      Slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      Slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

      WheelFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
      WheelFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
      WheelBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
      WheelBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
      Slide1.setDirection(DcMotorSimple.Direction.FORWARD);
      Slide2.setDirection(DcMotorSimple.Direction.FORWARD);

      WheelFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      WheelFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      WheelBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      WheelBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      Slide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      Slide2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
   boolean CrotateToPoint = false;
   boolean CrotateMoving = false;

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
      double twoLeftStickYPower = -gamepad2.left_stick_y;
      double twoLeftStickXPower = gamepad2.left_stick_x;
      double twoRightStickXPower = gamepad2.right_stick_x;
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


      telemetry.addData("Claw1", Claw1.getPosition());
      telemetry.addData("Claw2", Claw2.getPosition());
      telemetry.addData("Claw Rotate", Crotate.getPosition());
      telemetry.addData("Slide1 current position", Slide1.getCurrentPosition());
      telemetry.addData("Slide2 current position", Slide2.getCurrentPosition());


      if (Mlimit.isPressed()) {
         telemetry.addData("Slide1 mlimit is true", Slide1.getCurrentPosition());
         telemetry.addData("Slide2 mlimit is true", Slide2.getCurrentPosition());
      }

      if (Mlimit.isPressed() && FirstTime) {
         FirstTime = false;
         Slide1Zero = Slide1.getCurrentPosition();
         Slide2Zero = Slide2.getCurrentPosition();
      }

      if (gamepad2.x){
         FirstTime2 = true;
         telemetry.addData("X button pressed?", gamepad2.x);
      } else {
         telemetry.addData("X button pressed?", gamepad2.x);
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


      if (ClawButtonLeft == false && firstTimeLeft == false || gamepad2.y){
         firstTimeLeft = true;
      }

      if (ClawButtonLeft && firstTimeLeft || gamepad2.y){
         firstTimeLeft = false;
         clawToggle1 = !clawToggle1;
         if (clawToggle1){
            Claw1.setPosition(0.46);
         } else {
            Claw1.setPosition(0);
         }
      }

      ClawButtonRight = gamepad2.dpad_right;


      if (ClawButtonRight == false && firstTimeRight == false || gamepad2.y){
         firstTimeRight = true;
      }

      if (ClawButtonRight && firstTimeRight || gamepad2.y){
         firstTimeRight = false;
         clawToggle2 = !clawToggle2;
         if (clawToggle2){
            Claw2.setPosition(0.46);
         } else {
            Claw2.setPosition(0);
         }
      }




      // Drive Controls
      ProMotorControl(oneLeftStickYPower, oneLeftStickXPower, oneRightStickXPower);
      ToggleSineDrive(oneButtonB);

      // Slow Controls
      ToggleSlowModeDrive(oneButtonA);

      telemetry.update();

      //CLAW ROTATOR
      if (CrotateMoving == false || twoBumperLeft || twoBumperRight) {
         CrotateMoving = false;
         if (twoBumperLeft) {
            Crotate.setPosition(-1);
         } else if (twoBumperRight) {
            Crotate.setPosition(1);
         } else {
            Crotate.setPosition(0.5);
         }
      }

      if (gamepad2.a) {
         CrotateMoving = true;
         Crotate.setPosition(.25);
      }
      if (gamepad2.b) {
         CrotateMoving = true;
         Crotate.setPosition(-1);
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
}