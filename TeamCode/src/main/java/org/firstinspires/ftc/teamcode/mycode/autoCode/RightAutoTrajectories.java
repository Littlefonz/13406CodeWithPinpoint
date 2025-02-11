package org.firstinspires.ftc.teamcode.mycode.autoCode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Arm;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Claw;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Positions;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Prongs;

public class RightAutoTrajectories {
    //Variables
    PinpointDrive drive;
    Pose2d initialPose;
    Devices dev;
    Arm arm;
    Claw claw;
    Positions pos;
    Prongs prongs;

    /** Positions on right side **/

    //High rung transition splines
    Vector2d firstHangTransition = new Vector2d(-26, 45);
    Vector2d firstHangTransition2 = new Vector2d(-38, 18);

    //First sample push pos
    Vector2d firstSample = new Vector2d(-46, 20);
    //Second sample push pos
    Vector2d secondSample = new Vector2d(-57, 22);
    //Third sample push pos
    Vector2d thirdSample = new Vector2d(-66, 22);

    //First sample push pos
    Vector2d farFirstSample = new Vector2d(-42, 20);
    //Second sample push pos
    Vector2d farSecondSample = new Vector2d(-54, 22);

    //Ending push pos
    Pose2d sampleConvestionEndPos = new Pose2d(-64, 58, Math.toRadians(90));
    //Grab pos
    Pose2d grabSpecimen = new Pose2d(-34, 64, Math.toRadians(-90));
    //Specimen pickup pos
    Pose2d specimenPickupPrep = new Pose2d(-34, 61, Math.toRadians(-90));
    //Park pos
    Pose2d loadingZone = new Pose2d(-48, 64, Math.toRadians(90));

    //Hang #1
    Pose2d firstHangPos = new Pose2d(-8, 36, Math.toRadians(90));
    //Hang #2
    Pose2d secondHangPos = new Pose2d(-4, 35, Math.toRadians(90));
    //Hang #3
    Pose2d thirdHangPos = new Pose2d(0, 37.5, Math.toRadians(90));
    //Hang #4
    Pose2d fourthHangPos = new Pose2d(4, 37.5, Math.toRadians(90));
    //Hang #5
    Pose2d fifthHangPos = new Pose2d(8, 37.5, Math.toRadians(90));

    //-----------------------------------------------------------------------------------//
    Vector2d firstSamplePushPos = new Vector2d(-24, 46);
    Pose2d secondSamplePushPos = new Pose2d(-35, 46, Math.toRadians(45));
    Pose2d thirdSamplePushPos = new Pose2d(-46, 48, Math.toRadians(45));

    Pose2d firstSamplePushEnd = new Pose2d(-24, 62, Math.toRadians(0));
    Pose2d secondSamplePushEnd = new Pose2d(-35, 62, Math.toRadians(0));
    Pose2d thirdSamplePushEnd = new Pose2d(-46, 62, Math.toRadians(0));

    Pose2d latchPos1 = new Pose2d(-8, 40, Math.toRadians(90));
    Pose2d latchPos2 = new Pose2d(-4, 40, Math.toRadians(90));
    Pose2d latchPos3 = new Pose2d(0, 40, Math.toRadians(90));
    Pose2d latchPos4 = new Pose2d(4, 40, Math.toRadians(90));

    Vector2d parkPos = new Vector2d(-48, 60);

    Vector2d specimenPos = new Vector2d(-34, 48);
    Pose2d specimenPosition = new Pose2d(-34, 48, Math.toRadians(-90));

    Pose2d latchPosCheckpoint = new Pose2d(-16, 48, Math.toRadians(90));
    Pose2d getSpecimenCheckpoint = new Pose2d(-16, 48, Math.toRadians(90));

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

    public TrajectoryActionBuilder rightToSamplePushing;
    public TrajectoryActionBuilder rightToNewHang1;
    public TrajectoryActionBuilder rightToNewHang2;
    public TrajectoryActionBuilder rightToNewHang3;
    public TrajectoryActionBuilder rightToNewHang4;
    public TrajectoryActionBuilder rightToNewSpecimen;
    public TrajectoryActionBuilder rightToPark;

