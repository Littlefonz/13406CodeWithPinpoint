package org.firstinspires.ftc.teamcode.mycode.robotSetup;

public final class Positions {

    /**Positions**/

    //Idle position                  //Slides //Angle //Rotation //Wrist
    public double[][] idle = {       {50,     0},      {.65,      1}};
    //Rung positions
    public double[][] lowRung = {    {50,     350},    {.3,       .3}};
    public double[][] highRung = {   {750,     1325},   {.29,      .59}};//{2475,  0},         {.96,      .4}
    public double[][] highRung90D = {{50,     2750},   {.65,      .075}};
    public double[][] highRungPrep ={{50,     2750},   {.65,      .3}};
    //Basket positions
    public double[][] lowBasket = {  {50,     3600},   {.3,       .17}};
    public double[][] highBasket = { {2100,    3600},   {.97,       .7}};
    //Grab positions
    public double[][] grabAbove= {   {2000,    875},    {.65,      .12}};
    public double[][] grabAboveLow ={{2000,    685},    {.65,      .12}};
    public double[][] grabLow2 = {   {2000,    450},    {.92,      .45}};
    public double[][] grabMiddle = { {50,     250},    {.31,     .45}};
    public double[][] grabLow = {    {250,     50},     {.92,      .4}};
    //Bot hang
    public double[][] hangPrepPos = {{750,    3800},      {.65,      1}};

    //Claw positions
    public double[] closed = {.49, .49};
    public double[] open = {.625, .625};
    public double[] openXL = {.7, .7};

    //Slides positions
    public int slidesIn = 0;
    public int slidesOut = 1000;


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
