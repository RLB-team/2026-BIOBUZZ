// SPDX-License-Identifier: MIT

package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.TransferConsts.*;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public final class Transfer
{
   private final MotorEx intaker, indexer;

   public Transfer(final MotorEx intaker, final MotorEx indexer)
   {
      this.intaker = intaker;
      this.indexer = indexer;
   }

   public void intake()
   {
      intaker.set(INTAKE_SPEED);
      indexer.set(INDEX_SPEED);
   }

   public void eject()
   {
      intaker.set(-INTAKE_SPEED);
      indexer.set(-INDEX_SPEED);
   }

   public void feed()
   {
      indexer.set(INDEX_SPEED);
   }

   public void purge()
   {
      indexer.set(-INDEX_SPEED);
   }

   public void setIdle()
   {
      intaker.set(0);
      indexer.set(0);
   }
}