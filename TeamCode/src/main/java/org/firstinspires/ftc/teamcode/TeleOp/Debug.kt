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
import kotlin.math.absoluteValue

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

   fun outputTelemetry(outtakeFrontVelocity: Double, outtakeBackVelocity: Double,
                       loopTimeHz: Double, intakeCurrent: Double)
   {
      telemetry.addData("Outtake Front (RPM)", outtakeFrontVelocity)
      telemetry.addData("Outtake Back  (RPM)", outtakeBackVelocity.absoluteValue)

      telemetry.addData("\nIntake Current (AMPS)", intakeCurrent)

      telemetry.addLine("\nOther Debug Info")
      telemetry.addLine("============================")
      telemetry.addData("Loop Time (HZ) ", loopTimeHz)

      telemetry.update()
   }
}