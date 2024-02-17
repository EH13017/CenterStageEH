package org.firstinspires.ftc.teamcode.Charlie;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//@Disabled
//public class Autointake {
//
//    private DcMotor Intake;
//    private double IntakePower = .5;
//    private boolean IntakeMoving = false;
//
//    @Override
//    public void init() {
//
//        // Initialize Wheels
//        telemetry.addData("I", "Initializing Wheels");
//        telemetry.update();
//
//        WheelFrontLeft = hardwareMap.dcMotor.get("WheelFL");
//        WheelFrontRight = hardwareMap.dcMotor.get("WheelFR");
//        WheelBackLeft = hardwareMap.dcMotor.get("WheelBL");
//        WheelBackRight = hardwareMap.dcMotor.get("WheelBR");
//        Intake = hardwareMap.dcMotor.get("Intake");
//
//        WheelFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        WheelFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        WheelBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        WheelBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        WheelFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        WheelFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        WheelBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        WheelBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        Intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        WheelFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
//        WheelFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        WheelBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
//        WheelBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        Intake.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        WheelFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        WheelFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        WheelBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        WheelBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//
//
////      // Initialize REV Blinkin
////      telemetry.addData("I", "Initializing Blinkin");
////      telemetry.update();
////
////      LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");
////      setLEDPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
//
//
//        // Let the user know initialization is complete.
//        telemetry.addData("I", "Initialization Complete! :D");
//        telemetry.update();
//
//    }
//
//
//    public void loop() {
//
//        /*
//         * Gamepad Controls
//         */
//
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
//
//        /*
//         * Do Stuff Here!
//         */
//
//        //Intake
//        if (twoButtonA && IntakeMoving == false){ // Moves intake forward
//            IntakeForward();
//            IntakeMoving = true;
//        } else if (twoButtonB && IntakeMoving == false) { // Moves intake backwards
//            IntakeBackward();
//            IntakeMoving = true;
//        } else if (IntakeMoving && (twoButtonB || twoButtonA)) {
//            //do nothing while climbing
//
//        } else { // Stops intake
//            IntakeStop();
//            IntakeMoving = false;
//        }
//
////      // LEDs
////      manageLEDColors();
//
//        // Drive Controls
//        ProMotorControl(oneLeftStickYPower, oneLeftStickXPower, oneRightStickXPower);
//        ToggleSineDrive(oneButtonB);
//
//        // Slow Controls
//        ToggleSlowModeDrive(oneButtonA);
//
//        telemetry.update();
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
