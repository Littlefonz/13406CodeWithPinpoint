package org.firstinspires.ftc.teamcode.mycode;

//Imports
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.mycode.autoCode.OutreachAuto;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;
import roadrunner.PinpointDrive;

//Start of the autonomous
@Autonomous(name = "Outreach :o")
public class RunOutreachAuto extends LinearOpMode {
    public void runOpMode(){
        //Create an initial starting position
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(0));

        //Create a drive class, information for the bot to follow your desired path
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        //Import Devices
        Devices dev = new Devices(hardwareMap);

        //Import the autonomous path
        OutreachAuto auto = new OutreachAuto(drive, initialPose, dev);

        //Setup/declare the autonomous path
        auto.setup();

        //Set the servo's to their initial position
        dev.clawWrist.setPosition(.85);
        dev.prong1.setPosition(.49);
        dev.prong2.setPosition(.49);

        //Wait for play to be pressed
        waitForStart();

        //Run the autonomous path
        auto.runAuto();
    }
}