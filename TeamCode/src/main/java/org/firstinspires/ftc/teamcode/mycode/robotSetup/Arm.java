package org.firstinspires.ftc.teamcode.mycode.robotSetup;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {
    Devices dev;

    public Arm(Devices dev) {
        this.dev = dev;
    }

    //--------------------------------------------------------//
    public Action setPos(double goRailExtenstion, double angle, double slidesExtenstion) {
        return new setPos((int) goRailExtenstion, (int) angle, (int) slidesExtenstion);
    }

    public Action setPos(double extenstion, double angle) {
        return new setPos((int) extenstion, (int) angle, 0);
    }

    public Action setPos(double[][] pos) {
        return new setPos((int) pos[0][1], (int) pos[0][0], (int) pos[0][2]);
    }

    public Action setArm(double pos){
        return new setArm((int) pos, 10000);
    }

    public Action setArm(double pos, int velocity){
        return new setArm((int) pos, velocity);
    }

    public Action setSlides(double pos){
        return new setSlides((int) pos, 10000);
    }

    public Action setSlides(double pos, int velocity){
        return new setSlides((int) pos, velocity);
    }

    public void adjust(DcMotorEx motor, int speed){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); motor.setVelocity(speed);
    }

    //--------------------------------------------------------//

    public class setPos implements Action {
        int goRailExtenstion;
        int slidesExtenstion;
        int angle;

        public setPos(int goRailExtenstion, int angle, int slidesExtenstion) {
            this.goRailExtenstion = goRailExtenstion;
            this.slidesExtenstion = slidesExtenstion;
            this.angle = angle;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            dev.goRail.setVelocity(10000);
            dev.goRail.setTargetPosition(goRailExtenstion);
            dev.armAngle.setVelocity(10000);
            dev.armAngle.setTargetPosition(angle);
            dev.slides.setVelocity(10000);
            dev.slides.setTargetPosition(slidesExtenstion);
            return false;
        }
    }

    public class setArm implements Action {
        int angle;
        int velocity;

        public setArm(int angle, int velocity) {
            this.angle = angle;
            this.velocity = velocity;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            dev.armAngle.setVelocity(velocity);
            dev.armAngle.setTargetPosition(angle);
            return false;
        }
    }

    public class setSlides implements Action {
        int angle;
        int velocity;

        public setSlides(int angle, int velocity) {
            this.angle = angle;
            this.velocity = velocity;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            dev.slides.setVelocity(velocity);
            dev.slides.setTargetPosition(angle);
            return false;
        }
    }
}

