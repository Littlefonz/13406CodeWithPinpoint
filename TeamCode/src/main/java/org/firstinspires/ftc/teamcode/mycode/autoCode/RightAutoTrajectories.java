package org.firstinspires.ftc.teamcode.mycode.autoCode;

//Imports
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
    //Declare classes as empty variables
    PinpointDrive drive;
    Pose2d initialPose;
    Devices dev;
    Arm arm;
    Claw claw;
    Positions pos;

    /** Positions on the right side **/

    //Directions for the bot
    final double FORWARD = 0;
    final double LEFT = Math.toRadians(90);
    final double BACKWARD = Math.toRadians(180);
    final double RIGHT = Math.toRadians(-90);

    //Create the positions for the trajectories
    Vector2d firstSpecimenHangBackupVEC = new Vector2d(-24, 45);
    Vector2d samplePushingTransitionVEC = new Vector2d(-36, 18);
    Vector2d firstSampleVEC = new Vector2d(-49, 20);
    Vector2d firstToSecondSampleVEC = new Vector2d(-42, 20);
    Vector2d secondSampleVEC = new Vector2d(-60, 22);
    Vector2d secondToThirdSampleVEC = new Vector2d(-54, 22);
    Vector2d grabSpecimenVEC = new Vector2d(-33, 52);
    Vector2d parkVEC = new Vector2d(-48, 60);

    final Pose2d thirdSamplePOS = new Pose2d(-66, 16, RIGHT);
    final Pose2d preSpecimenHangPOS = new Pose2d(-16, 52, LEFT);
    final Pose2d firstSpecimenHangPOS = new Pose2d(-4, 33.5, LEFT);
    final Pose2d secondSpecimenHangPOS = new Pose2d(-2, 31.5, LEFT);
    final Pose2d otherSpecimenHangPOS = new Pose2d(-2, 32.5, LEFT);
    final Pose2d grabSpecimenPOS = new Pose2d(-33, 52, RIGHT);
    final Pose2d preGrabSpecimenPOS = new Pose2d(-16, 52, LEFT);

    //Create the trajectories variables
    public TrajectoryActionBuilder Start_HangSpecimen1;
    public TrajectoryActionBuilder HangSpecimen1_SamplePushing;
    public TrajectoryActionBuilder SamplePushing_HangSpecimen2;
    public TrajectoryActionBuilder HangSpecimen_GrabSpecimen;
    public TrajectoryActionBuilder GrabSpecimen_HangSpecimen;
    public TrajectoryActionBuilder HangSpecimen_Park;

    //The constructor to setup every class and trajectory
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

        //Trajectory #1
        Start_HangSpecimen1 = drive.actionBuilder(initialPose)
                .afterTime(0.25, claw.setPos(pos.highRung))
                .afterTime(0, arm.setPos(1000, 1375))//750, 2300
                .afterTime(0, claw.setPos(.3, .71))
                .waitSeconds(.4)
                .setTangent(RIGHT)
                .splineToLinearHeading(firstSpecimenHangPOS, RIGHT,
                        new TranslationalVelConstraint(75.0),
                        new ProfileAccelConstraint(-75.0, 75.0))
                .waitSeconds(.25);

        //Trajectory #2
        HangSpecimen1_SamplePushing = Start_HangSpecimen1.endTrajectory().fresh()
                .afterTime(.5, claw.setProngs(pos.openXL))
                .afterTime(.75, arm.setPos(pos.idle))
                .afterTime(1, claw.setPos(pos.idle))
                .afterTime(10.0, claw.setProngs(pos.openXL))
                .afterTime(10.0, arm.setPos(pos.grabMiddle))
                .afterTime(10.0, claw.setPos(pos.grabMiddle))
                .afterTime(11.5, claw.setProngs(pos.closed))
                .setTangent(LEFT)
                //Prep movement
                .splineToConstantHeading(firstSpecimenHangBackupVEC, BACKWARD,
                        new TranslationalVelConstraint(75.0))
                //1st sample move prep
                .splineToConstantHeading(samplePushingTransitionVEC, RIGHT,
                        new TranslationalVelConstraint(30.0))
                //1st sample move pos
                .splineToConstantHeading(firstSampleVEC, LEFT,
                        new TranslationalVelConstraint(25.0))
                //1st sample movement
                .lineToYConstantHeading(52,
                        new TranslationalVelConstraint(35.0))
                //1st to 2nd sample connection spline
                .splineToConstantHeading(firstToSecondSampleVEC, RIGHT,
                        new TranslationalVelConstraint(35.0))
                //2nd sample move pos
                .splineToConstantHeading(secondSampleVEC, LEFT,
                        new TranslationalVelConstraint(25.0))
                //2nd sample movement
                .lineToYConstantHeading(52,
                        new TranslationalVelConstraint(30.0))
                //2nd to 3rd sample connection spline
                .splineToConstantHeading(secondToThirdSampleVEC, RIGHT,
                        new TranslationalVelConstraint(35.0))
                //3rd sample move pos
                .splineToSplineHeading(thirdSamplePOS, LEFT,
                        new TranslationalVelConstraint(12.5))
                //3rd sample movement
                .lineToYConstantHeading(59.25,
                        new TranslationalVelConstraint(50.0));

        //Trajectory #3
        SamplePushing_HangSpecimen2 = HangSpecimen1_SamplePushing.endTrajectory().fresh()
                .afterTime(0, arm.setArm(pos.highRung[0][1]))
                .afterTime(0, claw.setPos(pos.highRung))
                .afterTime(2.0, arm.setSlides(1000))
                /* - - - - - - - - - - - - - - - - - - - - - - */
                .setTangent(Math.toRadians(-45))
                .splineToSplineHeading(preSpecimenHangPOS, FORWARD,
                        new TranslationalVelConstraint(45))
                .splineToLinearHeading(secondSpecimenHangPOS, RIGHT,
                        new TranslationalVelConstraint(45));

        //Trajectory #3, #5
        GrabSpecimen_HangSpecimen = drive.actionBuilder(grabSpecimenPOS)
                .afterTime(0, arm.setSlides(0))
                .afterTime(0, arm.setArm(pos.highRung[0][1]))
                .afterTime(0, claw.setPos(pos.highRung))
                .afterTime(1.75, arm.setSlides(1000))
                /* - - - - - - - - - - - - - - - - - - - - - - */
                .setTangent(FORWARD)
                .splineToSplineHeading(preSpecimenHangPOS, FORWARD,
                        new TranslationalVelConstraint(45))
                .splineToLinearHeading(otherSpecimenHangPOS, RIGHT,
                        new TranslationalVelConstraint(45));

        //Trajectory #4, #6
        HangSpecimen_GrabSpecimen = SamplePushing_HangSpecimen2.endTrajectory().fresh()
                .afterTime(0.2, claw.setProngs(pos.openXL))
                .afterTime(0.1, arm.setSlides(0))
                .afterTime(1.1, claw.setPos(pos.grabMiddle))
                .afterTime(1.1, arm.setArm(450))
                .afterTime(3.2, arm.setSlides(750))
                .afterTime(3.4, claw.setProngs(pos.closed))
                .afterTime(3.45, arm.setArm(650))
                /* - - - - - - - - - - - - - - - - - - - - - - */
                .splineToLinearHeading(preGrabSpecimenPOS, BACKWARD,
                        new TranslationalVelConstraint(45))
                .strafeToSplineHeading(grabSpecimenVEC, RIGHT,
                        new TranslationalVelConstraint(10));

        //Trajectory #8
        HangSpecimen_Park = SamplePushing_HangSpecimen2.endTrajectory().fresh()
                .afterTime(0, claw.setProngs(pos.open))
                .afterTime(0, arm.setSlides(0))
                .afterTime(.5, arm.setArm(0))
                .afterTime(.5, claw.setPos(pos.idle))
                .afterTime(.5, claw.setProngs(pos.closed))
                /* - - - - - - - - - - - - - - - - - - - - - - */
                .setTangent(LEFT)
                .strafeTo(parkVEC,
                        new TranslationalVelConstraint(50),
                        new ProfileAccelConstraint(-75.0, 75.0));
    }

    //The function to run the created trajectories
    public void runTrajectory(){
        Actions.runBlocking(
                new SequentialAction(
                        Start_HangSpecimen1.build(),
                        HangSpecimen1_SamplePushing.build(),
                        SamplePushing_HangSpecimen2.build(),
                        HangSpecimen_GrabSpecimen.build(),
                        new SleepAction(.25),
                        GrabSpecimen_HangSpecimen.build(),
                        HangSpecimen_GrabSpecimen.build(),
                        new SleepAction(.25),
                        GrabSpecimen_HangSpecimen.build(),
                        HangSpecimen_Park.build()
                )
        );
    }
}
