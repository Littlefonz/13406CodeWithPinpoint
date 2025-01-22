package org.firstinspires.ftc.teamcode.mycode;

//Imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mycode.teleOpCode.Controls;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;

@TeleOp(name="We're Gaming", group="Linear OpMode")
public class RunTeleOp extends LinearOpMode {
    @Override public void runOpMode() throws InterruptedException {
        //Start the Bot
        waitForStart();

        //Import the other files/classes
        Devices dev = new Devices(hardwareMap);
        Controls gpad = new Controls(gamepad1, gamepad2, dev);

        //Run the TeleOp Loop
        while (opModeIsActive()){
            //Run inputs if the controller isn't at rest
            gpad.controller1();
            gpad.controller2();

            //Telemetry
            telemetry.addData("Mode: ", gpad.selector.getChoice());

            telemetry.addData("\nArm rotation in TICKS:  ", dev.armAngle.getCurrentPosition());
            telemetry.addData("GoRail extension in TICKS:  ", dev.goRail.getCurrentPosition());

            telemetry.addData("\nClaw Rotation Pos:  ", dev.clawRotation.getPosition());
            telemetry.addData("Claw Wrist Pos:  ", dev.clawWrist.getPosition());

            telemetry.addData("\nBot Orientation:  ", dev.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
            telemetry.addData("Joystick Orientation:  ", gpad.fod.joystickOrientation);
            telemetry.addData("LF Drive:  ", dev.LFDrive.getPower());
            telemetry.addData("LB Drive:  ", dev.LBDrive.getPower());
            telemetry.addData("RF Drive:  ", dev.RFDrive.getPower());
            telemetry.addData("RB Drive:  ", dev.RBDrive.getPower());

            //Update telemetry
            telemetry.update();
        }
    }
}
