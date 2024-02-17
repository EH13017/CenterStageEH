package org.firstinspires.ftc.teamcode.Zakkk;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


@TeleOp
@Disabled
public class FieldCentricMecanumTeleOpZakk extends LinearOpMode {

   public ModernRoboticsI2cGyro modernRoboticsI2cGyro;
   public IntegratingGyroscope gyro;
   @Override
   public void runOpMode() throws InterruptedException {
      // Declare our motors
      FtcDashboard dashboard = FtcDashboard.getInstance();
      telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
      // Initialize Gyro
      modernRoboticsI2cGyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");
      gyro = (IntegratingGyroscope) modernRoboticsI2cGyro;
      telemetry.addData("GC", "Gyro Calibrating. Do Not Move!");
      telemetry.update();
      modernRoboticsI2cGyro.calibrate();


      // Make sure your ID's match your configuration
      DcMotor frontLeftMotor = hardwareMap.dcMotor.get("WheelFL");
      DcMotor backLeftMotor = hardwareMap.dcMotor.get("WheelBL");
      DcMotor frontRightMotor = hardwareMap.dcMotor.get("WheelFR");
      DcMotor backRightMotor = hardwareMap.dcMotor.get("WheelBR");

      // Reverse the right side motors. This may be wrong for your setup.
      // If your robot moves backwards when commanded to go forwards,
      // reverse the left side instead.
      // See the note about this earlier on this page.
      frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
      backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

      // Retrieve the IMU from the hardware map
//      IMU imu = hardwareMap.get(IMU.class, "imu");
//      // Adjust the orientation parameters to match your robot
//      IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//              RevHubOrientationOnRobot.LogoFacingDirection.UP,
//              RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
      // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
      //imu.initialize(parameters);
      double botHeading = 0;
      Orientation angles;
      waitForStart();

      if (isStopRequested()) return;

      while (opModeIsActive()) {
         double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
         double x = gamepad1.left_stick_x;
         double rx = gamepad1.right_stick_x;

         // This button choice was made so that it is hard to hit on accident,
         // it can be freely changed based on preference.
         // The equivalent button is start on Xbox-style controllers.
         if (gamepad1.options) {
            //imu.resetYaw();
            botHeading = 0;
         }

         //double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
         angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.RADIANS);
         botHeading = angles.firstAngle;
         telemetry.addData("botHeading",botHeading);
         telemetry.update();


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

         telemetry.addData("FL", frontLeftPower);
         telemetry.addData("BL", backLeftPower);
         telemetry.addData("FR", frontRightPower);
         telemetry.addData("BR", backRightPower);
         telemetry.update();

         frontLeftMotor.setPower(frontLeftPower);
         backLeftMotor.setPower(backLeftPower);
         frontRightMotor.setPower(frontRightPower);
         backRightMotor.setPower(backRightPower);
      }
   }
}