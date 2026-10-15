/*
 * Drive.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public record Drive(DcMotorEx frontLeft, DcMotorEx frontRight,
                    DcMotorEx backLeft,  DcMotorEx backRight)
{
   public void run(final double x, final double y, final double rx)
   {
      frontLeft.setPower(rx + x + y);
      backLeft.setPower(rx - x + y);
      frontRight.setPower(rx - x - y);
      backRight.setPower(rx + x - y);
   }
}