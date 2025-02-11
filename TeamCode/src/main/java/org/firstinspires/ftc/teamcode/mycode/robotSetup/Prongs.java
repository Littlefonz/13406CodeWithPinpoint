package org.firstinspires.ftc.teamcode.mycode.robotSetup;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

public class Prongs {
    Devices dev;

    public Prongs(Devices dev){
        this.dev = dev;
    }

    //--------------------------------------------------------//
    public Action setPos(double[] pos) {
        return new setPos(pos[0], pos[1]);
    }

    public Action setPos(double rightClawPos, double leftClawPos) {
        return new setPos(rightClawPos, leftClawPos);
    }

    public void adjust (Servo servo, double rotation) {
        servo.setPosition(servo.getPosition() + rotation);
    }
    //--------------------------------------------------------//

    public class setPos implements Action {
        double leftPos;
        double rightPos;

        public setPos(double leftPos, double rightPos) {
            this.leftPos = leftPos;
            this.rightPos = rightPos;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            dev.clawRotation.setPosition(leftPos);
            dev.clawWrist.setPosition(rightPos);
            return false;
        }
    }
}
