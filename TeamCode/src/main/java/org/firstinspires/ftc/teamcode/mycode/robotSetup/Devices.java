package org.firstinspires.ftc.teamcode.mycode.robotSetup;

//Imports
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Devices{
    HardwareMap hardwareMap;

    //Declare Devices
    public DcMotorEx LFDrive;
    public DcMotorEx LBDrive;
    public DcMotorEx RFDrive;
    public DcMotorEx RBDrive;

    public IMU imu;

    public DcMotorEx goRail;
    public DcMotorEx armAngle;

    public CRServo intake1;
    public CRServo intake2;
    public Servo clawRotation;
    public Servo clawWrist;

    public Devices(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        //Arm & Claw
        goRail = hardwareMap.get(DcMotorEx.class, "goRail");
        armAngle = hardwareMap.get(DcMotorEx.class, "armAngle");
        intake1 = hardwareMap.get(CRServo.class, "intake1");
        intake2 = hardwareMap.get(CRServo.class, "intake2");
        clawRotation = hardwareMap.get(Servo.class, "clawRotation");
        clawWrist = hardwareMap.get(Servo.class, "clawWrist");
        //Drive-Train (FOD)
        LFDrive = hardwareMap.get(DcMotorEx.class, "leftFront");
        LBDrive = hardwareMap.get(DcMotorEx.class, "leftBack");
        RFDrive = hardwareMap.get(DcMotorEx.class, "rightFront");
        RBDrive = hardwareMap.get(DcMotorEx.class, "rightBack");
        //IMU
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                //RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                                //RevHubOrientationOnRobot.UsbFacingDirection.UP
                                new Orientation(
                                        AxesReference.INTRINSIC,
                                        AxesOrder.XYZ,
                                        AngleUnit.DEGREES,
                                        0,
                                        0,
                                        255,
                                        0  // acquisitionTime, not used
                                )
                        )
                )
        );
        //Set device directions-
        LFDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LBDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        intake2.setDirection(CRServo.Direction.REVERSE);
        intake1.setDirection(CRServo.Direction.FORWARD);
        //Set motor break behavior
        LFDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        LBDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        RFDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        RBDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        //Set device mode
        goRail.setTargetPosition(0);
        armAngle.setTargetPosition(0);
        goRail.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armAngle.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armAngle.setDirection(DcMotorSimple.Direction.REVERSE);
        //Set scale ranges for servos
        //clawWrist.scaleRange(0, 1);
        //clawRotation.scaleRange(0, 1);

        //Preset Positions
        clawWrist.setPosition(1);
        clawRotation.setPosition(.65);

        //Preset Velocities
        armAngle.setVelocity(2500);
        goRail.setVelocity(5000);
    }
}
