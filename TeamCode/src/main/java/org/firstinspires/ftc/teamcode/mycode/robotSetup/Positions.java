package org.firstinspires.ftc.teamcode.mycode.robotSetup;

public final class Positions {

    /**Positions**/

    //Idle position                  //Angle //Extension //Rotation //Wrist
    public double[][] idle = {       {0,     0},         {.65,      1}};
    //Rung positions
    public double[][] lowRung = {    {600,   0},         {.3,       .3}};
    public double[][] highRung = {   {2700,  0},         {.29,      .3}};//{2475,  0},         {.96,      .4}
    public double[][] highRung90D = {{2900,  0},         {.65,      .075}};
    public double[][] highRungPrep ={{2900,  0},         {.65,      .3}};
    //Basket positions
    public double[][] lowBasket = {  {2700,  3300},      {.3,       0}};
    public double[][] highBasket = { {2600,  1300},      {.3,       0}};
    //Grab positions
    public double[][] grabAbove= {   {775,   3900},      {.65,      .1}};
    public double[][] grabAboveLow ={{450,   3900},      {.65,      .1}};
    public double[][] grabMiddle = { {475,   0},         {.275,     .425}};
    public double[][] grabLow = {    {0,     1600},      {.92,      .48}};
    public double[][] grabLow2 = {   {600,   3900},      {.92,      .48}};
    //Bot hang
    public double[][] hangPrepPos = {{3800,  1900},      {.65,      1}};

    /**Speeds**/

    //Universal Speeds
    public int stopDevice = 0;
    public int maxPower = 1;

    //Servo speeds
    public double clawAdjustment = .01;

    //Motor speeds
    public double fodFast = .85;
    public double fodSlower = .45;
    public double fodSlowest = .25;
    public int maxVelocity = 10000;
    public int adjustVelocity = 1500;

    //Gamepad values
    public double gamepadThreshold = .5;
}
