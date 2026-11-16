package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.*;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.TransferConsts.*;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public final class Outtake
{
   private final MotorEx outtake, indexer, intaker;
   private final ServoEx indexerGate;

   public Outtake(final MotorEx outtake, final MotorEx indexer,
                  final MotorEx intaker, final ServoEx indexerGate)
   {
      this.outtake = outtake;
      this.indexer = indexer;
      this.intaker = intaker;
      this.indexerGate = indexerGate;
   }

   public void run(double speed)
   {
      outtake.motorEx.setVelocity(-speed);
   }

   public void reverse(double speed)
   {
      outtake.motorEx.setVelocity(speed);
   }

   public void stop()
   {
      outtake.motorEx.setVelocity(0);
   }

   public void shootWhenReady(final double outtakeVelocity)
   {
      if (outtakeVelocity >= SHOOT_SPEED - 20 && outtakeVelocity < SHOOT_SPEED + 40) {
         // Underspeed is a guaranteed miss, overspeed is a gamble
         indexerGate.turnToAngle(55);
         intaker.set(INTAKE_SPEED);
         indexer.set(INDEX_SPEED);
      } else {
         indexerGate.turnToAngle(0);
      }
   }
}