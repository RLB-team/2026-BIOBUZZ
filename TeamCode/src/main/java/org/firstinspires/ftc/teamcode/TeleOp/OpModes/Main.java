/*
 * Main.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp.OpModes;

import static org.firstinspires.ftc.teamcode.TeleOp.Constants.*;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.TeleOp.Debug;
import org.firstinspires.ftc.teamcode.TeleOp.Drive;
import org.firstinspires.ftc.teamcode.TeleOp.Outtake;
import org.firstinspires.ftc.teamcode.TeleOp.Transfer;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import java.util.List;

@TeleOp(name = "Main OpMode")
public final class Main extends LinearOpMode
{

   private double motorSpeedCap;

   @Override
   public void runOpMode()
   {
      // final var pidf = new PIDFCoefficients(P_A, I_A, D_A, F_A);

      final var battery = hardwareMap.get(VoltageSensor.class, "Control Hub");

      final MotorEx frontLeft  = new MotorEx(hardwareMap, "frontLeft");
      final MotorEx frontRight = new MotorEx(hardwareMap, "frontRight");
      final MotorEx backLeft   = new MotorEx(hardwareMap, "backLeft");
      final MotorEx backRight  = new MotorEx(hardwareMap, "backRight");
      final MotorEx intaker    = new MotorEx(hardwareMap, "Intake");
      final MotorEx indexer    = new MotorEx(hardwareMap, "Indexer");
      final MotorEx outtake    = new MotorEx(hardwareMap, "Outtake");

      final MotorEx[] encoderMotors = { frontLeft, frontRight, backLeft, backRight, indexer,
                                        outtake };
      final MotorEx[] reversedMotors = { frontLeft, frontRight, backLeft, intaker };

      for (final var motor : encoderMotors) {
         motor.setRunMode(MotorEx.RunMode.VelocityControl);
      }
      for (final var motor : reversedMotors) {
         motor.setInverted(true);
      }

      final List<LynxModule> modules = hardwareMap.getAll(LynxModule.class);
      for (final var module : modules) {
         module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
      }

      final var Drive    = new Drive(frontLeft, frontRight, backLeft, backRight);
      final var Transfer = new Transfer(intaker, indexer);
      final var Outtake  = new Outtake(outtake);
      final var Debug    = new Debug(telemetry);

      final var timer = new ElapsedTime();

      if (USE_TELEMETRY_LITE) {
         telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
      } else {
         telemetry.setDisplayFormat(Telemetry.DisplayFormat.HTML);
      }
      telemetry.setCaptionValueSeparator(" = ");

      waitForStart();

      while (opModeIsActive()) {
         for (final var module : modules) module.clearBulkCache();

         double loopTimeMilliseconds = timer.milliseconds();
         timer.reset();
         double loopTimeHz = (loopTimeMilliseconds > 0.0) ? 1000.0 / loopTimeMilliseconds : 0.0;

         double aButton     = gamepad1.a ? 1.0 : 0.0;
         double bButton     = gamepad1.b ? 1.0 : 0.0;
         double xButton     = gamepad1.x ? 1.0 : 0.0;
         double leftTrigger = gamepad1.left_trigger / SHOOT_SPEED_DIVIDER;

         double outtakeVelocity = -outtake.getVelocity();
         double intakeCurrent   = intaker.motorEx.getCurrent(CurrentUnit.AMPS);
         double sysVoltage      = battery.getVoltage();

         double x  = Range.clip(-gamepad1.left_stick_x, -motorSpeedCap, motorSpeedCap) * DRIVE_X_BIAS;
         double y  = Range.clip(-gamepad1.left_stick_y, -motorSpeedCap, motorSpeedCap) * DRIVE_Y_BIAS;
         double rx = Range.clip(gamepad1.right_stick_x, -motorSpeedCap, motorSpeedCap) * DRIVE_TURN_BIAS;

         // --- Drive ---
         Drive.run(x, y, rx);

         // --- Intake / Index ---
         Transfer.intake(aButton);
         Transfer.index(aButton - bButton - xButton);

         // --- Outtake ---
         Outtake.run(leftTrigger);

         // --- Telemetry and Logs ---
         if (USE_RUNTIME_LOGGER) {
            motorSpeedCap = Debug.runLogger(sysVoltage, loopTimeMilliseconds, loopTimeHz);
         }

         if (USE_RUNTIME_TELEMETRY) {
            Debug.outputTelemetry(
                    outtakeVelocity,
                    loopTimeHz,
                    intakeCurrent,
                    sysVoltage
            );
         }
      }
   }
}