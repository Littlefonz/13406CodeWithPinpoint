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
    public Action setPos(double extenstion, double angle) {return new setPos((int) extenstion, (int) angle);}
    public Action setPos(double[][] pos) {return new setPos((int) pos[0][1], (int) pos[0][0]);}
    public void adjust(DcMotorEx motor, int speed){motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); motor.setVelocity(speed);}
    //--------------------------------------------------------//
    public class setPos implements Action {
        int extenstion;
        int angle;

        public setPos(int extenstion, int angle) {
            this.extenstion = extenstion;
            this.angle = angle;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            dev.goRail.setVelocity(10000);
            dev.goRail.setTargetPosition(extenstion);
            dev.armAngle.setVelocity(10000);
            dev.armAngle.setTargetPosition(angle + 150);
            return false;
        }
    }
}

