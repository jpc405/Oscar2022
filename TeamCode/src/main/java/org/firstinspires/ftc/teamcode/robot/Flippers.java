package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Flippers {

    //f0 is front, f1 is back

    private Servo f0;
    private Servo f1;

    private double downPosition0 = .43;
    private double downPosition1 = .95;

    private double upPosition0 = .84;
    private double upPosition1 = .62;


    public Flippers(HardwareMap ahwMap) {
        f0 = ahwMap.get(Servo.class, "Flipper 0");
        f1 = ahwMap.get(Servo.class, "Flipper 1");
    }
    public void moveDown(String frontOrBack) {
        if(frontOrBack.equals("front")) f0.setPosition(downPosition0);
        else f1.setPosition(downPosition1);
    }
    public void moveUp(String frontOrBack) {
        if(frontOrBack.equals("front")) f0.setPosition(upPosition0);
        else f1.setPosition(upPosition1);
    }
}
