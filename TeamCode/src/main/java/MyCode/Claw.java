package MyCode;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;

public class Claw {
    public CRServo intake1;
    public CRServo intake2;
    public Servo clawRotation;
    public Servo clawWrist;

    public Claw(HardwareMap hardwareMap){
        intake1 = hardwareMap.get(CRServo.class, "intake1");
        intake2 = hardwareMap.get(CRServo.class, "intake2");
        clawRotation = hardwareMap.get(Servo.class, "clawRotation");
        clawWrist = hardwareMap.get(Servo.class, "clawWrist");
    }

    public Action intakeSpeed(double speed) throws InterruptedException {
        return new intakeSpeed(speed);
    }

    public class intakeSpeed implements Action  {
        double speed;

        public intakeSpeed(double speed) {
            this.speed = speed;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            for (CRServo crServo : Arrays.asList(intake1, intake2)) {
                crServo.setPower(speed);
            }
            return false;
        }
    }

    public Action setPos(double rotation, double wrist) {
        return new setPos(rotation, wrist);
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
            clawRotation.setPosition(rotation);
            clawWrist.setPosition(wrist);
            return false;
        }
    }
}
