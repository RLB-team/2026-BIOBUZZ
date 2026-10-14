/*
 * Transfer.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public final class Transfer
{
   private final DcMotorEx intaker, indexer;

   public Transfer(final DcMotorEx intaker, final DcMotorEx indexer)
   {
      this.intaker = intaker;
      this.indexer = indexer;
   }

   public void intake()
   {
      intaker.setPower(1);
   }

   public void index(final double index)
   {
      indexer.setPower(index);
   }
}