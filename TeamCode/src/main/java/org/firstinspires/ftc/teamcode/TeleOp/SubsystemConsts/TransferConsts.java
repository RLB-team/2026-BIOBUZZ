package org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts;

public interface TransferConsts
{
   double INTAKE_SPEED = 1;
   double INDEX_SPEED = 0.3;

   enum transferStates {
      IDLE,
      INTAKING,
      INDEXING,
      SHOOTING
   }
}