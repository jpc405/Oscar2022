package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//hardware
import org.firstinspires.ftc.teamcode.robot.Hardware;
import org.firstinspires.ftc.teamcode.robot.controllers.AnalogCheck;
import org.firstinspires.ftc.teamcode.robot.controllers.ButtonState;
import org.firstinspires.ftc.teamcode.robot.controllers.ControllerState;

@Config
@TeleOp(group = "drive")
public class TeleopODO extends LinearOpMode {
    private FtcDashboard dashboard;

    Hardware Oscar = new Hardware();



    public void runOpMode(){

        Oscar.drive.setPoseEstimate(new Pose2d(0,0,Math.toRadians(0)));
        ControllerState controller1 = new ControllerState(gamepad1);
        ControllerState controller2 = new ControllerState(gamepad2);

        /*
        controller 1- For max most likely
        any small changes in robot position with d pad, will overide the joysticks

         */
        //TODO:tune these
        controller1.addEventListener("dpad_up", ButtonState.HELD,() -> Oscar.setVel(new Pose2d(0.25,0,0)));
        controller1.addEventListener("dpad_down", ButtonState.HELD, () -> Oscar.setVel(new Pose2d(-0.25,0,0)));
        controller1.addEventListener("dpad_left", ButtonState.HELD, () -> Oscar.setVel(new Pose2d(0,0.25,0)));
        controller1.addEventListener("dpad_right",ButtonState.HELD, () -> Oscar.setVel(new Pose2d(0,-0.25,0)));
        controller1.addEventListener("left_trigger", AnalogCheck.GREATER_THAN, 0.1, () -> Oscar.setVel(new Pose2d(0,0,0.2)));
        controller1.addEventListener("right_trigger", AnalogCheck.GREATER_THAN, 0.1, () -> Oscar.setVel(new Pose2d(0,0,-0.2)));

        /*controller 2
        sets intake on or off
        runs cycle for placing and releasing
         */
        // toggle intake on if the grabber is open and the elbow is at its home position
        controller2.addEventListener("right_trigger",AnalogCheck.GREATER_THAN, 0.1,() ->{
            if(Oscar.elbow.getElbowPosition() == .72 && !Oscar.intake.getIntakeMode()){Oscar.intake.setIntakeMode(true);}
            else {Oscar.intake.setIntakeMode(false);}
        } );
        //IDK how this will work will test to see but may change
        //It all keeps heading up
        // When button y is pressed it will go through the entire top cycle
        controller2.addEventListener("y", ButtonState.PRESSED, () ->{
            Oscar.elbow.goToGrabPos();
            Oscar.grabber.grab();
            Oscar.elbow.moveTop();
            Oscar.grabber.goTop();
            Oscar.grabber.stopGrab();
            Oscar.grabber.goStart();
        });

        waitForStart();

        while(opModeIsActive()){
            controller1.updateControllerState();
            controller2.updateControllerState();

            Oscar.setVel(new Pose2d(
                    -Math.pow(controller1.getAnalogValue("left_stick_y"),3),
                    -Math.pow(controller1.getAnalogValue("leftstick_x"),3),
                    -Math.pow(controller1.getAnalogValue("right_stick_x"),3)

            ));
            controller1.handleEvents();
            controller2.handleEvents();

            //Allow Dpad override
            Oscar.drive.setDrivePower(Oscar.getVel());
            Oscar.drive.update();

            telemetry.update();

            //Pose2d myPose = Oscar.odoDT.getPoseEstimate();

        }


    }

}