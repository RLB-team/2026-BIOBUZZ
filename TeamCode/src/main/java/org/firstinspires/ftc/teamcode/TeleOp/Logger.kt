/*
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 * Logger.kt manages error + exception handling, all through Kotlin
 */

package org.firstinspires.ftc.teamcode.TeleOp

import com.qualcomm.robotcore.util.RobotLog

class Logger {
   private var warnings = 0

   fun run(sysVoltage: Double, loopTime: Double) {
      if (sysVoltage < 10.0) {
         main.exceptionTime += loopTime / 1000.0 // Increment by realtime
         main.motorSpeedCap = 0.5
         // FIXME: Modifying global variables is a bad practice.
      }
      if (main.exceptionTime - warnings >= 10.0) {
         RobotLog.w("--- Automated dump due to high exception time ---")
         RobotLog.w("Exception time is ${main.exceptionTime} seconds.")
         RobotLog.w("Drivetrain max power: ${main.motorSpeedCap * 100}%")
         RobotLog.w("Loop time: ${1000.0 / loopTime} Hz")

         warnings += 10 // Limit how often this warning can go off
      }
   }
}