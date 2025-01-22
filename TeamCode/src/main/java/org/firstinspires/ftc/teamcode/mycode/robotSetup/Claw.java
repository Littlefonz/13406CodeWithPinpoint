package org.firstinspires.ftc.teamcode.mycode.robotSetup;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;

public class Claw {
    Devices dev;

    public Claw(Devices dev){
        this.dev = dev;
    }
    //--------------------------------------------------------//
    public Action intakeSpeed(double speed) {return new intakeSpeed(speed);}
    public Action setPos(double rotation, double wrist) {return new setPos(rotation, wrist);}
    public Action setPos(double[][] pos) {return new setPos(pos[1][0], pos[1][1]);}
    public void adjust(Servo servo, double rotation){servo.setPosition(servo.getPosition() + rotation);}
    //--------------------------------------------------------//
    public class intakeSpeed implements Action  {
        double speed;

        public intakeSpeed(double speed) {
            this.speed = speed;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            for (CRServo crServo : Arrays.asList(dev.intake1, dev.intake2)) {
                crServo.setPower(speed);
            }
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