    public RightAutoTrajectories(PinpointDrive drive, Pose2d initialPose, Devices dev) {
        //Initial setup
        this.drive = drive;
        this.initialPose = initialPose;

        //Hardware map setup
        this.dev = dev;
        arm = new Arm(dev);
        claw = new Claw(dev);
        pos = new Positions();
        prongs = new Prongs(dev);

        /** Starting + prep positions **/

        rightStartToHangSpecimen = drive.actionBuilder(initialPose)
                //.afterTime(0.25, claw.setPos(.65, .3))
                .afterTime(0, arm.setPos(0, 1500))//750, 2300
                .afterTime(0, claw.setPos(.3, .71))
                .afterTime(1.25, arm.setArm(1750))
                .setTangent(Math.toRadians(-45))
                .splineToLinearHeading(secondHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(75.0),
                        new ProfileAccelConstraint(-75.0, 75.0))
                .waitSeconds(.25);

        rightToSampleConversions = drive.actionBuilder(firstHangPos)
                //.afterTime(.5, claw.intakeSpeed(-1))
                //.afterTime(.75, arm.setPos(pos.idle))
                //.afterTime(1, claw.setPos(pos.idle))
                //.afterTime(1, claw.intakeSpeed(0))
                .setTangent(Math.toRadians(90))
                //Prep movement
                .splineToConstantHeading(firstHangTransition, Math.toRadians(180),
                        new TranslationalVelConstraint(75.0))
                //1st sample move prep
                .splineToConstantHeading(firstHangTransition2, Math.toRadians(-90),
                        new TranslationalVelConstraint(25.0))
                //1st sample move pos
                .splineToConstantHeading(firstSample, Math.toRadians(90),
                        new TranslationalVelConstraint(25.0))
                //1st sample movement
                .lineToYConstantHeading(52,
                        new TranslationalVelConstraint(25.0))
                //1st to 2nd sample connection spline
                .splineToConstantHeading(farFirstSample, Math.toRadians(-90),
                        new TranslationalVelConstraint(25.0))
                //2nd sample move pos
                .splineToConstantHeading(secondSample, Math.toRadians(90),
                        new TranslationalVelConstraint(25.0))
                //2nd sample movement
                .lineToYConstantHeading(52,
                        new TranslationalVelConstraint(40.0))
                //2nd to 3rd sample connection spline
                .splineToConstantHeading(farSecondSample, Math.toRadians(-90),
                        new TranslationalVelConstraint(25.0))
                //3rd sample move pos
                .splineToConstantHeading(thirdSample, Math.toRadians(90),
                        new TranslationalVelConstraint(20.0))
                //3rd sample movement
                .lineToYConstantHeading(60,
                        new TranslationalVelConstraint(50.0));

        /** Cycling positions **/

        rightToStartCycling = drive.actionBuilder(sampleConvestionEndPos)
                .stopAndAdd(arm.setPos(pos.grabMiddle))
                .stopAndAdd(claw.setPos(.65, .425))
                .afterTime(.25, claw.setPos(pos.grabMiddle))
                .setTangent(Math.toRadians(-90))
                //Grabbing Specimen Code (same as rightToNextCycle)
                .splineToLinearHeading(specimenPickupPrep, Math.toRadians(90),
                        new TranslationalVelConstraint(50))
                .afterTime(0.1, claw.setProngs(1))
                .afterTime(.4, claw.setProngs(0))
                .afterTime(.4, arm.setPos(0, 750))
                .lineToYLinearHeading(64.75, Math.toRadians(-90),
                        new TranslationalVelConstraint(10.0));

        rightToNextCycle = drive.actionBuilder(secondHangPos)
                .afterTime(.55, claw.setProngs(-1))
                .afterTime(.55, arm.setPos(0, 1500))
                .afterTime(1, claw.setPos(.65, .425))
                .afterTime(1, arm.setPos(pos.grabMiddle))
                .afterTime(1.25, claw.setProngs(0))
                .afterTime(1.25, claw.setPos(pos.grabMiddle))
                .setTangent(Math.toRadians(90))
                //Grabbing Specimen Code (same as rightToStartCycling)
                .splineToLinearHeading(new Pose2d(-31, 61, Math. toRadians(-90)), Math.toRadians(90),
                        new TranslationalVelConstraint(45.0))
                .waitSeconds(.25)
                .afterTime(0.2, claw.setProngs(1))
                .afterTime(.5, claw.setProngs(0))
                .afterTime(.5, arm.setPos(0, 750))
                .lineToYLinearHeading(64.75, Math.toRadians(-90),
                        new TranslationalVelConstraint(10.0),
                        new ProfileAccelConstraint(-35.0, 50.0));

        /** Hang positions **/

        rightToHangSpecimen = drive.actionBuilder(new Pose2d(-32, 63.75, Math. toRadians(-90)))
                .stopAndAdd(claw.setProngs(0))
                .afterTime(.285, claw.setPos(.96, .6))
                .afterTime(.285, arm.setPos(0, 1500))
                .afterTime(2, arm.setPos(0, 2200))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(secondHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(30),
                        new ProfileAccelConstraint(-75.0, 30.0));

        rightToHangSpecimen2 = drive.actionBuilder(new Pose2d(-32, 63.75, Math. toRadians(-90)))
                .stopAndAdd(claw.setProngs(0))
                .afterTime(.285, claw.setPos(.96, .6))
                .afterTime(.285, arm.setPos(0, 1500))
                .afterTime(2, arm.setPos(0, 2200))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(thirdHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(30),
                        new ProfileAccelConstraint(-75.0, 30.0));

        rightToHangSpecimen3 = drive.actionBuilder(new Pose2d(-32, 63.75, Math. toRadians(-90)))
                .stopAndAdd(claw.setProngs(0))
                .afterTime(.285, claw.setPos(.96, .6))
                .afterTime(.285, arm.setPos(0, 1500))
                .afterTime(2, arm.setPos(0, 2200))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(fourthHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(30),
                        new ProfileAccelConstraint(-75.0, 30.0));

        rightToHangSpecimen4 = drive.actionBuilder(new Pose2d(-32, 63.75, Math. toRadians(-90)))
                .stopAndAdd(claw.setProngs(0))
                .afterTime(.285, claw.setPos(.96, .6))
                .afterTime(.285, arm.setPos(0, 1500))
                .afterTime(2, arm.setPos(0, 2200))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(fifthHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(30),
                        new ProfileAccelConstraint(-75.0, 30.0));

        /** Park position **/

        parkInCorner = drive.actionBuilder(new Pose2d(4, 36, Math.toRadians(90)))
                //.afterTime(0, arm.setPos(0, 2500))
                //.afterTime(.5, claw.intakeSpeed(-1))
                //.afterTime(1, claw.intakeSpeed(0))
                .setTangent(Math.toRadians(-90))
                .lineToYLinearHeading(45, Math.toRadians(90),
                        new TranslationalVelConstraint(75.0))
                .afterTime(.75, arm.setPos(pos.idle))
                .afterTime(1, claw.setPos(pos.idle))
                .splineToLinearHeading(loadingZone, Math.toRadians(180),
                        new TranslationalVelConstraint(75.0));

        //-----------------------------------------------------------------------------------//
        rightToSamplePushing = rightStartToHangSpecimen.endTrajectory().fresh()

                .afterTime(.75, prongs.setPos(pos.open))
                .afterTime(1.25, claw.setPos(.51, .11))
                .afterTime(1.25, arm.setPos(0, 800, 1000))
                .afterTime(1.7, arm.setPos(0, 650, 1000))
                .afterTime(2, prongs.setPos(pos.closed))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(-90))
                .strafeToLinearHeading(firstSamplePushPos, Math.toRadians(45))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .afterTime(.9, claw.setProngs(pos.open))
                .afterTime(1, arm.setPos(0, 800, 1000))

