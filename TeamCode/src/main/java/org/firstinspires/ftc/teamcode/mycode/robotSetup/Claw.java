package org.firstinspires.ftc.teamcode.mycode.robotSetup;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;

public class Claw {
    Devices dev;

    public Claw(Devices dev){
        this.dev = dev;
    }

    //--------------------------------------------------------//
    public Action setProngs(double pos) {
        return new setProngs(pos, pos);
    }

    public Action setPos(double rotation, double wrist) {
        return new setPos(rotation, wrist);
    }

    public Action setPos(double[][] pos) {
        return new setPos(pos[1][0], pos[1][1]);
    }

    public Action setPos(double[] pos) {
        return new setPos(pos[0], pos[1]);
    }

    public Action setProngs(double[] pos){
        return new setProngs(pos[0], pos[1]);
    }

    public void adjust(Servo servo, double rotation){
        servo.setPosition(servo.getPosition() + rotation);
    }
    //--------------------------------------------------------//

    public class setProngs implements Action  {
        double prongPos1;
        double prongPos2;

        public setProngs(double prongPos1, double prongPos2) {
            this.prongPos1 = prongPos1;
            this.prongPos2 = prongPos2;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            dev.prong1.setPosition(prongPos1);
            dev.prong2.setPosition(prongPos2);
            return false;
        }
    }

    public class setPos implements Action {
        double rotation;
        double wrist;

        public setPos(double rotation, double wrist) {
            this.rotation = rotation;
            this.wrist = wrist;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            dev.clawRotation.setPosition(rotation);
            dev.clawWrist.setPosition(wrist);
            return false;
        }
    }
}
