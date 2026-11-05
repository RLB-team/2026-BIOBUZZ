package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.*;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public final class Outtake
{
   private final MotorEx outtake;
   private final ServoEx gate;

   public Outtake(final MotorEx outtake, final ServoEx indexerGate)
   {
      this.outtake = outtake;
      this.gate = indexerGate;
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
         gate.turnToAngle(0);
      } else {
         gate.turnToAngle(120);
      }
   }
}