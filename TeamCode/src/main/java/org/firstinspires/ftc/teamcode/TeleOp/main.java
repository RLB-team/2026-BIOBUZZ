/*
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 * Main.java provides an efficient, class organized OpMode
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.TeleOp.constants.*;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import java.util.List;

@TeleOp(name = "Testing OpMode")
public final class main extends LinearOpMode
{
   static double motorSpeedCap;
   static double exceptionTime;

   double loopTime = 0.0;
   double sysVoltage = 15.0; // Prevent brownout response on first loop

   double x, y, rx;
   double aButton, bButton, xButton;
   double leftTrigger;

   double outtakeFrontVelocity;
   double outtakeBackVelocity;
   double intakeCurrent;

   @Override
   public void runOpMode() {
      // Java sometimes ignores the initialization of static, non-constant variables.
      // To fix this we declare the statics and modify them here.
      motorSpeedCap = 1.0;
      exceptionTime = 0.0;

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

      final var drive =   new drive(frontLeft, frontRight, backLeft, backRight);
      final var intake =  new intake(intaker, indexer);
      final var outtake = new outtake(outtakeFront, outtakeBack);
      final var console = new ConsoleKt(telemetry);
      final var logger =  new logger();

      final var timer = new ElapsedTime();

      waitForStart();

      while (opModeIsActive()) {
         for (final var module : modules) {
            module.clearBulkCache();
         }
         timer.reset();

         x =  Range.clip(-gamepad1.left_stick_x, -motorSpeedCap, motorSpeedCap) * DRIVE_X_BIAS;
         y =  Range.clip(-gamepad1.left_stick_y, -motorSpeedCap, motorSpeedCap) * DRIVE_Y_BIAS;
         rx = Range.clip(gamepad1.right_stick_x, -motorSpeedCap, motorSpeedCap) * DRIVE_TURN_BIAS;

         aButton = gamepad1.a ? 1.0 : 0.0;
         bButton = gamepad1.b ? 1.0 : 0.0;
         xButton = gamepad1.x ? 1.0 : 0.0;
         leftTrigger = -gamepad1.left_trigger / SHOOT_SPEED_DIVIDER;

         drive.run(x, y, rx);
         intake.run(aButton, bButton, xButton);
         outtake.run(leftTrigger);

         outtakeFrontVelocity = -outtakeFront.getVelocity();
         outtakeBackVelocity = -outtakeBack.getVelocity();
         intakeCurrent = intaker.getCurrent(CurrentUnit.AMPS);
         sysVoltage = battery.getVoltage();
         logger.run(sysVoltage, loopTime);
         console.run(outtakeFrontVelocity, outtakeBackVelocity, sysVoltage, loopTime, intakeCurrent);

         loopTime = timer.milliseconds();
      }
   }
}