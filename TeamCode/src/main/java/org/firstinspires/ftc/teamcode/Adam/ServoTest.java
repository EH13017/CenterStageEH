package org.firstinspires.ftc.teamcode.Adam;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Test", group = "testing")
public class ServoTest extends OpMode {
    //Declaration
    private Servo Claw;
    @Override
    public void init() {
        Claw = hardwareMap.servo.get("Claw");
        Claw.setPosition(0);
    }

    @Override
    public void loop() {
        Claw.setPosition(90);
    }
}
