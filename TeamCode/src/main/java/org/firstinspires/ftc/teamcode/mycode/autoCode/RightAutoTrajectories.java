package org.firstinspires.ftc.teamcode.mycode.autoCode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.PinpointDrive;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Arm;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Claw;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Positions;

public class RightAutoTrajectories {
    //Variables
    PinpointDrive drive;
    Pose2d initialPose;
    Devices dev;
    Arm arm;
    Claw claw;
    Positions pos;

    //Positions on right side
    Vector2d highRungOffset = new Vector2d(-26, 45);
    Vector2d firstSampleOffsetB = new Vector2d(-32, 22);
    Vector2d firstSampleB = new Vector2d(-46, 30);
    Vector2d secondSampleB = new Vector2d(-56, 30); //55
    Vector2d thirdSampleB = new Vector2d(-66, 30); //63

    Pose2d samplePickupPrep = new Pose2d(-26, 61, Math.toRadians(-90));
    Pose2d thirdSampleDropOff = new Pose2d(-63, 62, Math.toRadians(90));
    Pose2d highRung = new Pose2d(-8, 34, Math.toRadians(90));
    Pose2d grabSpecimen = new Pose2d(-35, 62.5, Math.toRadians(-90));
    Pose2d loadingZone = new Pose2d(-64, 62, Math.toRadians(90));

    //Trajectories
    public TrajectoryActionBuilder rightStartToHangSpecimen;
    public TrajectoryActionBuilder rightToSampleConversions;
    public TrajectoryActionBuilder rightToGrabFirstSpecimen;
    public TrajectoryActionBuilder rightToHangSpecimen;
    public TrajectoryActionBuilder rightToGrabSpecimen;
    public TrajectoryActionBuilder parkInCorner2;

    public RightAutoTrajectories(PinpointDrive drive, Pose2d initialPose, Devices dev) {
        //Initial setup
        this.drive = drive;
        this.initialPose = initialPose;

        //Hardware map setup
        this.dev = dev;
        arm = new Arm(dev);
        claw = new Claw(dev);
        pos = new Positions();

        //Builder setup
        rightStartToHangSpecimen = drive.actionBuilder(initialPose)
                .afterTime(.35, arm.setPos(0, 2000))//750, 2300
                .afterTime(.35, claw.setPos(.65, .3))
                .afterTime(.6, claw.setPos(.28, .3))
                .setTangent(Math.toRadians(-45))
                .splineToLinearHeading(highRung, Math.toRadians(-90));

        rightToSampleConversions = drive.actionBuilder(highRung)
                .afterTime(.55, claw.intakeSpeed(-1))
                .afterTime(.75, arm.setPos(pos.idle))
                .afterTime(1, claw.setPos(pos.idle))
                .afterTime(1, claw.intakeSpeed(0))
                .setTangent(Math.toRadians(85))
                .splineToConstantHeading(highRungOffset, Math.toRadians(180),
                        new TranslationalVelConstraint(75.0))
                .splineToConstantHeading(firstSampleOffsetB, Math.toRadians(-90))
                .splineToConstantHeading(firstSampleB, Math.toRadians(90), //90
                        new TranslationalVelConstraint(30.0))
                .lineToYConstantHeading(60,
                        new TranslationalVelConstraint(75.0))
                .lineToYConstantHeading(22,
                        new TranslationalVelConstraint(75.0))
                .splineToConstantHeading(secondSampleB, Math.toRadians(90), //90
                        new TranslationalVelConstraint(30.0))
                .lineToYConstantHeading(60,
                        new TranslationalVelConstraint(75.0))
                .lineToYConstantHeading(22,
                        new TranslationalVelConstraint(75.0))
                .splineToConstantHeading(thirdSampleB, Math.toRadians(90), //90
                        new TranslationalVelConstraint(30.0))
                .lineToYConstantHeading(60,
                        new TranslationalVelConstraint(75.0));

        rightToGrabFirstSpecimen = drive.actionBuilder(thirdSampleDropOff)
                .stopAndAdd(arm.setPos(pos.grabMiddle))
                .afterTime(.25, claw.setPos(.65, .425))
                .afterTime(.5, claw.setPos(pos.grabMiddle))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(samplePickupPrep, Math.toRadians(90),
                        new TranslationalVelConstraint(75.0))
                .stopAndAdd(claw.intakeSpeed(1))
                .afterTime(.45, claw.intakeSpeed(0))
                .afterTime(.45, arm.setPos(0, 750))
                .lineToYLinearHeading(63, Math.toRadians(-90),
                        new TranslationalVelConstraint(10.0));

        rightToHangSpecimen = drive.actionBuilder(grabSpecimen)
                .stopAndAdd(claw.intakeSpeed(0))
                .afterTime(.25, claw.setPos(.96, .6))
                .afterTime(.25, arm.setPos(0, 1200))
                .afterTime(1.75, arm.setPos(0, 2000))
                .setTangent(Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(0, 33, Math.toRadians(90)), Math.toRadians(-90),
                        new TranslationalVelConstraint(75.0));

        rightToGrabSpecimen = drive.actionBuilder(new Pose2d(-5, 33, Math.toRadians(90)))
                .afterTime(.5, claw.intakeSpeed(-1))
                .afterTime(.75, claw.setPos(.65, .425))
                .afterTime(.75, arm.setPos(pos.grabMiddle))
                .afterTime(1, claw.intakeSpeed(0))
                .afterTime(1, claw.setPos(pos.grabMiddle))
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-26, 58, Math.toRadians(-90)), Math.toRadians(90),
                        new TranslationalVelConstraint(75.0))
                .stopAndAdd(claw.intakeSpeed(1))
                .afterTime(.45, claw.intakeSpeed(0))
                .afterTime(.45, arm.setPos(0, 750))
                .lineToYSplineHeading(62, Math.toRadians(-90),
                        new TranslationalVelConstraint(10.0))
                .waitSeconds(.2);

        parkInCorner2 = drive.actionBuilder(highRung)
                .afterTime(.5, claw.intakeSpeed(-1))
                .afterTime(.75, arm.setPos(pos.idle))
                .afterTime(1, claw.setPos(pos.idle))
                .afterTime(1, claw.intakeSpeed(0))
                .setTangent(Math.toRadians(85))
                .splineToSplineHeading(loadingZone, Math.toRadians(180),
                        new TranslationalVelConstraint(75.0));
    }
    public void runTrajectory(){
        Actions.runBlocking(
                new SequentialAction(
                        rightStartToHangSpecimen.build(),
                        rightToSampleConversions.build(),
                        rightToGrabFirstSpecimen.build(),
                        rightToHangSpecimen.build(),
                       //rightToGrabSpecimen.build(),
                        //rightToHangSpecimen.build(),
                        //rightToGrabSpecimen.build(),
                        //rightToHangSpecimen.build(),
                        parkInCorner2.build()
                )
        );
    }
}
