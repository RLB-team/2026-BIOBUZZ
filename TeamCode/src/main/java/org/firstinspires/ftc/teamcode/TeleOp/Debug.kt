/*
 * Debug.kt
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp

import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry

class Debug(val telemetry: Telemetry)
{
   private var warnings = 0
   private var exceptionTime = 0.0
   private var motorSpeedCap = 1.0

   fun runLogger(sysVoltage: Double, loopTimeMilliseconds: Double): Double
   {
      // --- Logging ---
      if (sysVoltage < 10.0 && sysVoltage > 0.0) {
         exceptionTime += loopTimeMilliseconds / 1000.0 // Increment by realtime
         motorSpeedCap = 0.5
      }
      if (exceptionTime - warnings > 10.0) {
         RobotLog.w("--- Automated dump due to high exception time ---")
         RobotLog.w("Exception time is $exceptionTime seconds.")
         RobotLog.w("Drivetrain max power: ${motorSpeedCap * 100.0}%")
         RobotLog.w("Loop time: ${1000.0 / loopTimeMilliseconds} Hz")

         warnings += 10
      }

      return motorSpeedCap
   }

   fun outputTelemetry(outtakeFrontVelocity: Double, outtakeBackVelocity: Double,
                       sysVoltage: Double, loopTimeMilliseconds: Double, intakeCurrent: Double,
                       x: Double, y: Double, rx: Double)
   {
      telemetry.addLine("--- Outtake ---")
      telemetry.addLine("Outtake Front: $outtakeFrontVelocity RPM")
      telemetry.addLine("Outtake Back: $outtakeBackVelocity RPM")

      telemetry.addLine("\n--- Intake / Indexer ---")
      telemetry.addLine(  "Intake Current: $intakeCurrent amps")

      telemetry.addLine("\n--- Other Debug Info ---")
      telemetry.addLine(  "Battery Voltage: $sysVoltage")
      telemetry.addLine(  "Loop Time: ${1000.0 / loopTimeMilliseconds} Hz")
      telemetry.addLine(  "Drive: { x: $x y: $y z: $rx }")

      telemetry.update()
   }
}