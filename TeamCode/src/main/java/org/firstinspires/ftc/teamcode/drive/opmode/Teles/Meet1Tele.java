package org.firstinspires.ftc.teamcode.drive.opmode.Teles;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.ElliotDrive;

@Disabled
@TeleOp (name = "Meet1Tele", group = "Elliot")
public class Meet1Tele extends LinearOpMode {
    //public DcMotor linear = null;
   // public CRServo intake = null;


    //HardwareMap hwMap           =  new HardwareMap();
    ElliotDrive drive;
    double linearPower;
    boolean yesClaw;
    DcMotor intakeLeft;
    DcMotor intakeRight;
    double clawPosition;
    public Servo claw;
    Servo trap;

    ElapsedTime runtime = new ElapsedTime();
    private boolean lastAMash = false;
    private boolean lastBMash = false;
    private boolean lastXMash = false;
    public boolean reverseDirections = false;
    public double reverseMod = 1;
//    public boolean precisionMode = false;
    public boolean lockedStrafe = false;
//    public double precisionModifier = 0.9;
    double driveRightPower;
    double driveLeftPower;
    double driveRight2Power;
    double driveLeft2Power;
    double y;
    double x;
    double rx;
    double x2;

    @Override
    public void runOpMode() throws InterruptedException {
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");
        trap = hardwareMap.get(Servo.class, "trap");

        drive = new ElliotDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(0, 0, 0));
        ElapsedTime opmodeRunTime = new ElapsedTime();


        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            opmodeRunTime.reset();
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -(((Math.abs(gamepad1.left_stick_y) < .2) ? 0 : gamepad1.left_stick_y) / .70) * (gamepad1.right_trigger > 0.05 ? 1 : 0.7),
                            (((Math.abs(gamepad1.left_stick_x) < .2) ? 0 : gamepad1.left_stick_x) / .70) * (gamepad1.right_trigger > 0.05 ? 1 : 0.7),
                            -(((Math.abs(gamepad1.right_stick_x) < .2) ? 0 : gamepad1.right_stick_x) / .70) * 0.7 * (gamepad1.right_trigger > 0.05 ? 1 : 0.7)
                    ));

            drive.linears.setPower(-(((Math.abs(gamepad2.left_stick_y) < .2) ? 0 : gamepad2.left_stick_y) / .70)*0.5);
            drive.susPension.setPower(gamepad2.right_stick_y);

//            if (gamepad1.left_trigger > 0.5){
//                drive.ramp.setPosition(0.42 );
//                drive.trap.setPosition(0.25);
//                //  intakeOne.setPower(1);
//                //   intakeTwo.setPower(-1);
//            } else {
//                drive.ramp.setPosition(0.46);
//                drive.intakeLeft.setPower(0);
//                drive.intakeRight.setPower(0);
//            }

            if (gamepad1.right_trigger > 0.5){
                intakeLeft.setPower(-1);
                intakeRight.setPower(1);
                drive.ramp.setPosition(0);
            }else if (gamepad2.left_trigger > 0.3){
                drive.ramp.setPosition(0.5);
            }else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
                drive.ramp.setPosition(0);
            }

            if (gamepad1.x){
                intakeLeft.setPower(1);
                intakeRight.setPower(-1);
            }
            if (gamepad2.right_trigger > 0.3){
                drive.poleGrabber.setPosition(0.75);
            } else if (gamepad2.a){
                drive.poleGrabber.setPosition(0);
            }
            else {
                drive.poleGrabber.setPosition(1);
            }

             //else if (gamepad2.right_bumper){
            //    drive.ramp.setPosition(0.25);
            //}
//        else {
//                drive.ramp.setPosition(0.025);
//            }

            if (gamepad2.right_bumper){
                trap.setPosition(0.3);
            }else {
                trap.setPosition(1);
            }

            if (gamepad2.left_bumper){
                drive.plane.setPower(-1);
            } else {
                drive.plane.setPower(0);
            }

            if (gamepad1.y){
                drive.ramp.setPosition(0);
            }

            // Read pose
            Pose2d poseEstimate = drive.getPoseEstimate();
        }
    }
}
