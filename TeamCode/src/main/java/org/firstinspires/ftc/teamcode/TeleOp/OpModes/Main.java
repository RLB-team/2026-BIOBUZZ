package org.firstinspires.ftc.teamcode.TeleOp.OpModes;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.DriveConsts.*;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.RuntimeConsts.*;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.*;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.TeleOp.Debug;
import org.firstinspires.ftc.teamcode.TeleOp.Outtake;
import org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.TransferConsts.*;
import org.firstinspires.ftc.teamcode.TeleOp.Transfer;

import java.util.List;

@TeleOp(name="Main OpMode")
public final class Main extends LinearOpMode
{
   @Override
   public void runOpMode()
   {
      double motorMax = 1;
      var transferState = transferStates.IDLE;

      final var battery = hardwareMap.get(VoltageSensor.class, "Control Hub");

      final var frontLeft    = new MotorEx(hardwareMap, "frontLeft");
      final var frontRight   = new MotorEx(hardwareMap, "frontRight");
      final var backLeft     = new MotorEx(hardwareMap, "backLeft");
      final var backRight    = new MotorEx(hardwareMap, "backRight");
      final var intaker      = new MotorEx(hardwareMap, "Intake");
      final var indexer      = new MotorEx(hardwareMap, "Indexer");
      final var outtake      = new MotorEx(hardwareMap, "Outtake");
      final var transferGate = new SimpleServo(hardwareMap, "indexerGate", 0, 120);

      final MotorEx[] encoderMotors = {
              frontLeft, frontRight, backLeft, backRight,
              indexer
      };
      final MotorEx[] reversedMotors = {
              frontLeft, frontRight, backLeft, intaker,
              indexer
      };

      for (final var motor : encoderMotors) {
         motor.setRunMode(MotorEx.RunMode.VelocityControl);
      }
      for (final var motor : reversedMotors) {
         motor.setInverted(true);
      }

      outtake.motorEx.setVelocityPIDFCoefficients(P_A, P_I, P_D, 0);


      final List<LynxModule> modules = hardwareMap.getAll(LynxModule.class);
      for (final var module : modules) {
         module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
      }

      final var Drive    = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
      final var Transfer = new Transfer(intaker, indexer);
      final var Outtake  = new Outtake(outtake, indexer, intaker, transferGate);
      final var Debug    = new Debug(telemetry);
      final var timer    = new ElapsedTime();

      telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
      telemetry.setCaptionValueSeparator(" = ");

      waitForStart();

      while (opModeIsActive()) {
         for (final var module : modules) {
            module.clearBulkCache();
         }

         double loopTimeMilliseconds = timer.milliseconds();
         timer.reset();
         double loopTimeHz = (loopTimeMilliseconds > 0) ? 1000 / loopTimeMilliseconds : 0;

         double outtakeVelocity = -outtake.getVelocity();
         double intakeCurrent   =  intaker.motorEx.getCurrent(CurrentUnit.AMPS);
         double sysVoltage      =  battery.getVoltage();

         double driveX  = Range.clip(gamepad1.left_stick_x,  -motorMax, motorMax) * DRIVE_X_BIAS;
         double driveY  = Range.clip(gamepad1.left_stick_y,  -motorMax, motorMax) * DRIVE_Y_BIAS;
         double driveRx = Range.clip(gamepad1.right_stick_x, -motorMax, motorMax) * DRIVE_TURN_BIAS;

         // --- Drive ---
         Drive.driveRobotCentric(driveX, driveY, driveRx);

         // --- Intake / Index ---
         if (gamepad1.a) {
            Transfer.intake();
         } else if (gamepad1.y) {
            Transfer.eject();
         } else if (gamepad1.b) {
            Transfer.feed();
         } else if (gamepad1.x) {
            Transfer.purge();
         } else if (transferState != transferStates.SHOOTING) {
            Transfer.stop();
         }

         // --- Outtake ---
         if (gamepad1.left_bumper) {
            Outtake.run(SHOOT_SPEED);
            transferState = transferStates.SHOOTING;
         } else if (ALLOW_REVERSE_SHOOTER && gamepad1.right_bumper) {
            Outtake.reverse(SHOOT_SPEED);
         } else {
            Outtake.stop();
            transferState = transferStates.IDLE;
         }
         Outtake.manageGate(outtakeVelocity, transferState);

         // --- Telemetry and Logging ---
         if (RUN_TELEMETRY_LOGGER) {
            motorMax = Debug.log(sysVoltage, loopTimeMilliseconds, loopTimeHz);
         }
         if (RUN_TELEMETRY) {
            Debug.print(outtakeVelocity, loopTimeHz, intakeCurrent);
         }
      }
   }
}