/* 2026
   Authors: Wade Kuhn
   Game:    BIOBUZZ
   License: GPL V3.0
   Main.java provides an efficient, class organized OpMode */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Testing OpMode")
public final class main extends LinearOpMode
{
   @Override
   public void runOpMode() {
      double exceptionTime = 0.0;
      double loopTime = 0.0;
      double sysVoltage;
      double motorSpeedCap = 1.0;

      double x, y, rx;
      double aButton, bButton, xButton;
      double leftTrigger;

      final var pidfA = new PIDFCoefficients(50.0, 15.0, 0.0, 0.0);

      final var battery =  hardwareMap.get(VoltageSensor.class, "Control Hub");

      final var frontLeft =    hardwareMap.get(DcMotorEx.class, "Front Left");
      final var frontRight =   hardwareMap.get(DcMotorEx.class, "Front Right");
      final var backLeft =     hardwareMap.get(DcMotorEx.class, "Back Left");
      final var backRight =    hardwareMap.get(DcMotorEx.class, "Back Right");
      final var intaker =      hardwareMap.get(DcMotorEx.class, "Intake");
      final var indexer =      hardwareMap.get(DcMotorEx.class, "Indexer");
      final var outtakeFront = hardwareMap.get(DcMotorEx.class, "Outtake Front");
      final var outtakeBack =  hardwareMap.get(DcMotorEx.class, "Outtake Back");

      final DcMotorEx[] brakeMotors =   { frontLeft, frontRight, backLeft, backRight };
      final DcMotorEx[] encoderMotors = { frontLeft, frontRight, backLeft, backRight, indexer };
      final DcMotorEx[] reverseMotors = { frontLeft, backLeft, outtakeFront };

      for (final var motor : brakeMotors)
         motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
      for (final var motor : encoderMotors)
         motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
      for (final var motor : reverseMotors)
         motor.setDirection(DcMotorEx.Direction.REVERSE);

      outtakeFront.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfA);
      outtakeBack.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfA);

      final var drive =   new drive(frontLeft, frontRight, backLeft, backRight);
      final var intake =  new intake(intaker, indexer);
      final var outtake = new outtake(outtakeFront, outtakeBack);
      final var console = new console(telemetry, intaker, outtakeFront, outtakeBack);
      final var logger =  new logger();

      final var loopTimer = new ElapsedTime();

      waitForStart();

      while (opModeIsActive()) {
         loopTimer.reset();

         sysVoltage = battery.getVoltage();

         x =  Range.clip(-gamepad1.left_stick_x, -motorSpeedCap, motorSpeedCap) * 1.3;
         y =  Range.clip(-gamepad1.left_stick_y, -motorSpeedCap, motorSpeedCap);
         rx = Range.clip(gamepad1.right_stick_x, -motorSpeedCap, motorSpeedCap);

         aButton = gamepad1.a ? 1.0 : 0.0;
         bButton = gamepad1.b ? 1.0 : 0.0;
         xButton = gamepad1.x ? 1.0 : 0.0;
         leftTrigger = gamepad1.left_trigger / 2.0;

         drive.run(x, y, rx);
         intake.run(aButton, bButton, xButton);
         outtake.run(leftTrigger);
         console.run(sysVoltage, loopTime);
         motorSpeedCap = logger.run(sysVoltage, exceptionTime, loopTime);

         loopTime = loopTimer.milliseconds();
      }
   }
}