package org.firstinspires.ftc.teamcode.Coach;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.hardware.DcMotor;

//import org.firstinspires.ftc.teamcode.hardware.ArmPositions;
//import org.firstinspires.ftc.teamcode.hardware.FlipperMotorPositions;
//import org.firstinspires.ftc.teamcode.hardware.GripperPositions;
//import org.firstinspires.ftc.teamcode.hardware.Light;
//import org.firstinspires.ftc.teamcode.hardware.LightMode;

@Autonomous(name = "Auton Blue Close To Backstage", group = "Autons")
public class AutonBlueClose extends AutonBase {

   private int parkingPosition= -1;
   private int lastParkingPosition = -1;
   @Override
   public void runOpMode(){

      boolean left = false;
      boolean middle = false;
      boolean right = true;

      initialize();

//      clawServo1.moveToPosition(GripperPositions.GRIPPER1_CLOSED);
//      clawServo2.moveToPosition(GripperPositions.GRIPPER2_CLOSED);

      while (opModeInInit()) {
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

         if (lastParkingPosition != parkingPosition) {
            if (parkingPosition == 1) {
               telemetry.addData("Location", "Left");
//               lights.switchLight(Light.LED1, LightMode.RED);
            } else if (parkingPosition == 2) {
               telemetry.addData("Location", "Middle");
//               lights.switchLight(Light.LED1, LightMode.YELLOW);
            } else if (parkingPosition == 3) {
               telemetry.addData("Location", "Right");
//               lights.switchLight(Light.LED1, LightMode.GREEN);
            } else if (parkingPosition == 0) {
               telemetry.addData("Location", "Knowhere");
//               lights.switchLight(Light.LED1, LightMode.OFF);
            }
            lastParkingPosition = parkingPosition;
         }
      }

      waitForStart();

      //Set the arm position up to not drag
//      armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_ZERO);
//      sleep(1000);
//
//      //Left
//      if(parkingPosition == 1){
//         imuDrive(.4, 5, 0);
//         encoderStrafe(0.25,-11.75,5);
//         imuDrive(0.25, 25, 0);
//         imuDrive(.25,-6,0);
//
//         //move arm down to deliver
//         armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_MIN);
//         sleep(500);
//         clawServo2.moveToPosition(GripperPositions.GRIPPER2_OPEN);
//         sleep(1000);
//         armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_ZERO);
//         sleep(1000);
//
//         //drive to deliver pixel
//         imuDrive(0.25, -5.5, 0);
//         encoderStrafe(0.5, -35, 5);
//         imuTurn(0.5, 90);
//
//         //deliver pixel
//         armMotor.moveArmEncoded(ArmPositions.BACK_ARC_MAX);
//         sleep(500);
//         flipper.moveFlipperEncoded(FlipperMotorPositions.CLAW2_PLACE);
//         sleep(250);
//         imuDrive(.15, -.5, 0);
//         sleep(250);
//         clawServo1.moveToPosition(GripperPositions.GRIPPER1_OPEN);
//         sleep(250);
//         armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_ZERO);
//         sleep(250);
//
//         //park
//         encoderStrafe(0.5, 15, 5);
//         sleep(250);
//      }
//      //Middle
//      else if(parkingPosition == 2){
//         imuDrive(.3, 36, 0);
//         sleep(750);
//         imuDrive(.15, -5, 0);
//
//         //move arm down to deliver
//         armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_MIN);
//         sleep(500);
//         clawServo2.moveToPosition(GripperPositions.GRIPPER2_OPEN);
//         sleep(1000);
//         armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_ZERO);
//         sleep(1000);
//
//         //drive to deliver pixel
//         imuDrive(.15, -8, 0);
//         imuTurn(.75, 90);
//         imuDrive(0.5, -37.5, 0);
//         encoderStrafe(0.25, -2.5, 5);
//
//         //deploy back pixel
//         armMotor.moveArmEncoded(ArmPositions.BACK_ARC_MAX);
//         sleep(500);
//         flipper.moveFlipperEncoded(FlipperMotorPositions.CLAW2_PLACE);
//         sleep(250);
//         sleep(250);
//         clawServo1.moveToPosition(GripperPositions.GRIPPER1_OPEN);
//         sleep(250);
//         armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_ZERO);
//         sleep(250);
//
//         encoderStrafe(0.5,30,5);
//      }
//      //Right
//      else if(parkingPosition == 3){
//         imuDrive(.4, 29.5, 0);
//         imuTurn(.3, 90);
//         imuDrive(.25, 7, 0);
//         imuDrive(.15, -3, 0);
//
//         //move arm down to deliver
//         armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_MIN);
//         sleep(500);
//         clawServo2.moveToPosition(GripperPositions.GRIPPER2_OPEN);
//         sleep(500);
//         armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_ZERO);
//         sleep(500);
//
//         //drive towards pixel
//         imuDrive(.2, -20, 0);
//         encoderStrafe(0.5,-6.5,5);
//         imuDrive(.15, -14.5, 0);
//
//         //deposit back pixel
//         armMotor.moveArmEncoded(ArmPositions.BACK_ARC_MAX);
//         sleep(500);
//         flipper.moveFlipperEncoded(FlipperMotorPositions.CLAW2_PLACE);
//         sleep(250);
//         imuDrive(.15, -9.00, 0);
//         sleep(250);
//         clawServo1.moveToPosition(GripperPositions.GRIPPER1_OPEN);
//         sleep(250);
//         armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_ZERO);
//         sleep(250);
//
//         //slide out of the way
//         encoderStrafe(0.5,35,5);
//      }
//      //Error unable to find target so slide to backdrop
//      else{
//         imuDrive(.5, 5, 0);
//         encoderStrafe(0.5, -47, 5);
//         telemetry.addData("Park Position Unknown",parkingPosition);
//      }
//
//      //set arm down in the end
//      armMotor.moveArmEncoded(ArmPositions.FRONT_ARC_MIN);
   }
}