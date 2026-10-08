/*
 * 2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 * Logger.java manages error + exception handling
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.util.RobotLog;

public final class logger
{
   public logger() {}

   public void run(final double sysVoltage, final double loopTime) {
      if (sysVoltage < 10.0) {
         main.exceptionTime += loopTime / 1000.0; // Increment by realtime
         main.motorSpeedCap = 0.5;
         // FIXME: Modifying global variables is a bad practice.
      }
      if (main.exceptionTime >= 10.0) {
         RobotLog.w("Exception time is " + main.exceptionTime + " seconds.");
      }
   }
}