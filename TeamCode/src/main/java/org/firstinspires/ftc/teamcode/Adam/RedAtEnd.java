package org.firstinspires.ftc.teamcode.Adam;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Adam.RevBlinkinLedDriver;

import java.util.concurrent.TimeUnit;


@TeleOp(name = "Red At End", group = "Competition")
public class RedAtEnd extends OpMode {
    RevBlinkinLedDriver blinkinLedDriver;
    @Override
    public void init() {
        blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "Light");
        resetRuntime();
    }
    @Override
    public void loop() {
        long runtime = (long) getRuntime();

        telemetry.addData("Runtime", runtime);
        if (runtime >= TimeUnit.MINUTES.toSeconds(1)+30) {
            blinkinLedDriver.setPWM(0.6525);
        }
        telemetry.update();
    }
}
