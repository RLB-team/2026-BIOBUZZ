package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.TransferConsts.*;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public final class Transfer
{
   private final MotorEx intaker, indexer;
   private IndexerStates transferState = IndexerStates.VOID;

   public Transfer(final MotorEx intaker, final MotorEx indexer)
   {
      this.intaker = intaker;
      this.indexer = indexer;
   }

   public void intake()
   {
      intaker.set(INTAKE_SPEED);
      indexer.set(INDEX_SPEED);
      transferState = IndexerStates.INTAKING;
   }

   public IndexerStates index()
   {
      // Let's take control if we're not intaking
      if (transferState != IndexerStates.INTAKING) {
         transferState = IndexerStates.INDEXING;
         indexer.set(INDEX_SPEED);
      }
      return transferState;
   }
}