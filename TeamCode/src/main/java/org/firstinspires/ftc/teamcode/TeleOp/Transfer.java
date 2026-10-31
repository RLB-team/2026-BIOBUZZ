/*
 * Transfer.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public final class Transfer
{
   private final MotorEx intaker, indexer;

   public Transfer(final MotorEx intaker, final MotorEx indexer)
   {
      this.intaker = intaker;
      this.indexer = indexer;
   }

   public void intake(final double power)
   {
      intaker.set(power);
   }

   public void index(final double index)
   {
      indexer.set(index);
   }
}