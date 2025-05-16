package org.firstinspires.ftc.teamcode.mycode.autoCode;

//Imports
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Arm;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Claw;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Positions;
import roadrunner.PinpointDrive;

public class OutreachAuto {
    //Declare classes as empty variables
    PinpointDrive drive;
    Pose2d initialPose;
    Devices dev;
    Arm arm;
    Claw claw;
    Positions pos;

    //Directions for the bot
    final double FORWARD = 0;
    final double BACKWARD = Math.toRadians(180);

    //Create the positions for the trajectories
    final Pose2d startToBlock = new Pose2d(0, 36, BACKWARD);
    final Pose2d blockToDeposit = new Pose2d(0, 0, FORWARD);

    //Create the trajectories variables
    public TrajectoryActionBuilder moveToBlock;
    public TrajectoryActionBuilder moveToDeposit;

    //The constructor to setup every class and trajectory
    public OutreachAuto(PinpointDrive drive, Pose2d initialPose, Devices dev){
        //Class setup
        this.drive = drive;
        this.initialPose = initialPose;

        this.dev = dev;
        arm = new Arm(dev);
        claw = new Claw(dev);
        pos = new Positions();

        //Trajectory #1
        moveToBlock = drive.actionBuilder(initialPose)
                .afterTime(0, claw.setProngs(pos.open))
                .afterTime(0.5, arm.setPos(pos.grabAbove))
                .afterTime(0.5, claw.setPos(pos.grabAbove))
                .afterTime(3.0, arm.setPos(pos.grabAboveLow))
                .afterTime(3.5, claw.setProngs(pos.closed))
                .setTangent(FORWARD)
                .splineToLinearHeading(startToBlock, FORWARD);

        //Trajectory #2
        moveToDeposit = moveToBlock.endTrajectory().fresh()
                .afterTime(0, arm.setSlides(0))
                .afterTime(0, claw.setPos(pos.grabMiddle))
                .afterTime(1.5, arm.setPos(2100, 2000))
                .afterTime(1.5, claw.setPos(pos.grabMiddle))
                .setTangent(BACKWARD)
                .splineToLinearHeading(blockToDeposit, BACKWARD)
                .afterTime(0.5, claw.setProngs(pos.open));
    }

    //The function to run the created trajectories
    public void runTrajectory(){
        Actions.runBlocking(
                new SequentialAction(
                        moveToBlock.build(),
                        new SleepAction(.25),
                        moveToDeposit.build(),
                        new SleepAction(.5)
                )
        );
    }
}
