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

    /** Positions on right side **/

    //High rung transition splines
    Vector2d firstHangTransition = new Vector2d(-26, 45);
    Vector2d firstHangTransition2 = new Vector2d(-32, 22);

    //First sample push pos
    Vector2d firstSample = new Vector2d(-46, 30);
    //Second sample push pos
    Vector2d secondSample = new Vector2d(-56, 30);
    //Third sample push pos
    Vector2d thirdSample = new Vector2d(-66, 30);

    //Ending push pos
    Pose2d sampleConvestionEndPos = new Pose2d(-66, 56, Math.toRadians(90));
    //Grab pos
    Pose2d grabSpecimen = new Pose2d(-26, 63, Math.toRadians(-90));
    //Specimen pickup pos
    Pose2d specimenPickupPrep = new Pose2d(-26, 61, Math.toRadians(-90));
    //Park pos
    Pose2d loadingZone = new Pose2d(-64, 64, Math.toRadians(90));

    //Hang #1
    Pose2d firstHangPos = new Pose2d(-8, 34, Math.toRadians(90));
    //Hang #2
    Pose2d secondHangPos = new Pose2d(-4, 26, Math.toRadians(90));
    //Hang #3
    Pose2d thirdHangPos = new Pose2d(0, 26, Math.toRadians(90));
    //Hang #4
    Pose2d fourthHangPos = new Pose2d(4, 26, Math.toRadians(90));
    //Hang #5
    Pose2d fifthHangPos = new Pose2d(8, 26, Math.toRadians(90));

    //Trajectories
    public TrajectoryActionBuilder rightStartToHangSpecimen;
    public TrajectoryActionBuilder rightToSampleConversions;
    public TrajectoryActionBuilder rightToStartCycling;
    public TrajectoryActionBuilder rightToNextCycle;
    public TrajectoryActionBuilder rightToHangSpecimen;
    public TrajectoryActionBuilder rightToHangSpecimen2;
    public TrajectoryActionBuilder rightToHangSpecimen3;
    public TrajectoryActionBuilder rightToHangSpecimen4;
    public TrajectoryActionBuilder parkInCorner;

    public RightAutoTrajectories(PinpointDrive drive, Pose2d initialPose, Devices dev) {
        //Initial setup
        this.drive = drive;
        this.initialPose = initialPose;

        //Hardware map setup
        this.dev = dev;
        arm = new Arm(dev);
        claw = new Claw(dev);
        pos = new Positions();

        /** Starting + prep positions **/

        rightStartToHangSpecimen = drive.actionBuilder(initialPose)
                .afterTime(.35, arm.setPos(0, 2000))//750, 2300
                .afterTime(.35, claw.setPos(.65, .3))
                .afterTime(.6, claw.setPos(.28, .3))
                .setTangent(Math.toRadians(-45))
                .splineToLinearHeading(firstHangPos, Math.toRadians(-90));

        rightToSampleConversions = drive.actionBuilder(firstHangPos)
                .afterTime(.55, claw.intakeSpeed(-1))
                .afterTime(.75, arm.setPos(pos.idle))
                .afterTime(1, claw.setPos(pos.idle))
                .afterTime(1, claw.intakeSpeed(0))
                .setTangent(Math.toRadians(85))
                //Prep movement
                .splineToConstantHeading(firstHangTransition, Math.toRadians(180),
                        new TranslationalVelConstraint(75.0))
                //1st sample move prep
                .splineToConstantHeading(firstHangTransition2, Math.toRadians(-90))
                //1st sample move pos
                .splineToConstantHeading(firstSample, Math.toRadians(90),
                        new TranslationalVelConstraint(30.0))
                //1st sample movement
                .lineToYConstantHeading(56,
                        new TranslationalVelConstraint(75.0))
                //1st to 2nd sample connection spline
                .splineToConstantHeading(firstSample, Math.toRadians(-90))
                //2nd sample move prep
                .lineToYConstantHeading(22,
                        new TranslationalVelConstraint(75.0))
                //2nd sample move pos
                .splineToConstantHeading(secondSample, Math.toRadians(90),
                        new TranslationalVelConstraint(30.0))
                //2nd sample movement
                .lineToYConstantHeading(56,
                        new TranslationalVelConstraint(75.0))
                //2nd to 3rd sample connection spline
                .splineToConstantHeading(secondSample, Math.toRadians(-90))
                //3rd sample move prep
                .lineToYConstantHeading(22,
                        new TranslationalVelConstraint(75.0))
                //3rd sample move pos
                .splineToConstantHeading(thirdSample, Math.toRadians(90),
                        new TranslationalVelConstraint(30.0))
                //3rd sample movement
                .lineToYConstantHeading(56,
                        new TranslationalVelConstraint(75.0));

        /** Cycling positions **/

        rightToStartCycling = drive.actionBuilder(sampleConvestionEndPos)
                .stopAndAdd(arm.setPos(pos.grabMiddle))
                .stopAndAdd(claw.setPos(.65, .425))
                .afterTime(.25, claw.setPos(pos.grabMiddle))
                .setTangent(Math.toRadians(-90))
                //Grabbing Specimen Code (same as rightToNextCycle)
                .splineToLinearHeading(specimenPickupPrep, Math.toRadians(90),
                        new TranslationalVelConstraint(75.0))
                .stopAndAdd(claw.intakeSpeed(1))
                .afterTime(.45, claw.intakeSpeed(0))
                .afterTime(.45, arm.setPos(0, 750))
                .lineToYLinearHeading(63, Math.toRadians(-90),
                        new TranslationalVelConstraint(10.0));

        rightToNextCycle = drive.actionBuilder(firstHangPos)
                .afterTime(.5, claw.intakeSpeed(-1))
                .afterTime(.75, claw.setPos(.65, .425))
                .afterTime(.75, arm.setPos(pos.grabMiddle))
                .afterTime(1, claw.intakeSpeed(0))
                .afterTime(1, claw.setPos(pos.grabMiddle))
                .setTangent(Math.toRadians(85))
                //Grabbing Specimen Code (same as rightToStartCycling)
                .splineToSplineHeading(specimenPickupPrep, Math.toRadians(90),
                        new TranslationalVelConstraint(75.0))
                .stopAndAdd(claw.intakeSpeed(1))
                .afterTime(.45, claw.intakeSpeed(0))
                .afterTime(.45, arm.setPos(0, 750))
                .lineToYSplineHeading(63, Math.toRadians(-90),
                        new TranslationalVelConstraint(10.0));

        /** Hang positions **/

        rightToHangSpecimen = drive.actionBuilder(grabSpecimen)
                .stopAndAdd(claw.intakeSpeed(0))
                .afterTime(.25, claw.setPos(.96, .6))
                .afterTime(.25, arm.setPos(0, 1200))
                .afterTime(1.75, arm.setPos(0, 2000))
                .setTangent(Math.toRadians(0))
                .splineToSplineHeading(secondHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(75.0))
                .lineToYSplineHeading(33, Math.toRadians(-90));

        rightToHangSpecimen2 = drive.actionBuilder(grabSpecimen)
                .stopAndAdd(claw.intakeSpeed(0))
                .afterTime(.25, claw.setPos(.96, .6))
                .afterTime(.25, arm.setPos(0, 1200))
                .afterTime(1.75, arm.setPos(0, 2000))
                .setTangent(Math.toRadians(0))
                .splineToSplineHeading(thirdHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(75.0))
                .lineToYSplineHeading(33, Math.toRadians(-90));

        rightToHangSpecimen3 = drive.actionBuilder(grabSpecimen)
                .stopAndAdd(claw.intakeSpeed(0))
                .afterTime(.25, claw.setPos(.96, .6))
                .afterTime(.25, arm.setPos(0, 1200))
                .afterTime(1.75, arm.setPos(0, 2000))
                .setTangent(Math.toRadians(0))
                .splineToSplineHeading(fourthHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(75.0))
                .lineToYSplineHeading(33, Math.toRadians(-90));

        rightToHangSpecimen4 = drive.actionBuilder(grabSpecimen)
                .stopAndAdd(claw.intakeSpeed(0))
                .afterTime(.25, claw.setPos(.96, .6))
                .afterTime(.25, arm.setPos(0, 1200))
                .afterTime(1.75, arm.setPos(0, 2000))
                .setTangent(Math.toRadians(0))
                .splineToSplineHeading(fifthHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(75.0))
                .lineToYSplineHeading(33, Math.toRadians(-90));

        /** Park position **/

        parkInCorner = drive.actionBuilder(fourthHangPos)
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
                        rightToStartCycling.build(),
                        rightToHangSpecimen.build(),
                        //rightToNextCycle.build(),
                        //rightToHangSpecimen2.build(),
                        //rightToNextCycle.build(),
                        //rightToHangSpecimen3.build(),
                        //rightToNextCycle.build(),
                        //rightToHangSpecimen4.build(),
                        parkInCorner.build()
                )
        );
    }
}
