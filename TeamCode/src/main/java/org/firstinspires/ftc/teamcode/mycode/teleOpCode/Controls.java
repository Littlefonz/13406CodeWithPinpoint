package org.firstinspires.ftc.teamcode.mycode.teleOpCode;

//Imports
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    ElapsedTime timer = new ElapsedTime();

    //Gamepad values
    boolean x = false;

    //Set the constructor (sets these values whenever the class is first called)
    public Controls(Gamepad gamepad1, Gamepad gamepad2, Devices dev) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.dev = dev;

        pos = new Positions();
        fod = new FOD(dev);
        arm = new Arm(dev);
        claw = new Claw(dev);
        selector = new ListSelector(new String[]{"Specimen", "Sample", "Hang"});
    }

    //Runs controller 1 code (start + a)
    public void controller1() throws InterruptedException {
        //Intake/Outtake
        if (gamepad1.left_trigger > pos.gamepadThreshold) {
            Actions.runBlocking(claw.setProngs(pos.open));
        } else if (gamepad1.right_trigger > pos.gamepadThreshold) {
            Actions.runBlocking(claw.setProngs(pos.closed));
        }

        //Arm Controls
        if (gamepad1.dpad_up) {
            arm.adjust(dev.armAngle, pos.adjustVelocity);
        } else if (gamepad1.dpad_down) {
            arm.adjust(dev.armAngle, -pos.adjustVelocity);
        } else {
            dev.armAngle.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        }

        //Slide Controls
        if (gamepad1.dpad_left) {
            arm.adjust(dev.slides, pos.maxVelocity);
        } else if (gamepad1.dpad_right) {
            arm.adjust(dev.slides, -pos.maxVelocity);
        } else {
            dev.slides.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
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

        //Reset slides
        if (gamepad1.back){
            dev.slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        //Arm + Claw Set Positions
        selector.run(gamepad1.left_bumper, gamepad1.right_bumper);
        if (selector.getChoice().equals("Specimen")) {
            //Claw + Arm Controls
            if (gamepad1.b) {
                Actions.runBlocking(arm.setPos(pos.grabAbove));
                Actions.runBlocking(claw.setPos(pos.grabAbove));
                Actions.runBlocking(claw.setProngs(pos.open));
            }
            if (gamepad1.x) {
                if (timer.time() > 1.25){
                    timer.reset();
                }
                else if (timer.time() > .7){
                    Actions.runBlocking(arm.setArm(pos.grabAbove[0][1]));
                }
                else if(timer.time() > .6){
                    Actions.runBlocking(claw.setProngs(pos.closed));
                }
                else if (timer.time() > 0){
                    Actions.runBlocking(arm.setArm(pos.grabAboveLow[0][1], 1250));
                }
            }
            if (gamepad1.a) {
                Actions.runBlocking(claw.setPos(pos.grabMiddle));
                Actions.runBlocking(arm.setSlides(pos.grabMiddle[0][0]));
                if(timer.time() > .75){
                    timer.reset();
                }else if (timer.time() > .15){
                    Actions.runBlocking(arm.setArm(pos.grabMiddle[0][1], 1000));
                }
            }
            //High Rung
            if (gamepad1.y){
                Actions.runBlocking(arm.setPos(pos.highRung));
                Actions.runBlocking(claw.setPos(pos.highRung));
            }
            if (gamepad1.back) {
                Actions.runBlocking(arm.setSlides(pos.slidesIn));
            }
            if (gamepad1.start){
                Actions.runBlocking(arm.setSlides(pos.slidesOut));
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
            }
            if (gamepad1.y) {
                Actions.runBlocking(arm.setPos(pos.highBasket));
                Actions.runBlocking(claw.setPos(pos.highBasket));
            }
            if (gamepad1.a) {
                Actions.runBlocking(claw.setPos(pos.grabMiddle));
                Actions.runBlocking(arm.setSlides(pos.grabMiddle[0][0]));
                if(timer.time() > .75){
                    timer.reset();
                }else if (timer.time() > .15){
                    Actions.runBlocking(arm.setArm(pos.grabMiddle[0][1], 1000));
                }
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
