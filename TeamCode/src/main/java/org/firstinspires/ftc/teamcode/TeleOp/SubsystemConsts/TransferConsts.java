package org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts;

import com.acmerobotics.dashboard.config.Config;

@Config
public class TransferConsts
{
   public static double INTAKE_SPEED = 1;
   public static double EJECT_SPEED = 0.5;
   public static double INDEX_SPEED = 0.21;

   public enum transferStates {
      IDLE,
      INTAKING,
      INDEXING,
      SHOOTING
   }
}