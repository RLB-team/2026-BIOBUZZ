/*
 * 2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 * Logger.java manages error + exception handling
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.RobotLog;

public final class logger
{
   double motorSpeedCap = 1.0;

   public logger() {}

   public void run(final double sysVoltage, final double loopTime) {
      if (sysVoltage < 10.0) {
         main.exceptionTime += loopTime / 1000.0;
         main.motorSpeedCap = 0.5;
      }
      if (main.exceptionTime >= 10.0) {
         RobotLog.w("Exception time is " + main.exceptionTime + " seconds.");
      }
   }
}