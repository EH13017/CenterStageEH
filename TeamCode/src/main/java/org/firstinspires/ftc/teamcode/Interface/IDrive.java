package org.firstinspires.ftc.teamcode.Interface;



public interface IDrive {
    public enum Direction {
        FORWARD,
        BACKWARD
    }
    public void Forward(double distanceInch, double power, double stopDistance);
    public void Backward(double distanceInch, double power);
    public void Straight(Direction direction, double distanceInch, double power, double stopDistance);
    public void Left(int degrees, double power);
    public void Right(int degrees, double power);
    public void BasicMotorControl(double right_stick_y);
    public void ShowTelemetry();
}
