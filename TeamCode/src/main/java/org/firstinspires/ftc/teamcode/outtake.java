/* 2026
   Authors: Wade Kuhn
   Game:    BIOBUZZ
   License: GPL V3.0
   Outtake.java is a simple outtake management class */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public final class outtake
{
   private final DcMotorEx outtakeFront, outtakeBack;

   public outtake(final DcMotorEx outtakeFront, final DcMotorEx outtakeBack) {
      this.outtakeFront = outtakeFront;
      this.outtakeBack = outtakeBack;
   }

   public void run(final double leftTrigger) {
      outtakeFront.setPower(-leftTrigger);
      outtakeBack.setPower(-leftTrigger);
   }
}