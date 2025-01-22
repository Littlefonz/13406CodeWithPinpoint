package MyCode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    DcMotorEx armAngle;
    DcMotorEx goRail;

    public Arm(HardwareMap hardwareMap) {
        armAngle = hardwareMap.get(DcMotorEx.class, "armAngle");
        goRail = hardwareMap.get(DcMotorEx.class, "goRail");
        armAngle.setDirection(DcMotorSimple.Direction.FORWARD);
        goRail.setDirection(DcMotorSimple.Direction.FORWARD);

        armAngle.setTargetPosition(0);
        armAngle.setVelocity(2500);
        armAngle.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armAngle.setDirection(DcMotorSimple.Direction.REVERSE);

        goRail.setTargetPosition(0);
        goRail.setVelocity(5000);
        goRail.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public class setPos implements Action {
        int extenstion;
        int angle;

        public setPos(int extenstion, int angle) {
            this.extenstion = extenstion;
            this.angle = angle;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            goRail.setVelocity(5000);
            goRail.setTargetPosition(extenstion);
            armAngle.setVelocity(2500);
            armAngle.setTargetPosition(angle);
            return false;
        }
    }
    public Action setPos(int extenstion, int angle) {
        return new setPos(extenstion, angle);
    }
}

