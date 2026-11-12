package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.*;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.TransferConsts.*;

import org.firstinspires.ftc.teamcode.TeleOp.Transfer.*;

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

   public void manageGate(final double outtakeVelocity, final transferStates transferState)
                                           //                       ^              ^
   {                                       // there's probably a better name for this, oh well
      if (outtakeVelocity >= SHOOT_SPEED - 100 && outtakeVelocity < SHOOT_SPEED + 60) {
         // Perfect RPM range
         indexerGate.turnToAngle(55);
         intaker.set(INTAKE_SPEED);
         indexer.set(INDEX_SPEED);
      } else {
         indexerGate.turnToAngle(0);
      }
   }
}