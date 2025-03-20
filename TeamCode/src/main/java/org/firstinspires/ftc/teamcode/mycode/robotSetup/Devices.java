package org.firstinspires.ftc.teamcode.mycode.robotSetup;

//Imports
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
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

    public DcMotorEx armAngle;

    public Servo prong1;
    public Servo prong2;
    public Servo clawRotation;
    public Servo clawWrist;

    public DcMotorEx slides;

    public Devices(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        //Arm & Claw
        armAngle = hardwareMap.get(DcMotorEx.class, "armAngle");
        prong1 = hardwareMap.get(Servo.class, "intake1");
        prong2 = hardwareMap.get(Servo.class, "intake2");
        clawRotation = hardwareMap.get(Servo.class, "clawRotation");
        clawWrist = hardwareMap.get(Servo.class, "clawWrist");
        slides = hardwareMap.get(DcMotorEx.class, "slides");
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
        //Set motor break behavior
        LFDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        LBDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        RFDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        RBDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // Arm angle
        armAngle.setDirection(DcMotorSimple.Direction.REVERSE);
        armAngle.setTargetPosition(0);
        armAngle.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armAngle.setPower(1); // Was: 2500

        // Slides
        slides.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setTargetPosition(0);
        slides.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        slides.setPower(1); // increase this value?

        // Prongs
        prong1.setPosition(.49);
        prong2.setPosition(.49);
        prong2.setDirection(Servo.Direction.REVERSE);
        prong1.setDirection(Servo.Direction.FORWARD);

        // Claw
        clawWrist.setPosition(1);
        clawRotation.setPosition(.65);
    }
}
