package org.firstinspires.ftc.teamcode.mycode.autoCode;

//Imports
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Arm;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Claw;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Positions;

public class LeftAutoTrajectories {
    //Declare classes as empty variables
    PinpointDrive drive;
    Pose2d initialPose;
    Devices dev;
    Arm arm;
    Claw claw;
    Positions pos;

    /** Positions on the left side **/

    //Directions for the bot
    final double FORWARD = 0;
    final double BACKWARD = Math.toRadians(180);
    final double RIGHT = Math.toRadians(-90);

    //Create the positions for the trajectories
    final Pose2d basketPOS = new Pose2d(61, 61, Math.toRadians(-135));
    final Pose2d sample1POS = new Pose2d(36, 26, BACKWARD);
    final Pose2d sample2POS = new Pose2d(47, 26, BACKWARD);
    final Pose2d sample3POS = new Pose2d(58, 26, BACKWARD);
    final Pose2d parkPOS = new Pose2d(20, 10, FORWARD);

    //Create the trajectories variables
    public TrajectoryActionBuilder Start_Basket;
    public TrajectoryActionBuilder Basket_Sample1;
    public TrajectoryActionBuilder Basket_Sample2;
    public TrajectoryActionBuilder Basket_Sample3;
    public TrajectoryActionBuilder Samples_Basket;
    public TrajectoryActionBuilder Basket_Park;

    //The constructor to setup every class and trajectory
    public LeftAutoTrajectories(PinpointDrive drive, Pose2d initialPose, Devices dev) {
        //Class setup
        this.drive = drive;
        this.initialPose = initialPose;

        this.dev = dev;
        arm = new Arm(dev);
        claw = new Claw(dev);
        pos = new Positions();

        //Trajectory #1
        Start_Basket = drive.actionBuilder(initialPose)
                .afterTime(0, arm.setPos(pos.highBasket))
                .afterTime(0, claw.setPos(pos.highBasket))
                .waitSeconds(2.5)
                .setTangent(FORWARD)
                .lineToXSplineHeading(54, FORWARD)
                .afterTime(0, claw.setProngs(pos.openXL));

        //Trajectory #2
        Basket_Sample1 = Start_Basket.endTrajectory().fresh()
                .afterTime(.75, claw.setPos(.65, .07))
                .afterTime(.75, arm.setPos(700, 200))
                .afterTime(3.5, claw.setProngs(pos.closed))
                .setTangent(BACKWARD)
                .splineToLinearHeading(sample1POS, Math.toRadians(-45),
                        new TranslationalVelConstraint(25.0));

        //Trajectory #3, #5, #7
        Samples_Basket = Basket_Sample1.endTrajectory().fresh()
                .afterTime(0, arm.setPos(2100, 3350))
                .afterTime(0, claw.setPos(pos.highBasket))
                .afterTime(4.0, claw.setProngs(pos.openXL))
                .setTangent(Math.toRadians(100))
                .splineToLinearHeading(basketPOS, Math.toRadians(-315),
                        new TranslationalVelConstraint(12.5));

        //Trajectory #4
        Basket_Sample2 = Samples_Basket.endTrajectory().fresh()
                .afterTime(.75, claw.setPos(.65, .07))
                .afterTime(.75, arm.setPos(700, 200))
                .afterTime(3.2, claw.setProngs(pos.closed))
                .splineToLinearHeading(sample2POS, FORWARD,
                        new TranslationalVelConstraint(25.0));

        //Trajectory #6
        Basket_Sample3 = Samples_Basket.endTrajectory().fresh()
                .afterTime(.75, claw.setPos(.65, .07))
                .afterTime(.75, arm.setPos(700, 200))
                .afterTime(3.2, claw.setProngs(pos.closed))
                .splineToLinearHeading(sample3POS, FORWARD,
                        new TranslationalVelConstraint(20.0));

        //Trajectory #8
        Basket_Park = drive.actionBuilder(basketPOS)
                .afterTime(.75, claw.setProngs(pos.closed))
                .afterTime(.75, claw.setPos(pos.idle))
                .afterTime(.75, arm.setPos(pos.idle))
                .afterTime(2.5, arm.setPos(FORWARD, 2000))
                .setTangent(RIGHT)
                .splineToLinearHeading(parkPOS, BACKWARD,
                        new TranslationalVelConstraint(35));
    }

    //The function to run the created trajectories
    public void runTrajectory(){
        Actions.runBlocking(
                new SequentialAction(
                        Start_Basket.build(),
                        Basket_Sample1.build(),
                        Samples_Basket.build(),
                        Basket_Sample2.build(),
                        Samples_Basket.build(),
                        Basket_Sample3.build(),
                        Samples_Basket.build(),
                        Basket_Park.build(),
                        new SleepAction(10)
                )
        );
    }
}
