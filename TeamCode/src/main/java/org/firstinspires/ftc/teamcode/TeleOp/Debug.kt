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
import kotlin.math.roundToLong

class Debug(val telemetry: Telemetry)
{
   private var warnings = 0
   private var exceptionTime = 0.0
   private var motorSpeedCap = 1.0

   fun runLogger(sysVoltage: Double, loopTimeMilliseconds: Double, loopTimeHz: Double): Double
   {
      // --- Logging ---
      if (sysVoltage < 10.0 && sysVoltage > 0.0) {
         exceptionTime += loopTimeMilliseconds / 1000.0 // Increment by realtime
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

   fun outputTelemetry(outtakeVelocity: Double,
                       loopTimeHz: Double, intakeCurrent: Double, sysVoltage: Double)
   {
      if (USE_TELEMETRY_LITE) {
         telemetry.addData("Outtake (RPM)", outtakeVelocity)

         telemetry.addData("\nIntake Current (AMPS)", intakeCurrent)

         telemetry.addLine("\nOther Debug Info" +
                           "\n============================")
         telemetry.addData("Loop Time (HZ)", loopTimeHz)
      } else {
         telemetry.addLine("Outtake Front = $outtakeVelocity RPM")

         telemetry.addLine("\nIntake Current = $intakeCurrent Amps")
         telemetry.addLine("Loop Time = $loopTimeHz Hz")

         telemetry.addLine("<h1>Battery: ${(sysVoltage * 10).roundToLong() / 10} V</h1>")
         // all hell breaks loose, just because we want to round with 1 decimal point
         // maybe we should make our own math library to avoid this
      }
      telemetry.update()
   }
}