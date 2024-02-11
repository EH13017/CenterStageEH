package org.firstinspires.ftc.teamcode.Adam;

//Import libraries
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "Servo Test", group = "testing")
public class ServoTest extends LinearOpMode {
    //Define my hardware
    Servo Claw;
    TouchSensor Touch;

    //Make init function
    @Override
    public void runOpMode() {
        //Define variables
        boolean toggle = false;
        Claw = hardwareMap.servo.get("Claw");
        Touch = hardwareMap.get(TouchSensor.class, "Touch");
        Claw.setPosition(0);

        //Start loop
        waitForStart();
        while(opModeIsActive()){
            //Make a toggle for the button
            if (Touch.isPressed()) {
                toggle = !toggle;
                while (Touch.isPressed()) {
                    toggle = toggle;
                    telemetry.addData("Horray", toggle);
                    telemetry.update();
                }
            }
            //Set the servo position
            if (toggle) {
                Claw.setPosition(90);
            }
            else {
                Claw.setPosition(0);
            }
        }
    }
}
