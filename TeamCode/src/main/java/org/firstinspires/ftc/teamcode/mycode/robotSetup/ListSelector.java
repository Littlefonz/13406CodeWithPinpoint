package org.firstinspires.ftc.teamcode.mycode.robotSetup;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ListSelector {
    Gamepad gamepad1;
    public String[] list;
    ElapsedTime timer = new ElapsedTime();

    public ListSelector(Gamepad gamepad1, String[] list){
        this.gamepad1 = gamepad1;
        this.list = list;
    }

    int indexChoice = 0;

    public void inputs(boolean button1, boolean button2){
        if(timer.time() > .250){
            if(button1 && indexChoice > 0){
                indexChoice--;
                timer.reset();
            }else if(button2 && indexChoice < list.length - 1){
                indexChoice++;
                timer.reset();
            }
        }
    }

    public String getChoice(){
        return list[indexChoice];
    }
}
