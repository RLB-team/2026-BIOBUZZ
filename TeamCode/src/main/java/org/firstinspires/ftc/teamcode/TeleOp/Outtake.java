// SPDX-License-Identifier: MIT

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

   public void run()
   {
      outtake.motorEx.setVelocity(-SHOOT_SPEED);
   }

   public void stop()
   {
      outtake.motorEx.setVelocity(0);
   }

   public void manageGate(final double outtakeVelocity)
   {
      if (outtakeVelocity >= SHOOT_SPEED - 60) {
         indexerGate.turnToAngle(0);
      } else {
         indexerGate.turnToAngle(120);
      }
   }
}