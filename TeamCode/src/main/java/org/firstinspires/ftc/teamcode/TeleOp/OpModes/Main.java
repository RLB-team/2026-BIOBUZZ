package org.firstinspires.ftc.teamcode.TeleOp.OpModes;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.DriveConsts.*;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.RuntimeConsts.*;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.*;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.TeleOp.RobotConfig;
import org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.TransferConsts.*;

@TeleOp(name="Main OpMode")
public final class Main extends RobotConfig
{
   @Override
   public void runOpMode()
   {
      initRobot();

      final var timer = new ElapsedTime();

      double motorMax = 1;
      var transferState = transferStates.IDLE;

      waitForStart();

      while (opModeIsActive()) {
         clearBulkCache();

         final double loopTimeMilliseconds = timer.milliseconds();
         timer.reset();

         double loopTimeHz;
         if (loopTimeMilliseconds != 0) {
            loopTimeHz = 1000 / loopTimeMilliseconds;
         } else {
            loopTimeHz = 0;
         }

         final double outtakeVelocity = -outtake.getVelocity();
         final double intakeCurrent   =  intaker.motorEx.getCurrent(CurrentUnit.AMPS);
         final double sysVoltage      =  battery.getVoltage();

         final double driveX  = Range.clip(gamepad1.left_stick_x,  -motorMax, motorMax) * DRIVE_X_BIAS;
         final double driveY  = Range.clip(gamepad1.left_stick_y,  -motorMax, motorMax) * DRIVE_Y_BIAS;
         final double driveRx = Range.clip(gamepad1.right_stick_x, -motorMax, motorMax) * DRIVE_TURN_BIAS;

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
         if (gamepad1.left_trigger != 0) {
            Outtake.run(SHOOT_RPM);
            transferState = transferStates.SHOOTING;
         } else {
            Outtake.stop();
            transferState = transferStates.IDLE;
         }
         Outtake.indexIfFlywheelReady(outtakeVelocity);

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