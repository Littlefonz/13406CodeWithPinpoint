package org.firstinspires.ftc.teamcode.mycode;

//Imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Arm;
import org.firstinspires.ftc.teamcode.mycode.robotSetup.Devices;

//Start of the TeleOp
@TeleOp(name="Reset Encoders", group="Linear OpMode")
public class ResetEncoders extends LinearOpMode {
    @Override public void runOpMode() throws InterruptedException {
        //Import Devices
        Devices dev = new Devices(hardwareMap);

        //Import the Arm class
        Arm arm = new Arm(dev);

        //Wait for play to be pressed
        waitForStart();

        //Run the TeleOp Loop
        while (opModeIsActive()){
            //Adjustment speed values
            int speed = 10000;
            if(gamepad1.left_trigger >= 0.5){
                speed = 500;
            }else if(gamepad1.right_trigger >= 0.5){
                speed = 1000;
            }

            //Arm adjustments
            if(gamepad1.dpad_up){
                arm.adjust(dev.armAngle, speed);
            } else if (gamepad1.dpad_down) {
                arm.adjust(dev.armAngle, -speed);
            } else {
                dev.armAngle.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            //Slides adjustments
            if (gamepad1.dpad_left) {
                arm.adjust(dev.slides, speed);
            } else if (gamepad1.dpad_right) {
                arm.adjust(dev.slides, -speed);
            } else {
                dev.slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            //Reset encoders
            if (gamepad1.y){
                dev.armAngle.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                dev.slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            //Update the screen with new telemetry
            telemetry.addData("Arm rotation in TICKS:  ", dev.armAngle.getCurrentPosition());
            telemetry.addData("Slides extension in TICKS:  ", dev.slides.getCurrentPosition());
            telemetry.update();
        }
    }
}
