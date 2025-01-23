package org.firstinspires.ftc.teamcode.mycode;

//Imports
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mycode.robotSetup.ListSelector;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;
import org.firstinspires.ftc.teamcode.mycode.autoCode.*;
import java.util.Random;

import org.firstinspires.ftc.teamcode.PinpointDrive;

@Autonomous(name="We'll be Gamin'", group="Autonomous", preselectTeleOp="We're Gaming")
public class RunAuto extends LinearOpMode {

    Devices dev = new Devices(hardwareMap);
    Pose2d initialPose;
    PinpointDrive drive;
    LeftAutoTrajectories trajectoryL;
    RightAutoTrajectories trajectoryR;

    @Override
    public void runOpMode() throws InterruptedException {
        ListSelector selector = new ListSelector(gamepad1, new String[]{"Left Side", "Right Side"});
        Random rand = new Random();
        ElapsedTime timer = new ElapsedTime();
        ElapsedTime timer2 = new ElapsedTime();
        String[] waitingText = {"Vrej", "Start already...", "103 point auto when?", "13406 < DeliBot", "Seed :O",
                "I like splash text", "Don't throw. Or else...", "7571 is ugh... ugh...", ":D", ":3", ":l",
                "RIP better FOD 2024 - 2025", "If we lose, you owe me a soda", "I'm bored", "I'm still bored",
                "This is reminding me of Minecraft", "#13406And7571ToWorlds (real?)", "There's no text here...",
                "You ready?", "We <3 FTC", "Why can't we have more $$$ ):", "Pheonix was NOT here ;)", "James was here :)",
                "o/", "\\o/", "o7", "W bot", "The Camera is cameraing (trust)", "Odometry is BROKEN"
        };

        int index = rand.nextInt(waitingText.length);
        boolean locked = false;
        String status = "(Unlocked)";

        while(!isStarted()){
            //Auto choice
            if (gamepad1.a && !locked){
                locked = true;
                status = "(Locked)";
                loadTrajectory(selector.getChoice());
            }
            if (gamepad1.left_bumper || gamepad1.right_bumper && !locked){
                selector.inputs(gamepad1.left_bumper, gamepad1.right_bumper);
            }
            //Waiting string
            String waiting = " - Waiting - ";
            if(timer2.time() > 1.6){
                timer2.reset();
            }else if(timer2.time() > 1.2) {
                waiting = " - Waiting... - ";
            }else if(timer2.time() > 0.8){
                waiting = " - Waiting.. - ";
            }else if(timer2.time() > 0.4){
                waiting = " - Waiting. - ";
            }
            //Random string
            if(timer.time() > 8){
                index = rand.nextInt(waitingText.length);
                timer.reset();
            }
            //Update screen
            telemetry.addLine("Auto choice :\n< " + selector.getChoice() +  " >" + status);
            telemetry.addLine("---------------------------------------------------------");
            telemetry.addLine(waiting);
            telemetry.addLine(waitingText[index]);
            telemetry.update();
        }
        //Running text
        telemetry.addLine("4 specimen auto in progress...");
        telemetry.update();

        switch (selector.getChoice()) {
            case "Left Side":
                trajectoryL.runTrajectory();
                break;

            case "Right Side":
                trajectoryR.runTrajectory();
                break;
        }
    }

    void loadTrajectory(String trajectory){
        switch (trajectory) {
            case "Left Side":
                //Setup and run the auto
                initialPose = new Pose2d(40, 66, Math.toRadians(-180));
                drive = new PinpointDrive(hardwareMap, initialPose);
                trajectoryL = new LeftAutoTrajectories(drive, initialPose, dev);
                break;

            case "Right Side":
                //Setup and run the auto
                initialPose = new Pose2d(-24, 62, Math.toRadians(135)); //-64, 60, Math.toRadians(90)
                drive = new PinpointDrive(hardwareMap, initialPose);
                trajectoryR = new RightAutoTrajectories(drive, initialPose, dev);
                break;

            default: //Fail
                throw new IllegalStateException("Unexpected value: " + trajectory);
        }
    }
}