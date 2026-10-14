/*
 * Main.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp.MainOpMode;

import static org.firstinspires.ftc.teamcode.TeleOp.Constants.*;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.TeleOp.Debug;
import org.firstinspires.ftc.teamcode.TeleOp.Drive;
import org.firstinspires.ftc.teamcode.TeleOp.Outtake;
import org.firstinspires.ftc.teamcode.TeleOp.Transfer;

import java.util.List;

@TeleOp(name = "Main OpMode")
public final class Main extends LinearOpMode
{
   double loopTime = 0.0;
   double sysVoltage = 15.0; // Prevent brownout response on first loop

   double x, y, rx;
   double bButton, xButton;
   double leftTrigger;

   double outtakeFrontVelocity;
   double outtakeBackVelocity;
   double intakeCurrent;

   double motorSpeedCap;

   @Override
   public void runOpMode()
   {
      final var pidf = new PIDFCoefficients(P_A, I_A, D_A, F_A);

      final var battery = hardwareMap.get(VoltageSensor.class, "Control Hub");

      final var frontLeft =    hardwareMap.get(DcMotorEx.class, "Front Left");
      final var frontRight =   hardwareMap.get(DcMotorEx.class, "Front Right");
      final var backLeft =     hardwareMap.get(DcMotorEx.class, "Back Left");
      final var backRight =    hardwareMap.get(DcMotorEx.class, "Back Right");
      final var intaker =      hardwareMap.get(DcMotorEx.class, "Intake");
      final var indexer =      hardwareMap.get(DcMotorEx.class, "Indexer");
      final var outtakeFront = hardwareMap.get(DcMotorEx.class, "Outtake Front");
      final var outtakeBack =  hardwareMap.get(DcMotorEx.class, "Outtake Back");

      final DcMotorEx[] brakeMotors =   { frontLeft, frontRight, backLeft, backRight };
      final DcMotorEx[] encoderMotors = { frontLeft, frontRight, backLeft, backRight, indexer,
                                          outtakeFront, outtakeBack };
      final DcMotorEx[] reverseMotors = { frontLeft, backLeft, outtakeFront };

      for (final var motor : brakeMotors)
         motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
      for (final var motor : encoderMotors)
         motor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidf);
      for (final var motor : reverseMotors)
         motor.setDirection(DcMotorEx.Direction.REVERSE);

      final List<LynxModule> modules = hardwareMap.getAll(LynxModule.class);
      for (final var module : modules) {
         module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
      }

      final var Drive =    new Drive(frontLeft, frontRight, backLeft, backRight);
      final var Transfer = new Transfer(intaker, indexer);
      final var Outtake =  new Outtake(outtakeFront, outtakeBack);
      final var Debug =    new Debug(telemetry);

      final var timer = new ElapsedTime();

      waitForStart();

      while (opModeIsActive()) {
         for (final var module : modules) {
            module.clearBulkCache();
         }

         x =  Range.clip(-gamepad1.left_stick_x, -motorSpeedCap, motorSpeedCap) * DRIVE_X_BIAS;
         y =  Range.clip(-gamepad1.left_stick_y, -motorSpeedCap, motorSpeedCap) * DRIVE_Y_BIAS;
         rx = Range.clip(gamepad1.right_stick_x, -motorSpeedCap, motorSpeedCap) * DRIVE_TURN_BIAS;

         bButton = gamepad1.b ? 1.0 : 0.0;
         xButton = gamepad1.x ? 1.0 : 0.0;
         leftTrigger = -gamepad1.left_trigger / SHOOT_SPEED_DIVIDER;

         outtakeFrontVelocity = -outtakeFront.getVelocity();
         outtakeBackVelocity =  -outtakeBack.getVelocity();
         intakeCurrent = intaker.getCurrent(CurrentUnit.AMPS);
         sysVoltage = battery.getVoltage();

         Drive.run(x, y, rx);

         if (gamepad1.a) Transfer.intake();
         Transfer.index(bButton - xButton);
         Outtake.run(leftTrigger);

         motorSpeedCap = Debug.run(outtakeFrontVelocity, outtakeBackVelocity, sysVoltage,
                                   loopTime, intakeCurrent);

         loopTime = timer.milliseconds();
         timer.reset();
      }
   }
}