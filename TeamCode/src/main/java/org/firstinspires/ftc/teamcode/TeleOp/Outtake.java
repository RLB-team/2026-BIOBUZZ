/*
 * Outtake.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public final class Outtake
{
   private final DcMotorEx outtakeFront, outtakeBack;

   public Outtake(final DcMotorEx outtakeFront, final DcMotorEx outtakeBack)
   {
      this.outtakeFront = outtakeFront;
      this.outtakeBack = outtakeBack;
   }

   public void run(final double leftTrigger)
   {
      outtakeFront.setPower(leftTrigger);
      outtakeBack.setPower(leftTrigger);
   }
}