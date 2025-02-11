package org.firstinspires.ftc.teamcode.mycode.robotSetup;

public final class Positions {

    /**Positions**/

    //Idle position                  //Angle //GoRail //Slides //Rotation //Wrist
    public double[][] idle = {       {0,     0,       0},      {.65,      1}};
    //Rung positions
    public double[][] lowRung = {    {600,   0,       0},      {.3,       .3}};
    public double[][] highRung = {   {1630,  3928,    0},      {.29,      .55}};//{2475,  0},         {.96,      .4}
    public double[][] highRung90D = {{2900,  0,       0},      {.65,      .075}};
    public double[][] highRungPrep ={{2900,  0,       0},      {.65,      .3}};
    //Basket positions
    public double[][] lowBasket = {  {2850,  0,       0},      {.3,       .17}};
    public double[][] highBasket = { {3250,  3300,    1000},   {.3,       0}};
    //Grab positions
    public double[][] grabAbove= {   {850,   3900,    1000},   {.65,      .12}};
    public double[][] grabAboveLow ={{625,   3900,    1000},   {.65,      .12}};
    public double[][] grabLow2 = {   {600,   0,       1000},   {.92,      .48}};
    public double[][] grabMiddle = { {420,   0,       0},      {.31,     .48}};
    public double[][] grabLow = {    {0,     1600,    0},      {.92,      .48}};
    //Bot hang
    public double[][] hangPrepPos = {{3800,  1900,    0},      {.65,      1}};

    //Claw positions
    public double[] closed = {.49, .49};
    public double[] open = {.625, .625};

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
