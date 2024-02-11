package org.firstinspires.ftc.teamcode.Adam;

import static android.os.SystemClock.sleep;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "Scoring Arm", group = "testing")
public class ScoringArm extends OpMode {

    private DcMotor Slide1;
    private DcMotor Slide2;
    TouchSensor Touch;

    //boolean start = gamepad2.a;

    @Override
    public void init() {
        Slide1 = hardwareMap.get(DcMotor.class, "Slide1");
        Slide2 = hardwareMap.get(DcMotor.class, "Slide2");
        Touch = hardwareMap.get(TouchSensor.class, "Touch");

        Slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Slide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Slide1.setDirection(DcMotorSimple.Direction.FORWARD);
        Slide2.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void loop() {
        if (Touch.isPressed()) {
            slidesUp(0.5);
            sleep(TimeUnit.SECONDS.toMillis(1));
            slidesStop();
            sleep(TimeUnit.SECONDS.toMillis(1));
            slidesDown(0.5);
            sleep(TimeUnit.SECONDS.toMillis(1));
            slidesStop();
        }
    }

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
