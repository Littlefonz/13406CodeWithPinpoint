**Tuning steps are mostly the same as https://rr.brott.dev/docs/v1-0/tuning/**
differences: 

- Ensure you configure the Pinpoint in hardwaremap as pinpoint by default or change in PinpointDrive 
- Leave inPerTick at 1.0 in MecanumDrive and skip ForwardPushTest; instead configure encoder resolution 
in PinpointDrive to one of the presets for goBilda odometry
- Also change the drive class in TuningOpModes, SplineTest, and your op modes to PinpointDrive
- Also, ensure that you use DeadWheelDirectionDebugger and properly reverse the wheels in PinpointDrive 
- Then you can move on with tuning in https://rr.brott.dev/docs/v1-0/tuning/ as normal.

TODO: expand these docs
