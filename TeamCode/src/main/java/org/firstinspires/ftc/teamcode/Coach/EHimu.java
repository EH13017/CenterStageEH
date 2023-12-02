package org.firstinspires.ftc.teamcode.Coach;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Interface.IGyro;

import static android.os.SystemClock.sleep;

class EHimu implements IGyro {
   public IMU Gyro;
   private Orientation _lastAngles = new Orientation();
   private double _globalAngle;

   private HardwareMap hardwareMap;
   private Telemetry telemetry;

   // A timer helps provide feedback while calibration is taking place
   ElapsedTime timer = new ElapsedTime();

   public EHimu(HardwareMap hardwareMap, Telemetry telemetry){

      this.hardwareMap = hardwareMap;
      this.telemetry = telemetry;

      Initialize();
   }

   private void Initialize() {

      // Initialize Gyro
      Gyro = hardwareMap.get(IMU.class, "imu");
      telemetry.addData("GC", "Gyro Calibrating. Do Not Move!");
      telemetry.update();
      

      

   }

   private double lastHeading = 0;

   /**
    * Get current cumulative angle rotation from last reset.
    * https://stemrobotics.cs.pdx.edu/node/7268
    *
    * @return Angle in degrees. + = left, - = right from zero point.
    */
   @Override
   public double GetAngle() {
      /* We experimentally determined the Z axis is the axis we want to use for heading angle.
       * We have to process the angle because the imu works in euler angles so the Z axis is
       * returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
       * 180 degrees. We detect this transition and track the total cumulative angle of rotation. */


      /* Read dimensionalized data from the gyro. This gyro can report angular velocities
       * about all three axes. Additionally, it internally integrates the Z axis to
       * be able to report an absolute angular Z orientation. */
      //Orientation angles = Gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

      // Gets the current heading from the IMU
      double currentheading = getHeading();

      double deltaAngle = currentheading - lastHeading;

      if (deltaAngle < -180)
         deltaAngle += 360;
      else if (deltaAngle > 180)
         deltaAngle -= 360;

      _globalAngle += deltaAngle;

      lastHeading = currentheading;

      return _globalAngle;
   }

   /**
    * read the Robot heading directly from the IMU (in degrees)
    */
   public double getHeading() {
      YawPitchRollAngles orientation = Gyro.getRobotYawPitchRollAngles();
      double rawYaw = orientation.getYaw(AngleUnit.DEGREES);
      return (rawYaw < 0 ) ? rawYaw + 360 : rawYaw;
   }

   @Override
   public void ResetAngle() {
      //_lastAngles = Gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
      lastHeading = getHeading();
      _globalAngle = 0;
   }

   /**
    * Get current cumulative angle heading.
    *
    * @return Angle in degrees. + = left, - = right from zero point.
    */
   @Override
   public int GetHeadingEH(){ return (int)getHeading(); }

   @Override
   public void ResetHeadingEH() { ResetAngle(); }
}
