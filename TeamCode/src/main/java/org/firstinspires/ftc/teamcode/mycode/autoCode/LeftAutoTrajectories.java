package org.firstinspires.ftc.teamcode.mycode.autoCode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Arm;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Claw;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Positions;

public class LeftAutoTrajectories {
    PinpointDrive drive;
    Pose2d initialPose;
    Devices dev;
    Arm arm;
    Claw claw;
    Positions pos;

    //Positions on left side
    Pose2d basket = new Pose2d(61, 61, Math.toRadians(-135));
    Pose2d firstSampleA = new Pose2d(36, 26, Math.toRadians(180));
    Pose2d secondSampleA = new Pose2d(47, 26, Math.toRadians(180));
    Pose2d thirdSampleA = new Pose2d(58, 26, Math.toRadians(180));
    Pose2d middle = new Pose2d(20, 10, 0);

    Pose2d leftHangPos = new Pose2d(8, 38, Math.toRadians(90));
    Vector2d firstHangTransition = new Vector2d(30, 50);

    //Trajectories
    public TrajectoryActionBuilder leftStartToBasket;
    public TrajectoryActionBuilder leftToFirstYellow;
    public TrajectoryActionBuilder leftToFirstYellow2;
    public TrajectoryActionBuilder leftToSecondYellow;
    public TrajectoryActionBuilder leftToThirdYellow;
    public TrajectoryActionBuilder leftToBasket;
    public TrajectoryActionBuilder parkInMiddle;
    public TrajectoryActionBuilder leftTransitionToYellow;
    public TrajectoryActionBuilder leftToHang;

    public LeftAutoTrajectories(PinpointDrive drive, Pose2d initialPose, Devices dev) {
        this.drive = drive;
        this.initialPose = initialPose;

        this.dev = dev;
        arm = new Arm(dev);
        claw = new Claw(dev);
        pos = new Positions();

        leftStartToBasket = drive.actionBuilder(initialPose)
                .afterTime(0, arm.setPos(pos.highBasket))
                .afterTime(0, claw.setPos(pos.highBasket))
                .waitSeconds(2.5)
                .setTangent(Math.toRadians(0))
                .lineToXSplineHeading(54, Math.toRadians(0))
                .afterTime(0, claw.setProngs(pos.openXL));

//        leftToHang = drive.actionBuilder(initialPose)
//                .afterTime(0.1, claw.setPos(.65, .3))
//                .afterTime(0.1, arm.setPos(0, 2300))//750, 2300
//                .afterTime(.35, claw.setPos(.28, .3))
//                .setTangent(Math.toRadians(-90))
//                .splineToLinearHeading(leftHangPos, Math.toRadians(-90),
//                        new TranslationalVelConstraint(45.0),
//                        new ProfileAccelConstraint(-75.0, 50.0));
//
//        leftTransitionToYellow = drive.actionBuilder(leftHangPos)
//                .afterTime(.55, claw.setProngs(-1))
//                .afterTime(1, claw.setPos(.65, .15))
//                .afterTime(1, arm.setPos(1200, 650))
//                .afterTime(1, claw.setProngs(0))
//                .setTangent(Math.toRadians(90))
//                .splineToConstantHeading(firstHangTransition, Math.toRadians(180),
//                        new TranslationalVelConstraint(45.0));

        leftToFirstYellow = leftStartToBasket.endTrajectory().fresh()
                .afterTime(.75, claw.setPos(.65, .07))
                .afterTime(.75, arm.setPos(700, 200))
                .afterTime(3.5, claw.setProngs(pos.closed))
                .setTangent(Math.toRadians(-180))
                .splineToLinearHeading(firstSampleA, Math.toRadians(-45),
                        new TranslationalVelConstraint(25.0));

//        leftToFirstYellow2 = drive.actionBuilder(new Pose2d(30, 50, Math.toRadians(90)))
//                .afterTime(3.25, arm.setPos(1250, 200))
//                .afterTime(3.25, claw.setProngs(1))
//                .waitSeconds(.5)
//                .setTangent(Math.toRadians(-180))
//                .splineToLinearHeading(firstSampleA, Math.toRadians(-45),
//                        new TranslationalVelConstraint(20.0))
//                .waitSeconds(.3);

        leftToBasket = leftToFirstYellow.endTrajectory().fresh()
                .afterTime(0, arm.setPos(2100, 3350))
                .afterTime(0, claw.setPos(pos.highBasket))
                .afterTime(4.0, claw.setProngs(pos.openXL))
                .setTangent(Math.toRadians(100))
                .splineToLinearHeading(basket, Math.toRadians(-315),
                        new TranslationalVelConstraint(12.5));

        leftToSecondYellow = leftToBasket.endTrajectory().fresh()
                .afterTime(.75, claw.setPos(.65, .07))
                .afterTime(.75, arm.setPos(700, 200))
                .afterTime(3.2, claw.setProngs(pos.closed))
                .splineToLinearHeading(secondSampleA, 0,
                        new TranslationalVelConstraint(25.0));

        leftToThirdYellow = leftToBasket.endTrajectory().fresh()
                .afterTime(.75, claw.setPos(.65, .07))
                .afterTime(.75, arm.setPos(700, 200))
                .afterTime(3.2, claw.setProngs(pos.closed))
                .splineToLinearHeading(thirdSampleA, 0,
                        new TranslationalVelConstraint(20.0));

        parkInMiddle = drive.actionBuilder(basket)
                .afterTime(.75, claw.setProngs(pos.closed))
                .afterTime(.75, claw.setPos(pos.idle))
                .afterTime(.75, arm.setPos(pos.idle))
                .afterTime(2.5, arm.setPos(0, 2000))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(middle, Math.toRadians(180),
                        new TranslationalVelConstraint(35))
                .waitSeconds(10);
    }
    public void runTrajectory(){
        Actions.runBlocking(
                new SequentialAction(
                        leftStartToBasket.build(),
                        leftToFirstYellow.build(),
                        //leftToHang.build(),
                        //leftTransitionToYellow.build(),
                        leftToBasket.build(),
                        leftToSecondYellow.build(),
                        leftToBasket.build(),
                        leftToThirdYellow.build(),
                        leftToBasket.build(),
                        parkInMiddle.build()
                )
        );
    }
}
