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
import roadrunner.PinpointDrive;

/* Note To Self:
 * This could have easily been made into more separate files for
 * organization, but I lacked the time and knowledge to do so.
 */

//Start of the autonomous
@Autonomous(name="We'll be Gamin'", group="Autonomous", preselectTeleOp="We're Gaming")
public class RunAuto extends LinearOpMode {

    //Declare classes as empty variables
    Devices dev;
    Pose2d initialPose;
    PinpointDrive drive;
    LeftAutoTrajectories trajectoryL;
    RightAutoTrajectories trajectoryR;

    @Override
    public void runOpMode() throws InterruptedException {
        //Import Devices
        dev = new Devices(hardwareMap);

        //Import a random number generator
        Random rand = new Random();

        //Import 2 separate timers
        ElapsedTime timer = new ElapsedTime();
        ElapsedTime timer2 = new ElapsedTime();

        //Setup a list selector
        ListSelector selector = new ListSelector(new String[]{"Left Side", "Right Side"});

        //Random messages to display before the autonomous starts
        String[] waitingText = {"Vrej", "Start already...", "103 point auto when?", "13406 < DeliBot", "Seed :O",
                "I like splash text", "Don't throw. Or else...", "7571 is ugh... ugh...", ":D", ":3", ":l",
                "RIP better FOD 2024 - 2025", "If we lose, you owe me a soda", "I'm bored", "I'm still bored",
                "This is reminding me of Minecraft", "#13406And7571ToWorlds (real?)", "There's no text here...",
                "You ready?", "We <3 FTC", "Why can't we have more $$$ ):", "Pheonix was NOT here ;)", "James was here :)",
                "o/", "\\o/", "o7", "W bot", "The Camera is cameraing (trust)", "Odometry is BROKEN"
        };

        //Declare the first random string
        int waitingTextIndex = rand.nextInt(waitingText.length);

        //Declare the decision for whether or not the autonomous has been selected and locked
        boolean lockedDecision = false;

        //Declare the status of the autonomous selection
        String status = "(Unlocked)";

        //Set the initial positions of the servo's
        dev.clawWrist.setPosition(.85);
        dev.prong1.setPosition(.49);
        dev.prong2.setPosition(.49);

        //While the start button is not pressed
        while(!isStarted()){
            //Autonomous selection navigation
            if (gamepad1.left_bumper || gamepad1.right_bumper && !lockedDecision){
                selector.run(gamepad1.left_bumper, gamepad1.right_bumper);
            }
            //Autonomous selection decision
            if (gamepad1.a && !lockedDecision){
                lockedDecision = true;
                status = "(Locked)";
                loadTrajectory(selector.getChoice());
            }
            //Display a waiting string to occupy the user before start is pressed
            String waiting = " - Waiting    - ";
            if(timer2.time() > 1.6){
                timer2.reset();
            }else if(timer2.time() > 1.2) {
                waiting = " - Waiting... - ";
            }else if(timer2.time() > 0.8){
                waiting = " - Waiting..  - ";
            }else if(timer2.time() > 0.4){
                waiting = " - Waiting.   - ";
            }
            //Get a new random waiting string every 8 seconds
            if(timer.time() > 8){
                waitingTextIndex = rand.nextInt(waitingText.length);
                timer.reset();
            }
            //Update the screen with new telemetry
            telemetry.addLine("Auto choice :\n< " + selector.getChoice() +  " > " + status);
            telemetry.addLine("---------------------------------------------------------");
            telemetry.addLine(waiting);
            telemetry.addLine("\n" + waitingText[waitingTextIndex]);
            telemetry.update();
        }

        //Run the autonomous path coresponding to the selected side
        switch (selector.getChoice()) {
            case "Left Side":
                trajectoryL.runTrajectory();
                break;

            case "Right Side":
                trajectoryR.runTrajectory();
                break;
        }

        //Telemetry to display while the autonomous is running
        telemetry.addLine("4 specimen auto in progress...");
        telemetry.update();
    }

    //Load the trajectory coresponding to the selected side
    void loadTrajectory(String trajectory){
        switch (trajectory) {
            case "Left Side": //Setup and run the left side autonomous
                initialPose = new Pose2d(40, 66, Math.toRadians(0));
                drive = new PinpointDrive(hardwareMap, initialPose);
                trajectoryL = new LeftAutoTrajectories(drive, initialPose, dev);
                break;

            case "Right Side": //Setup and run the right side autonomous
                initialPose = new Pose2d(-4, 64, Math.toRadians(90)); //-64, 60, Math.toRadians(90) //-24, 62, Math.toRadians(135)
                drive = new PinpointDrive(hardwareMap, initialPose);
                trajectoryR = new RightAutoTrajectories(drive, initialPose, dev);
                break;

            default: //Fail state while locating a trajectory
                throw new IllegalStateException("Unexpected trajectory value: " + trajectory);
        }
    }
}