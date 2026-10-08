/*
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 * Intake.java is a simple intake and indexer management class
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public final class intake
{
   private final DcMotorEx intaker, indexer;

   public intake(final DcMotorEx intaker, final DcMotorEx indexer) {
      this.intaker = intaker;
      this.indexer = indexer;
   }

   public void run(final double aButton, final double bButton,
                   final double xButton) {
      intaker.setPower(aButton);
      indexer.setPower(bButton - xButton);
   }
}