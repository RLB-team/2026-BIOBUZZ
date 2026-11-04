package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.*;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public final class Outtake
{
   private final MotorEx outtake;
   private final ServoEx indexerGate;

   public Outtake(final MotorEx outtake, final ServoEx indexerGate)
   {
      this.outtake = outtake;
      this.indexerGate = indexerGate;
   }

   public void run(final boolean leftTrigger, final double outtakeVelocity)
   {
      if (leftTrigger) {
         outtake.motorEx.setVelocity(-SHOOT_SPEED);
      } else {
         outtake.motorEx.setVelocity(0);
      }
      // offset is necessary for reliability
      if (outtakeVelocity >= SHOOT_SPEED - 50) {
         indexerGate.turnToAngle(0);
      } else {
         indexerGate.turnToAngle(120);
      }
   }
}