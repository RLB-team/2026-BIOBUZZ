package org.firstinspires.ftc.teamcode.TeleOp

import org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.TransferConsts.*

import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry

class Debug(val telemetry: Telemetry)
{
   private var warnings      = 0
   private var exceptionTime = 0.0
   private var motorSpeedCap = 1.0

   fun log(sysVoltage: Double, loopTimeMilliseconds: Double, loopTimeHz: Double): Double
   {
      // --- Logging ---
      if (sysVoltage < 10) {
         exceptionTime += loopTimeMilliseconds / 1000 // Increment by realtime
         motorSpeedCap = 0.5
      } else {
         motorSpeedCap = 1.0
      }

      if (exceptionTime - warnings > 10.0) {
         RobotLog.w("--- Automated dump due to high exception time ---")
         RobotLog.w("Exception time is $exceptionTime seconds.")
         RobotLog.w("Drivetrain max power: ${motorSpeedCap * 100.0}%")
         RobotLog.w("Loop time: $loopTimeHz Hz")
         warnings += 10
      }

      return motorSpeedCap
   }

   fun outputTelemetry(outtakeVelocity: Double, loopTimeHz: Double,
                       intakeCurrent: Double, indexerState: IndexerStates)
   {
      telemetry.addData("Outtake (RPM)", outtakeVelocity)

      telemetry.addData("\nIntake Current (AMPS)", intakeCurrent)

      telemetry.addLine("\nOther Debug Info" +
                        "\n===========================")
      telemetry.addData("Indexer State ", indexerState)
      telemetry.addData("Loop Time (HZ)", loopTimeHz)
      telemetry.update()
   }
}