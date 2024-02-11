package org.firstinspires.ftc.teamcode.Adam;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "Claw Test", group = "testing")
public class ClawTest extends OpMode {
    Servo Claw1;
    Servo Claw2;
    TouchSensor Touch;
    boolean toggle = false;

    @Override
    public void init() {
        Claw1 = hardwareMap.servo.get("Claw1");
        Claw1.setDirection(Servo.Direction.FORWARD);
        Claw1.setPosition(0.46);
        Claw2 = hardwareMap.servo.get("Claw2");
        Claw2.setDirection(Servo.Direction.REVERSE);
        Claw2.setPosition(0.46);

        Touch = hardwareMap.get(TouchSensor.class, "Touch");
    }

    @Override
    public void loop() {
        if (Touch.isPressed()) {
            toggle = !toggle;
            while (Touch.isPressed()) {}
        }
        if (toggle) {
            Claw1.setPosition(1);
            Claw2.setPosition(1);
        }
        else {
            Claw1.setPosition(0.46);
            Claw2.setPosition(0.46);
        }
        telemetry.addData("Toggle", toggle);
        telemetry.addData("Claw1", Claw1.getPosition());
        telemetry.addData("Claw2", Claw2.getPosition());
    }
}
