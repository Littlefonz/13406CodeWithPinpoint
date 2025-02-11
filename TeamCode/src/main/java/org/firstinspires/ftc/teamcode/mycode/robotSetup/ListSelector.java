package org.firstinspires.ftc.teamcode.mycode.robotSetup;

import com.qualcomm.robotcore.util.ElapsedTime;

public class ListSelector {
    public String[] list;
    ElapsedTime timer = new ElapsedTime();

    public ListSelector(String[] list){
        this.list = list;
    }

    int indexChoice = 0;

    public void run(boolean button1, boolean button2){
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


//package org.firstinspires.ftc.teamcode.mycode.robotSetup;
//
//public class ListSelector {
//    //Lists
//    int listCount = 0;
//    public String[] selectedList = {};
//    public String[][] listStorage = {{}};
//
//    //Indexes
//    int indexChoice = 0;
//    int listsIndexChoice = 0;
//
//    //First List Setup
//    public ListSelector(String[] list){
//        listStorage[0] = list;
//        listCount++;
//    }
//
//    // Single List Usage
//    public void run(boolean navLeft, boolean navRight) {
//        if(listStorage[0].length > 0){
//            runMain(navLeft, navRight, false, false);
//        }
//    }
//
//    // Multi List Usage
//    public void run(boolean navLeft, boolean navRight, boolean navDown, boolean navUp) {
//        if(listStorage[0].length > 0){
//            runMain(navLeft, navRight, navDown, navUp);
//        }
//    }
//
//    //Button Values
//    boolean pressedButton1 = false;
//    boolean pressedButton2 = false;
//    boolean pressedButton3 = false;
//    boolean pressedButton4 = false;
//
//    /** Main Code **/
//
//    public void runMain(boolean navLeft, boolean navRight, boolean navDown, boolean navUp){
//        //Button 1 controls
//        if(navLeft && indexChoice > 0){
//            if(!pressedButton1){
//                pressedButton1 = true;
//                indexChoice--;
//            }
//        }else{
//            pressedButton1 = false;
//        }
//
//        //Button 2 controls
//        if(navRight && indexChoice < selectedList.length - 1){
//            if(!pressedButton2) {
//                pressedButton2 = true;
//                indexChoice++;
//            }
//        }else{
//            pressedButton2 = false;
//        }
//
//        //Button 3 controls
//        if(navDown && listsIndexChoice > 0){
//            if(!pressedButton3){
//                pressedButton3 = true;
//                listsIndexChoice--;
//            }
//        }else{
//            pressedButton3 = false;
//        }
//
//        //Button 4 controls
//        if(navUp && listsIndexChoice < listStorage.length - 1){
//            if(!pressedButton4) {
//                pressedButton4 = true;
//                listsIndexChoice++;
//            }
//        }else{
//            pressedButton4 = false;
//        }
//    }
//
//    //Add a list to the list storage
//    public void addList(String[] newList){
//        String[][] newStorage = new String[listCount + 1][];
//        System.arraycopy(listStorage, 0, newStorage, 0, listCount);
//        newStorage[listCount] = newList;
//        listStorage = newStorage;
//        listCount++;
//    }
//
//    //Return the selected list choice
//    public String getChoice(){
//        return listStorage[listsIndexChoice][indexChoice];
//    }
//
//    //Clear the list storage
//    public void clearAllLists(){
//        listStorage = null;
//    }
//}