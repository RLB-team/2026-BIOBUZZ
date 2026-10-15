/*
 * Outtake.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public record Outtake(DcMotorEx outtakeFront, DcMotorEx outtakeBack)
{
   public void run(final double leftTrigger)
   {
      outtakeFront.setPower(leftTrigger);
      outtakeBack.setPower(leftTrigger);
   }
}