package org.firstinspires.ftc.teamcode.mycode.autoCode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.PinpointDrive;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Arm;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Claw;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;

public class LeftAutoTrajectories {
    PinpointDrive drive;
    Pose2d initialPose;
    Devices dev;
    Arm arm;
    Claw claw;

    //Positions on left side
    Pose2d basket = new Pose2d(60, 60, Math.toRadians(225));
    Pose2d firstSampleA = new Pose2d(38, 25, Math.toRadians(180));
    Pose2d secondSampleA = new Pose2d(48.75, 25, Math.toRadians(180));
    Pose2d thirdSampleA = new Pose2d(60, 25, Math.toRadians(180));
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

        leftStartToBasket = drive.actionBuilder(initialPose)
                .stopAndAdd(arm.setPos(2200, 3000))
                .afterTime(0, claw.setPos(.65, 0))
                .afterTime(.25, claw.setPos(.3, 0))
                .waitSeconds(1.25)
                .setTangent(Math.toRadians(0))
                .lineToXSplineHeading(52, Math.toRadians(180));

        leftToHang = drive.actionBuilder(initialPose)
                .afterTime(0.1, claw.setPos(.65, .3))
                .afterTime(0.1, arm.setPos(0, 2300))//750, 2300
                .afterTime(.35, claw.setPos(.28, .3))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(leftHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(45.0),
                        new ProfileAccelConstraint(-75.0, 50.0));

        leftTransitionToYellow = drive.actionBuilder(leftHangPos)
                .afterTime(.55, claw.intakeSpeed(-1))
                .afterTime(1, claw.setPos(.65, .15))
                .afterTime(1, arm.setPos(1200, 650))
                .afterTime(1, claw.intakeSpeed(0))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(firstHangTransition, Math.toRadians(180),
                        new TranslationalVelConstraint(45.0));

        leftToFirstYellow = drive.actionBuilder(new Pose2d(52, 66, Math.toRadians(180)))
                .stopAndAdd(claw.intakeSpeed(-1))
                .afterTime(1, claw.intakeSpeed(0))
                .afterTime(1, claw.setPos(.65, .15))
                .afterTime(1, arm.setPos(1200, 650))
                .afterTime(3.25, arm.setPos(1250, 200))
                .afterTime(3.25, claw.intakeSpeed(1))
                .waitSeconds(.5)
                .setTangent(Math.toRadians(-180))
                .splineToLinearHeading(firstSampleA, Math.toRadians(-45),
                        new TranslationalVelConstraint(20.0))
                .waitSeconds(.3);

        leftToFirstYellow2 = drive.actionBuilder(new Pose2d(30, 50, Math.toRadians(90)))
                .afterTime(3.25, arm.setPos(1250, 200))
                .afterTime(3.25, claw.intakeSpeed(1))
                .waitSeconds(.5)
                .setTangent(Math.toRadians(-180))
                .splineToLinearHeading(firstSampleA, Math.toRadians(-45),
                        new TranslationalVelConstraint(20.0))
                .waitSeconds(.3);

        leftToSecondYellow = drive.actionBuilder(basket)
                .stopAndAdd(claw.intakeSpeed(-1))
                .afterTime(1, claw.intakeSpeed(0))
                .afterTime(1, claw.setPos(.65, .15))
                .afterTime(1, arm.setPos(1200, 650))
                .afterTime(3, arm.setPos(1250, 200))
                .afterTime(3, claw.intakeSpeed(1))
                .waitSeconds(.5)
                .splineToLinearHeading(secondSampleA, 0,
                        new TranslationalVelConstraint(20.0))
                .waitSeconds(.3);

        leftToThirdYellow = drive.actionBuilder(basket)
                .stopAndAdd(claw.intakeSpeed(-1))
                .afterTime(1, claw.intakeSpeed(0))
                .afterTime(1, claw.setPos(.65, .15))
                .afterTime(1, arm.setPos(1200, 650))
                .afterTime(3, arm.setPos(1250, 100))
                .afterTime(3.3, claw.intakeSpeed(1))
                .waitSeconds(.5)
                .splineToLinearHeading(thirdSampleA, 0,
                        new TranslationalVelConstraint(20.0))
                .waitSeconds(.4);

        leftToBasket = drive.actionBuilder(secondSampleA)
                .stopAndAdd(claw.intakeSpeed(0))
                .stopAndAdd(arm.setPos(2200, 3000))
                .afterTime(.4, claw.setPos(.3, 0))
                .setTangent(Math.toRadians(100))
                .splineToLinearHeading(basket, Math.toRadians(-315),
                        new TranslationalVelConstraint(20.0));

        parkInMiddle = drive.actionBuilder(basket)
                .stopAndAdd(claw.intakeSpeed(-1))
                .afterTime(.6, claw.intakeSpeed(0))
                .afterTime(1, claw.setPos(.95, .48))
                .afterTime(1, arm.setPos(0, 1400))
                .afterTime(3.6, arm.setPos(0, 2000))
                .waitSeconds(.5)
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(middle, Math.toRadians(180))
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
