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
    Vector2d firstHangTransition = new Vector2d(-24, 45);
    Vector2d firstHangTransition2 = new Vector2d(-36, 18);

    //First sample push pos
    Vector2d firstSample = new Vector2d(-49, 20);
    //Second sample push pos
    Vector2d secondSample = new Vector2d(-60, 22);
    //Third sample push pos
    Vector2d thirdSample = new Vector2d(-64, 22);

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
    Pose2d firstHangPos = new Pose2d(-8, 36.25, Math.toRadians(90));
    //Hang #2
    Pose2d secondHangPos = new Pose2d(-4, 34, Math.toRadians(90));
    //Hang #3
    Pose2d thirdHangPos = new Pose2d(0, 37.5, Math.toRadians(90));
    //Hang #4
    Pose2d fourthHangPos = new Pose2d(4, 37.5, Math.toRadians(90));
    //Hang #5
    Pose2d fifthHangPos = new Pose2d(8, 37.5, Math.toRadians(90));

    //-----------------------------------------------------------------------------------//
    Pose2d firstSamplePushPos = new Pose2d(-42.5, 35, Math.toRadians(45));
    Pose2d secondSamplePushPos = new Pose2d(-50.5, 37, Math.toRadians(45));
    Pose2d thirdSamplePushPos = new Pose2d(-63, 20, Math.toRadians(-90));

    Pose2d firstSamplePushEnd = new Pose2d(-41.5, 35.5, Math.toRadians(-45));
    Pose2d secondSamplePushEnd = new Pose2d(-56, 36, Math.toRadians(-90));
    Pose2d thirdSamplePushEnd = new Pose2d(-63, 59, Math.toRadians(-90));

    Pose2d latchPos1 = new Pose2d(-2, 33, Math.toRadians(90));
    Pose2d latchPos2 = new Pose2d(-2, 32.5, Math.toRadians(90));
    Pose2d latchPos3 = new Pose2d(0, 40, Math.toRadians(90));
    Pose2d latchPos4 = new Pose2d(4, 40, Math.toRadians(90));

    Pose2d latchPos1SubPos = new Pose2d(-6, 34, Math.toRadians(90));
    Pose2d latchPos2SubPos = new Pose2d(-4, 34, Math.toRadians(90));

    Vector2d parkPos = new Vector2d(-48, 60);

    Vector2d specimenPos = new Vector2d(-33, 52);
    Pose2d specimenPosition = new Pose2d(-33, 52, Math.toRadians(-90));

    Pose2d latchPosCheckpoint = new Pose2d(-16, 52, Math.toRadians(90));
    Pose2d getSpecimenCheckpoint = new Pose2d(-16, 52, Math.toRadians(90));

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

    public TrajectoryActionBuilder rightStartToFirstClip;
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

        /** Starting + prep positions **/

        rightStartToHangSpecimen = drive.actionBuilder(initialPose)
                .afterTime(0.25, claw.setPos(pos.highRung))
                .afterTime(0, arm.setPos(750, 1375))//750, 2300
                .afterTime(0, claw.setPos(.3, .71))
                .waitSeconds(.4)
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(secondHangPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(75.0),
                        new ProfileAccelConstraint(-75.0, 75.0))
                .waitSeconds(.25);

        rightToSampleConversions = rightStartToHangSpecimen.endTrajectory().fresh()
                .afterTime(.5, claw.setProngs(pos.openXL))
                .afterTime(.75, arm.setPos(pos.idle))
                .afterTime(1, claw.setPos(pos.idle))
                .afterTime(10.0, claw.setProngs(pos.openXL))
                .afterTime(10.0, arm.setPos(pos.grabMiddle))
                .afterTime(10.0, claw.setPos(pos.grabMiddle))
                .afterTime(11.25, claw.setProngs(pos.closed))
                .setTangent(Math.toRadians(90))
                //Prep movement
                .splineToConstantHeading(firstHangTransition, Math.toRadians(180),
                        new TranslationalVelConstraint(75.0))
                //1st sample move prep
                .splineToConstantHeading(firstHangTransition2, Math.toRadians(-90),
                        new TranslationalVelConstraint(30.0))
                //1st sample move pos
                .splineToConstantHeading(firstSample, Math.toRadians(90),
                        new TranslationalVelConstraint(25.0))
                //1st sample movement
                .lineToYConstantHeading(52,
                        new TranslationalVelConstraint(35.0))
                //1st to 2nd sample connection spline
                .splineToConstantHeading(farFirstSample, Math.toRadians(-90),
                        new TranslationalVelConstraint(35.0))
                //2nd sample move pos
                .splineToConstantHeading(secondSample, Math.toRadians(90),
                        new TranslationalVelConstraint(25.0))
                //2nd sample movement
                .lineToYConstantHeading(52,
                        new TranslationalVelConstraint(30.0))
                //2nd to 3rd sample connection spline
                .splineToConstantHeading(farSecondSample, Math.toRadians(-90),
                        new TranslationalVelConstraint(35.0))
                //3rd sample move pos
                .splineToSplineHeading(new Pose2d(-66, 16, Math.toRadians(-90)), Math.toRadians(90),
                        new TranslationalVelConstraint(12.5))
                //3rd sample movement
                .lineToYConstantHeading(59.25,
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
        rightStartToFirstClip = drive.actionBuilder(initialPose)
                .afterTime(0, arm.setPos(pos.highRung))
                .afterTime(0, claw.setPos(pos.highRung))
                .setTangent(Math.toRadians(-90))
                .splineToSplineHeading(firstHangPos, Math.toRadians(90))
                .splineToSplineHeading(firstHangPos, Math.toRadians(-90));

        rightToSamplePushing = rightStartToHangSpecimen.endTrajectory().fresh()

                .afterTime(.50, claw.setProngs(pos.openXL))
                .afterTime(.75, claw.setPos(.46, .07))
                .afterTime(.75, arm.setPos(250, 500))
                .afterTime(2.0, arm.setArm(50, 1000))
                .afterTime(2.4, claw.setProngs(pos.closed))
                .afterTime(2.9, arm.setPos(2100, 700))

                .afterTime(4.35, claw.setProngs(pos.openXL))
                .afterTime(4.4, arm.setSlides(250))
                .afterTime(4.6, arm.setArm(500))

                .afterTime(5.6, arm.setArm(50, 1000))
                .afterTime(6.0, claw.setProngs(pos.closed))
                .afterTime(6.3, arm.setPos(2100, 700))

                .afterTime(6.5, claw.setProngs(pos.openXL))
                .afterTime(7.0, arm.setPos(pos.grabMiddle))
                .afterTime(7.0, claw.setPos(pos.grabMiddle))
                .afterTime(9.0, claw.setProngs(pos.closed))

//                .afterTime(5.5, arm.setSlides(2000))
//                .afterTime(5.8, arm.setArm(pos.grabAboveLow[0][1], 750))
//                .afterTime(6.1, claw.setProngs(pos.closed))
//                .afterTime(6.2, arm.setSlides(1400))
//
//                .afterTime(7.2, claw.setProngs(pos.open))
//
//                .afterTime(7.4, arm.setArm(pos.grabMiddle[0][1]))
//                .afterTime(7.4, arm.setSlides(2000, 500))
//                .afterTime(7.4, claw.setPos(pos.grabMiddle))
//                .afterTime(7.8, claw.setProngs(pos.closed))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(firstSamplePushPos, Math.toRadians(-135),
                        null,
                        new ProfileAccelConstraint(-45.0, 45.0))
                .waitSeconds(.5)

                /* - - - - - - - - - - - - - - - - - - - - - - */

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .turnTo(Math.toRadians(-70))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(secondSamplePushPos, Math.toRadians(180),
                        new TranslationalVelConstraint(20))
                .waitSeconds(.5)

                /* - - - - - - - - - - - - - - - - - - - - - - */

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(secondSamplePushEnd, Math.toRadians(180),
                        new TranslationalVelConstraint(5))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(thirdSamplePushPos, Math.toRadians(90),
                        new TranslationalVelConstraint(25))
                .splineToLinearHeading(thirdSamplePushEnd, Math.toRadians(90),
                        new TranslationalVelConstraint(35));
