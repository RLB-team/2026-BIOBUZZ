/*
 * Debug.kt
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp

import org.firstinspires.ftc.teamcode.TeleOp.Constants.*

import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry

class Debug(val telemetry: Telemetry)
{
   private var warnings = 0
   private var exceptionTime = 0.0
   private var motorSpeedCap = 1.0

   fun run(outtakeBackVelocity: Double, outtakeFrontVelocity: Double,
           sysVoltage: Double, loopTime: Double, intakeCurrent: Double): Double
   {
      val loopHz = 1000.0 / loopTime

      // --- Logging ---
      if (sysVoltage < 10.0) {
         exceptionTime += loopTime / 1000.0 // Increment by realtime
         motorSpeedCap = 0.5
      }
      if (exceptionTime - warnings > 10.0) {
         RobotLog.w("--- Automated dump due to high exception time ---")
         RobotLog.w("Exception time is $exceptionTime seconds.")
         RobotLog.w("Drivetrain max power: ${motorSpeedCap * 100}%")
         RobotLog.w("Loop time: $loopHz Hz")

         warnings += 10 // Limit how often this warning can go off
      }

      // --- Telemetry ---
      if (OUTPUT_TELEMETRY) {
         telemetry.addLine("--- Outtake ---")
         telemetry.addLine("Outtake Front: $outtakeFrontVelocity RPM")
         telemetry.addLine("Outtake Back: $outtakeBackVelocity RPM\n")

         telemetry.addLine("--- Intake / Indexer ---")
         telemetry.addLine("Intake Current: $intakeCurrent amps\n")

         telemetry.addLine("--- Other Debug Info ---")
         telemetry.addLine("Battery Voltage: $sysVoltage")
         telemetry.addLine("Loop Time: $loopHz Hz")

         telemetry.update()
      }

      return motorSpeedCap
   }
}