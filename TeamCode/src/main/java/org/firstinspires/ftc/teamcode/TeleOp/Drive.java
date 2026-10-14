/*
 * Drive.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public final class Drive
{
   private final DcMotorEx frontLeft, frontRight, backLeft, backRight;

   public Drive(final DcMotorEx frontLeft, final DcMotorEx frontRight,
                final DcMotorEx backLeft, final DcMotorEx backRight)
   {
      this.frontLeft = frontLeft;
      this.frontRight = frontRight;
      this.backLeft = backLeft;
      this.backRight = backRight;
   }

   public void run(final double x, final double y, final double rx)
   {
      frontLeft.setPower(rx + x + y);
      backLeft.setPower(rx - x + y);
      frontRight.setPower(rx - x - y);
      backRight.setPower(rx + x - y);
   }
}