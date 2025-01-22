package org.firstinspires.ftc.teamcode.mycode;

/* This file will be the main file of the program, running every other file to make the whole bot run.
 *
 * Specifically, this code will include:
 * - A path to every file/class
 * - Links every part of the bot, making every file run together at once
 */

//Imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.mycode.robotSetup.Arm;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;

@TeleOp(name="Reset Encoders", group="Linear OpMode")
public class ResetEncoders extends LinearOpMode {
    @Override public void runOpMode() throws InterruptedException {
        //Import the other files/classes
        Devices dev = new Devices(hardwareMap);
        Arm arm = new Arm(dev);
        //Start the Bot
        waitForStart();

        //Run the TeleOp Loop
        while (opModeIsActive()){
            //Adjustment speed
            int speed = 10000;
            if(gamepad1.left_trigger == 1){
                speed = 500;
            }else if(gamepad1.right_trigger == 1){
                speed = 1000;
            }

            //Arm adjustments
            if(gamepad1.dpad_up){
                arm.adjust(dev.armAngle, speed);
            } else if (gamepad1.dpad_down) {
                arm.adjust(dev.armAngle, -speed);
            } else {
                dev.armAngle.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            //GoRail Adjustments
            if (gamepad1.dpad_left) {
                arm.adjust(dev.goRail, speed);
            } else if (gamepad1.dpad_right) {
                arm.adjust(dev.goRail, -speed);
            } else {
                dev.goRail.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            //Reset Encoders
            if (gamepad1.y){
                dev.armAngle.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                dev.goRail.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            //Telemetry
            telemetry.addData("Arm rotation in TICKS:  ", dev.armAngle.getCurrentPosition());
            telemetry.addData("GoRail extension in TICKS:  ", dev.goRail.getCurrentPosition());

            telemetry.addData("\nClaw Rotation Pos:  ", dev.clawRotation.getPosition());
            telemetry.addData("Claw Wrist Pos:  ", dev.clawWrist.getPosition());

            telemetry.update();
        }
    }
}
