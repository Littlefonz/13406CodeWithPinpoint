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
    public Action setPos(double extenstion, double angle) {
        return new setPos((int) angle, (int) extenstion);
    }

    public Action setPos(double[][] pos) {
        return new setPos((int) pos[0][1], (int) pos[0][0]);
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
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setVelocity(speed);
        motor.setTargetPosition(motor.getCurrentPosition());
    }

    //--------------------------------------------------------//

    public class setPos implements Action {
        int slidesExtenstion;
        int angle;

        public setPos(int angle, int slidesExtenstion) {
            this.slidesExtenstion = slidesExtenstion;
            this.angle = angle;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            dev.armAngle.setPower(1);
            dev.armAngle.setTargetPosition(angle);
            dev.slides.setPower(1);
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
        double power;

        public setSlides(int angle, double power) {
            this.angle = angle;
            this.power = power;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            dev.slides.setVelocity(power);
            dev.slides.setTargetPosition(angle);
            return false;
        }
    }
}

