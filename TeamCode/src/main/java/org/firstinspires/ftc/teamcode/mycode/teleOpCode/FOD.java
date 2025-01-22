package org.firstinspires.ftc.teamcode.mycode.teleOpCode;

//Imports
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;

public class FOD{
    //Declare classes
    Devices dev;
    //Identify other classes
    public FOD(Devices dev) {
        this.dev = dev;
    }
    //Variable
    public double maxSpeed;
    final public double slow = .25;
    final public double normal = .625;
    final public double fast = .85;

    //Resetting IMU
    public void resetOrientation(){dev.imu.resetYaw();}
    //All of the Driving Components
    public void FODDrive(double y, double x, double rx, /*double ry,*/ double speed) {
        //Speed multiplier
        x *= 1;
        y *= 1;
        rx *= 1.1;

        //Gets the direction the robot is facing (yaw)
        double orientation = dev.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        //Flick Stick turning (but Saeid hates it D:)
        //rx = FlickStickRXCalc(orientation, rx, ry);

        //Math to decide how the robot will move (with its current orientation)
        double fodStrafe = x * Math.cos(-orientation) - y * Math.sin(-orientation);
        double fodForward = x * Math.sin(-orientation) + y * Math.cos(-orientation);

        //Math to set the power/speed of the motors
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1); // Makes sure power <= maxSpeed
        double TLPower = (fodForward + fodStrafe + rx) / denominator;
        double BLPower = (fodForward - fodStrafe + rx) / denominator;
        double TRPower = (fodForward - fodStrafe - rx) / denominator;
        double BRPower = (fodForward + fodStrafe - rx) / denominator;

        //Engage the motors
        dev.LFDrive.setPower(TLPower * speed);
        dev.RFDrive.setPower(TRPower * speed);
        dev.LBDrive.setPower(BLPower * speed);
        dev.RBDrive.setPower(BRPower * speed);
    }
    public double joystickOrientation;
    
    public double FlickStickRXCalc(double botOrientation, double joystickX, double joystickY){ // calc is short for calculator if you didn't know
        //Base turning power
        double turnPower = 0;

        //Get the joysticks position
        joystickOrientation = Math.atan2(-joystickX,joystickY);

        //Adjust the bot's position based on the joystick's position
        if (Math.abs(joystickX) > 0.5 || Math.abs(joystickY) > 0.5) {
            turnPower = Math.max(botOrientation - joystickOrientation, 1);
        }

        //Return the new rx value
        return turnPower;
    }
}