                .afterTime(1.7, arm.setPos(0, 650, 1000))
                .afterTime(2, prongs.setPos(pos.closed))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(firstSamplePushEnd, Math.toRadians(0),
                        new TranslationalVelConstraint(20))
                .splineToLinearHeading(secondSamplePushPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(20))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .afterTime(.9, claw.setProngs(pos.open))
                .afterTime(1, arm.setPos(0, 800, 1000))

                .afterTime(1.7, arm.setPos(0, 650, 1000))
                .afterTime(2, prongs.setPos(pos.closed))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(secondSamplePushEnd, Math.toRadians(90),
                        new TranslationalVelConstraint(20))
                .splineToLinearHeading(thirdSamplePushPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(20))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .afterTime(.9, claw.setProngs(pos.open))
                .afterTime(1, arm.setPos(0, 800, 1000))

                .afterTime(1.75, claw.setProngs(pos.closed))
                .afterTime(2, arm.setArm(950))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(thirdSamplePushEnd, Math.toRadians(90),
                        new TranslationalVelConstraint(20));

                /* - - - - - - - - - - - - - - - - - - - - - - */

        rightToNewHang1 = rightToSamplePushing.endTrajectory().fresh()

                .afterTime(0, arm.setArm(pos.highRung[0][0]))
                .afterTime(0, claw.setPos(pos.highRung))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(latchPosCheckpoint, Math.toRadians(0),
                        new TranslationalVelConstraint(45))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .afterTime(0, arm.setSlides(1000))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .splineToSplineHeading(latchPos1, Math.toRadians(90),
                        new TranslationalVelConstraint(30));