//
//                /* - - - - - - - - - - - - - - - - - - - - - - */
//
//                /* - - - - - - - - - - - - - - - - - - - - - - */
//
//                .setTangent(Math.toRadians(90))
//                .splineToSplineHeading(thirdSamplePushEnd, Math.toRadians(90),
//                        new TranslationalVelConstraint(15));
//
//                /* - - - - - - - - - - - - - - - - - - - - - - */

        rightToNewHang1 = rightToSampleConversions.endTrajectory().fresh()

                .afterTime(0, arm.setArm(pos.highRung[0][1]))
                .afterTime(0, claw.setPos(pos.highRung))
                .afterTime(1.75, arm.setSlides(750))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(-45))
                .splineToSplineHeading(latchPosCheckpoint, Math.toRadians(0),
                        new TranslationalVelConstraint(45))
                .splineToLinearHeading(latchPos2, Math.toRadians(-90),
                        new TranslationalVelConstraint(45));
//                .splineToLinearHeading(latchPos1SubPos, Math.toRadians(90),
//                        new TranslationalVelConstraint(45));

                /* - - - - - - - - - - - - - - - - - - - - - - */

        rightToNewHang2 = drive.actionBuilder(specimenPosition)

                .afterTime(0, arm.setSlides(0))
                .afterTime(0, arm.setArm(pos.highRung[0][1]))
                .afterTime(0, claw.setPos(pos.highRung))
                .afterTime(0.7, arm.setSlides(750))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(0))
                .splineToSplineHeading(latchPosCheckpoint, Math.toRadians(0),
                        new TranslationalVelConstraint(45))
                .splineToLinearHeading(latchPos1, Math.toRadians(-90),
                        new TranslationalVelConstraint(45));
