// This class is depreciated. It has been replaced by `Logger.kt`,
// the Kotlin rewrite of this file.
// This dead class will continue to exist as a backup until `Logger.kt`
// is proven to be reliable.

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.util.RobotLog;

public final class logger
{
   private int warnings = 0;

   public logger() {}

   public void run(final double sysVoltage, final double loopTime) {
      if (sysVoltage < 10.0) {
         main.exceptionTime += loopTime / 1000.0; // Increment by realtime
         main.motorSpeedCap = 0.5;
         // FIXME: Modifying global variables is a bad practice.
      }
      if (main.exceptionTime - warnings >= 10.0) {
         RobotLog.w("--- Automated dump due to high exception time ---");
         RobotLog.w("Exception time is " + main.exceptionTime + " seconds.");
         RobotLog.w("Drivetrain max power: " + main.motorSpeedCap * 100.0 + "%");
         RobotLog.w("Loop time: " + 1000.0 / loopTime + " Hz");

         warnings += 10; // Limit how often this warning can go off
      }
   }
}