                /* - - - - - - - - - - - - - - - - - - - - - - */

//        rightToNewHang2 = rightToNewSpecimen.endTrajectory().fresh()
//
//                .afterTime(0, arm.setPos(pos.highRung))
//                .afterTime(0, claw.setPos(pos.highRung))
//
//                /* - - - - - - - - - - - - - - - - - - - - - - */
//
//                .setTangent(Math.toRadians(-90))
//                .splineToLinearHeading(latchPosCheckpoint, Math.toRadians(0),
//                        new TranslationalVelConstraint(30))
//                .splineToSplineHeading(latchPos2, Math.toRadians(90));
//
//                /* - - - - - - - - - - - - - - - - - - - - - - */
//
//        rightToNewHang3 = rightToNewSpecimen.endTrajectory().fresh()
//
//                .afterTime(0, arm.setPos(pos.highRung))
//                .afterTime(0, claw.setPos(pos.highRung))
//
//                /* - - - - - - - - - - - - - - - - - - - - - - */
//
//                .setTangent(Math.toRadians(0))
//                .splineToLinearHeading(latchPosCheckpoint, Math.toRadians(0),
//                        new TranslationalVelConstraint(30))
//                .splineToSplineHeading(latchPos3, Math.toRadians(90));
//
//                /* - - - - - - - - - - - - - - - - - - - - - - */
//
//        rightToNewHang4 = rightToNewSpecimen.endTrajectory().fresh()
//
//                .afterTime(0, arm.setPos(pos.highRung))
//                .afterTime(0, claw.setPos(pos.highRung))
//
//                /* - - - - - - - - - - - - - - - - - - - - - - */
//
//                .setTangent(Math.toRadians(0))
//                .splineToLinearHeading(latchPosCheckpoint, Math.toRadians(0),
//                        new TranslationalVelConstraint(30))
//                .splineToSplineHeading(latchPos4, Math.toRadians(90));
//
//                /* - - - - - - - - - - - - - - - - - - - - - - */

        rightToNewSpecimen = rightToNewHang1.endTrajectory().fresh()

                .afterTime(0, prongs.setPos(pos.open))
                .afterTime(.25, arm.setSlides(0))
                .afterTime(1, arm.setSlides(1000))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(getSpecimenCheckpoint, Math.toRadians(180),
                        new TranslationalVelConstraint(30))
                .strafeToSplineHeading(specimenPos, Math.toRadians(-90));

                /* - - - - - - - - - - - - - - - - - - - - - - */

        rightToPark = rightToNewHang1.endTrajectory().fresh()

                .afterTime(0, prongs.setPos(pos.open))
                .afterTime(.25, arm.setPos(pos.idle))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(90))
                .strafeTo(parkPos,
                        new TranslationalVelConstraint(75),
                        new ProfileAccelConstraint(-75.0, 75.0));

                /* - - - - - - - - - - - - - - - - - - - - - - */
    }
    public void runTrajectory(){
        Actions.runBlocking(
                new SequentialAction(
                        rightStartToHangSpecimen.build(),
                        rightToSamplePushing.build(),
                        new SleepAction(.25),
                        rightToNewHang1.build(),
                        rightToNewSpecimen.build(),
                        new SleepAction(.5),
                        rightToNewHang1.build(),
                        rightToNewSpecimen.build(),
                        new SleepAction(.5),
                        rightToNewHang1.build(),
                        rightToNewSpecimen.build(),
                        new SleepAction(.5),
                        rightToNewHang1.build(),
                        rightToPark.build()
                )
        );
    }
}
