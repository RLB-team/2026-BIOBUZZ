/* 2026
   Authors: Wade Kuhn
   Game:    BIOBUZZ
   License: GPL V3.0
   Logger.java manages error + exception handling */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.RobotLog;

public final class logger
{
   double motorSpeedCap = 1.0;

   public logger() {}

   public double run(final double sysVoltage, double exceptionTime, final double loopTime) {
      if (sysVoltage < 10.0) {
         exceptionTime += loopTime / 1000.0;
         motorSpeedCap = 0.5;
      }
      if (exceptionTime >= 10.0) {
         RobotLog.w("Exception time is " + exceptionTime + " seconds.");
      }
      return motorSpeedCap;
   }
}