//                .splineToLinearHeading(latchPos1SubPos, Math.toRadians(90),
//                        new TranslationalVelConstraint(45));

                /* - - - - - - - - - - - - - - - - - - - - - - */
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

                .afterTime(0, claw.setProngs(pos.openXL))
                .afterTime(0, arm.setSlides(0))
                .afterTime(1.1, claw.setPos(pos.grabMiddle))
                .afterTime(1.1, arm.setArm(425))
                .afterTime(3.0, arm.setSlides(750))
                .afterTime(3.4, claw.setProngs(pos.closed))
                .afterTime(3.45, arm.setArm(650))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                //.setTangent(Math.toRadians(90))
                .splineToLinearHeading(getSpecimenCheckpoint, Math.toRadians(180),
                        new TranslationalVelConstraint(45))
                .strafeToSplineHeading(specimenPos, Math.toRadians(-90),
                        new TranslationalVelConstraint(10));

                /* - - - - - - - - - - - - - - - - - - - - - - */

        rightToPark = rightToNewHang1.endTrajectory().fresh()

                .afterTime(0, claw.setProngs(pos.open))
                .afterTime(0, arm.setSlides(0))
                .afterTime(.5, arm.setArm(0))
                .afterTime(.5, claw.setPos(pos.idle))
                .afterTime(.5, claw.setProngs(pos.closed))

                /* - - - - - - - - - - - - - - - - - - - - - - */

                .setTangent(Math.toRadians(90))
                .strafeTo(parkPos,
                        new TranslationalVelConstraint(50),
                        new ProfileAccelConstraint(-75.0, 75.0));

                /* - - - - - - - - - - - - - - - - - - - - - - */
    }
    public void runTrajectory(){
        Actions.runBlocking(
                new SequentialAction(
                        rightStartToHangSpecimen.build(),
                        rightToSampleConversions.build(),
                        rightToNewHang1.build(),
                        rightToNewSpecimen.build(),
                        new SleepAction(.25),
                        rightToNewHang2.build(),
                        rightToNewSpecimen.build(),
                        new SleepAction(.25),
                        rightToNewHang2.build(),
                        rightToPark.build()
                )
        );
    }
}
