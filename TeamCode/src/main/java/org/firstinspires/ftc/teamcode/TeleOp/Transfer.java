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

   /// Intakes pollen normally. Both the intake and the indexer run forwards.
   public void intake()
   {
      intaker.set(INTAKE_SPEED);
      indexer.set(INDEX_SPEED);
   }

   /// Ejects pollen. Both the intake and the indexer run backwards.
   public void eject()
   {
      intaker.set(-EJECT_SPEED);
      indexer.set(-INDEX_SPEED);
   }

   /// Spins indexer forwards.
   public void feed()
   {
      indexer.set(INDEX_SPEED);
   }

   /// Spins indexer backwards.
   public void purge()
   {
      indexer.set(-INDEX_SPEED);
   }

   /// Stops the intake and the indexer.
   public void stop()
   {
      intaker.set(0);
      indexer.set(0);
   }
}