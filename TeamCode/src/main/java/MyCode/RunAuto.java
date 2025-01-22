package MyCode;

//Imports

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PinpointDrive;

@Autonomous(name="We'll be Gamin'", group="Autonomous")
public class RunAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(0, 0, 0);

        //Import the other files/classes
        //AutoSelector selector = new AutoSelector(gpad);
        Arm arm = new Arm(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        //Trajectories traj = new Trajectories(drive);

        arm.goRail.setVelocity(5000);
        arm.armAngle.setVelocity(2500);
        arm.goRail.setTargetPosition(0);
        arm.armAngle.setTargetPosition(0);
        claw.clawWrist.setPosition(1);
        claw.clawRotation.setPosition(.65);

        //selector.setChoice();
        switch (1) {
            case 1: //Red Left
                initialPose = new Pose2d(24, 66, Math.toRadians(180));
                break;
            case 2: //Red Right
                initialPose = new Pose2d(-24, 66, 0);
                break;
            case 3: //Blue Left
                initialPose = new Pose2d(24, -66, Math.toRadians(180));
                break;
            case 4: //Blue Right
                initialPose = new Pose2d(-24, -66, 0);
        }

        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        waitForStart();

        //Build the trajectories
        //Red Left
        TrajectoryActionBuilder leftStartToFirstYellow = drive.actionBuilder(initialPose)
                .stopAndAdd(claw.setPos(.65, .15))
                .stopAndAdd(arm.setPos(1000, 700))
                .afterTime(2, arm.setPos(1050, 250))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(38, 28, Math.toRadians(180)), Math.toRadians(-45));

        TrajectoryActionBuilder leftToSecondYellow = drive.actionBuilder(new Pose2d(56, 56, Math.toRadians(225)))
                .stopAndAdd(claw.intakeSpeed(-1))
                .afterTime(.6, claw.intakeSpeed(0))
                .afterTime(.6, claw.setPos(.65, .15))
                .afterTime(2, arm.setPos(1050, 250))
                .waitSeconds(.5)
                .splineToLinearHeading(new Pose2d(48, 28, Math.toRadians(-180)), 0);

        TrajectoryActionBuilder leftToThirdYellow = drive.actionBuilder(new Pose2d(56, 56, Math.toRadians(225)))
                .stopAndAdd(claw.intakeSpeed(-1))
                .afterTime(.6, claw.intakeSpeed(0))
                .afterTime(.6, claw.setPos(.65, .15))
                .afterTime(2, arm.setPos(1050, 250))
                .waitSeconds(.5)
                .splineToLinearHeading(new Pose2d(58, 28, Math.toRadians(-180)), 0);

        TrajectoryActionBuilder leftToBasket = drive.actionBuilder(new Pose2d(48, 26, Math.toRadians(-180)))
                .stopAndAdd(arm.setPos(550, 250))
                .stopAndAdd(claw.intakeSpeed(1))
                .afterTime(.25, arm.setPos(2625, 3000))
                .afterTime(.4, claw.intakeSpeed(0))
                .afterTime(.4, claw.setPos(.3, .4))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(56, 56, Math.toRadians(225)), Math.toRadians(-315));

        //Red Right
        TrajectoryActionBuilder rightStartToFirstSample = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-38, 26, 0), Math.toRadians(-135));

        TrajectoryActionBuilder rightToSecondSample = drive.actionBuilder(new Pose2d(-60, 60, Math.toRadians(-90)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-48, 26, 0), Math.toRadians(-90));

        TrajectoryActionBuilder rightToThirdSample = drive.actionBuilder(new Pose2d(0, 36, Math.toRadians(90)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-58, 26, 0), Math.toRadians(-135));

        TrajectoryActionBuilder rightToSpecimenConversion = drive.actionBuilder(new Pose2d(-48, 26, 0))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-60, 60, Math.toRadians(-90)), Math.toRadians(90));

        //From dropping off a sample
        TrajectoryActionBuilder rightToPickupSpecimen = drive.actionBuilder(new Pose2d(-60, 60, Math.toRadians(-90)))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-48, 60, Math.toRadians(-90)), 0);

        //From hanging a specimen
        TrajectoryActionBuilder rightToPickupLastSpecimen = drive.actionBuilder(new Pose2d(0, 36, Math.toRadians(90)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-48, 60, Math.toRadians(-90)), Math.toRadians(180));

        TrajectoryActionBuilder rightStartToHangSpecimen = drive.actionBuilder(new Pose2d(-48, 60, Math.toRadians(-90)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(0, 36, Math.toRadians(90)), Math.toRadians(-90));

        //Parking
        //From Basket
        TrajectoryActionBuilder parkInMiddle = drive.actionBuilder(new Pose2d(56, 56, Math.toRadians(225)))
                .stopAndAdd(claw.intakeSpeed(-1))
                .afterTime(.6, claw.intakeSpeed(0))
                .afterTime(.6, claw.setPos(.65, .15))
                .afterTime(.6, arm.setPos(1000, 700))
                .waitSeconds(.5)
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(26, 10, 0), Math.toRadians(180));

        //From basket
        TrajectoryActionBuilder parkInCorner = drive.actionBuilder(new Pose2d(56, 56, Math.toRadians(225)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-60, 60, Math.toRadians(180)), Math.toRadians(180));

        //From specimen hang
        TrajectoryActionBuilder parkInCorner2 = drive.actionBuilder(new Pose2d(0, 36, Math.toRadians(90)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-60, 60, Math.toRadians(180)), Math.toRadians(180));

        //From basket
        TrajectoryActionBuilder parkCloseToCorner = drive.actionBuilder(new Pose2d(56, 56, Math.toRadians(225)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-64, 64, Math.toRadians(180)), Math.toRadians(180));

        //From specimen hang
        TrajectoryActionBuilder parkCloseToCorner2 = drive.actionBuilder(new Pose2d(0, 36, Math.toRadians(-90)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-64, 64, Math.toRadians(180)), Math.toRadians(180));

        switch (1) {
            case 1:
                Actions.runBlocking(
                        new SequentialAction(
                                leftStartToFirstYellow.build(),
                                leftToBasket.build(),
                                leftToSecondYellow.build(),
                                leftToBasket.build(),
                                leftToThirdYellow.build(),
                                leftToBasket.build(),
                                parkInMiddle.build()
                        )
                );
                break;
            case 2:
                Actions.runBlocking(
                        new SequentialAction(
                                //arm.liftUp(), arm.extendGoRail(),
                                rightStartToHangSpecimen.build(),
                                //arm.liftDown(), arm.retractGoRail(),
                                rightStartToFirstSample.build(),
                                rightToSpecimenConversion.build(),
                                rightToSecondSample.build(),
                                rightToSpecimenConversion.build(),
                                rightToPickupSpecimen.build(),
                                //arm.liftUp(), arm.extendGoRail(),
                                rightStartToHangSpecimen.build(),
                                //arm.liftDown(), arm.retractGoRail(),
                                rightToThirdSample.build(),
                                rightToSpecimenConversion.build(),
                                rightToPickupSpecimen.build(),
                                //arm.liftUp(), arm.extendGoRail(),
                                rightStartToHangSpecimen.build(),
                                //arm.liftDown(), arm.retractGoRail(),
                                rightToPickupLastSpecimen.build(),
                                //arm.liftUp(), arm.extendGoRail(),
                                rightStartToHangSpecimen.build(),
                                //arm.liftDown(), arm.retractGoRail(),
                                parkInCorner2.build()
                        )
                );
                break;
            case 3:
                //Action robotGoBrrrr = robotGoBrrrrrr.build();
                break;
            case 4:
                //Action robotGoBrrrrr = robotGoBrrrrrr.build();
        }
        sleep(1500);
    }
}