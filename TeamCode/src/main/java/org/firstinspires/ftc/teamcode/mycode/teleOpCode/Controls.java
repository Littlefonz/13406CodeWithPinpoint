package org.firstinspires.ftc.teamcode.mycode.teleOpCode;

//Imports
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.mycode.robotSetup.Arm;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Claw;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.ListSelector;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Positions;

public class Controls {
    //Declare input variables to eventually set values to
    Gamepad gamepad1;
    Gamepad gamepad2;
    Devices dev;
    Positions pos;
    Claw claw;
    Arm arm;
    public FOD fod;
    public ListSelector selector;

    //Set the constructor (sets these values whenever the class is first called)
    public Controls(Gamepad gamepad1, Gamepad gamepad2, Devices dev) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.dev = dev;

        pos = new Positions();
        fod = new FOD(dev);
        arm = new Arm(dev);
        claw = new Claw(dev);
        selector = new ListSelector(gamepad1, new String[]{"Specimen", "Sample", "Hang"});
    }

    //Runs controller 1 code (start + a)
    public void controller1() {
        //Intake/Outtake
        if (gamepad1.left_trigger > pos.gamepadThreshold) {
            Actions.runBlocking(claw.intakeSpeed(pos.maxPower));
        } else if (gamepad1.right_trigger > pos.gamepadThreshold) {
            Actions.runBlocking(claw.intakeSpeed(-pos.maxPower));
        } else {
            Actions.runBlocking(claw.intakeSpeed(pos.stopDevice));
        }

        //Arm Controls
        if (gamepad1.dpad_up) {
            arm.adjust(dev.armAngle, pos.adjustVelocity);
        } else if (gamepad1.dpad_down) {
            arm.adjust(dev.armAngle, -pos.adjustVelocity);
        } else {
            dev.armAngle.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        }

        //GoRail Controls
        if (gamepad1.dpad_left) {
            arm.adjust(dev.goRail, pos.maxVelocity);
        } else if (gamepad1.dpad_right) {
            arm.adjust(dev.goRail, -pos.maxVelocity);
        } else {
            dev.goRail.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        }

        //Claw Rotation Controls
        if (gamepad1.right_stick_x > pos.gamepadThreshold) {
            claw.adjust(dev.clawRotation, pos.clawAdjustment);
        } else if (gamepad1.right_stick_x < -pos.gamepadThreshold) {
            claw.adjust(dev.clawRotation, -pos.clawAdjustment);
        }

        //Claw Wrist Controls
        if (-gamepad1.left_stick_y > pos.gamepadThreshold) {
            claw.adjust(dev.clawWrist, pos.clawAdjustment);
        } else if (-gamepad1.left_stick_y < -pos.gamepadThreshold) {
            claw.adjust(dev.clawWrist, -pos.clawAdjustment);
        }

        //Center chamber grabbing
        if (gamepad1.back){
            Actions.runBlocking(arm.setPos(pos.grabAboveLow));
        }
        if(gamepad1.right_stick_button){
            Actions.runBlocking(arm.setPos(pos.grabLow2));
            Actions.runBlocking(claw.setPos(pos.grabLow2));
        }

        //Arm + Claw Set Positions
        selector.inputs(gamepad1.left_bumper, gamepad1.right_bumper);
        if (selector.getChoice().equals("Specimen")) {
            //Claw + Arm Controls
            if (gamepad1.b) {
                Actions.runBlocking(arm.setPos(pos.grabAbove));
                Actions.runBlocking(claw.setPos(pos.grabAbove));
            }
            if (gamepad1.x) {
                Actions.runBlocking(arm.setPos(pos.grabAboveLow));
                Actions.runBlocking(claw.setPos(pos.grabAboveLow));
            }
            if (gamepad1.a) {
                Actions.runBlocking(arm.setPos(pos.grabLow));
                Actions.runBlocking(claw.setPos(pos.grabLow));
            }
            //High Rung
            if (gamepad1.y){
                Actions.runBlocking(arm.setPos(pos.highRung));
                Actions.runBlocking(claw.setPos(pos.highRung));
            }
            if (gamepad1.guide) {
                Actions.runBlocking(arm.setPos(pos.highRungPrep));
                Actions.runBlocking(claw.setPos(pos.highRungPrep));
            }
            if (gamepad1.start){
                Actions.runBlocking(arm.setPos(pos.highRung90D));
                Actions.runBlocking(claw.setPos(pos.highRung90D));
            }
        } else if (selector.getChoice().equals("Sample")) {
            //Claw + Arm Controls
            if (gamepad1.b) {
                Actions.runBlocking(arm.setPos(pos.grabAbove));
                Actions.runBlocking(claw.setPos(pos.grabAbove));
            }
            if (gamepad1.a) {
                Actions.runBlocking(arm.setPos(pos.grabLow));
                Actions.runBlocking(claw.setPos(pos.grabLow));
            }
            if (gamepad1.x) {
                Actions.runBlocking(arm.setPos(pos.grabAboveLow));
                Actions.runBlocking(claw.setPos(pos.grabAboveLow));
            }
            if (gamepad1.y) {
                Actions.runBlocking(arm.setPos(pos.lowBasket));
                Actions.runBlocking(claw.setPos(pos.lowBasket));
            }
        } else if (selector.getChoice().equals("Hang")) {
            //Claw + Arm Controls
            if (gamepad1.x) {
                Actions.runBlocking(arm.setPos(pos.hangPrepPos));
                Actions.runBlocking(claw.setPos(pos.hangPrepPos));
            }
            if (gamepad1.a) {
                Actions.runBlocking(arm.setPos(pos.idle));
                Actions.runBlocking(claw.setPos(pos.idle));
            }
            } else {
            throw new IllegalStateException("Unexpected value: " + selector.getChoice());
        }
    }

    //Runs controller 2 code (start + b)
    public void controller2() {
        double speed = pos.fodFast;
        //Drive Config
        if (gamepad2.left_trigger > pos.gamepadThreshold) {
            speed = pos.fodSlowest;
        } else if (gamepad2.right_trigger > pos.gamepadThreshold) {
            speed = pos.fodSlower;
        }

        //Imu
        if (gamepad2.y) {
            fod.resetOrientation();
        }

        //Drive
        fod.FODDrive(-gamepad2.left_stick_y, gamepad2.left_stick_x, gamepad2.right_stick_x, /*gamepad2.right_stick_y,*/ speed);
    }
}
