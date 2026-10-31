/*
 * Drive.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public final class Drive
{
   private final MotorEx frontLeft, frontRight, backLeft, backRight;

   public Drive(final MotorEx frontLeft, final MotorEx frontRight,
                final MotorEx backLeft,  final MotorEx backRight)
   {
      this.frontLeft  = frontLeft;
      this.frontRight = frontRight;
      this.backLeft   = backLeft;
      this.backRight  = backRight;
   }

   public void run(final double x, final double y, final double rx)
   {
      frontLeft.set(rx + x + y);
      backLeft.set(rx - x + y);
      frontRight.set(rx - x - y);
      backRight.set(rx + x - y);
   }
}