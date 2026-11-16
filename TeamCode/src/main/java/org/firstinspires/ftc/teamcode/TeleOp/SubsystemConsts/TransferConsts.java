package org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts;

public interface TransferConsts
{
   double INTAKE_SPEED = 1;
   double EJECT_SPEED = 0.5;
   double INDEX_SPEED = 0.21;

   enum transferStates {
      IDLE,
      INTAKING,
      INDEXING,
      SHOOTING
   }
}