package org.firstinspires.ftc.teamcode.Zakkk;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "Drone Launcher", group = "testing")
public class DroneLauncher extends OpMode {

    /*
     * Declare Hardware
     */

    // Drone Launcher
    private Servo Drone;
    TouchSensor touchSensor;

    @Override
    public void init() {

        // Initialize
        telemetry.addData("I", "Initializing");
        telemetry.update();

        Drone = hardwareMap.servo.get("Drone");
        touchSensor = hardwareMap.get(TouchSensor.class, "Touch");
        Drone.setPosition(0);



        // Let the user know initialization is complete.
        telemetry.addData("I", "Initialization Complete! :D");
        telemetry.update();

    }

    @Override
    public void loop() {

        /*
         * Gamepad Controls
         */

//        // Gamepad 1
//        double oneLeftStickYPower = -gamepad1.left_stick_y;
//        double oneLeftStickXPower = gamepad1.left_stick_x;
//        double oneRightStickXPower = gamepad1.right_stick_x;
//        boolean oneButtonA = gamepad1.a;
//        boolean oneButtonB = gamepad1.b;
//
//        // Gamepad 2
//        boolean twoButtonA = gamepad2.a;
//        boolean twoButtonB = gamepad2.b;
//        boolean twoButtonX = gamepad2.x;
//        boolean twoButtonY = gamepad2.y;
//        boolean twoPadUp = gamepad2.dpad_up;
//        boolean twoPadDown = gamepad2.dpad_down;
//        boolean twoPadLeft = gamepad2.dpad_left;
//        boolean twoPadRight = gamepad2.dpad_right;
//        float twoTriggerLeft = gamepad2.left_trigger;
//        float twoTriggerRight = gamepad2.right_trigger;
//        boolean twoBumperLeft = gamepad2.left_bumper;
//        boolean twoBumperRight = gamepad2.right_bumper;
//        boolean twoBack = gamepad2.back;
//        boolean twoStart = gamepad2.start;


        /*
         * Do Stuff Here!
         */
        if (touchSensor.isPressed()) {
            Drone.setPosition(90);
        }
    }


    }
