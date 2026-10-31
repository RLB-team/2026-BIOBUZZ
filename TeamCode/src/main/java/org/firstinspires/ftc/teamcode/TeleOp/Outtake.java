/*
 * Outtake.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public final class Outtake
{
   private final MotorEx outtake;

   public Outtake(final MotorEx outtake)
   {
      this.outtake = outtake;
   }

   public void run(final double leftTrigger)
   {
      outtake.set((leftTrigger != 0.0) ? -0.6 : 0.0);
   }
}