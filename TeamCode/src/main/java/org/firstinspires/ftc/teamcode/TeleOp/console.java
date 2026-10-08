/*
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 * Console.java manages telemetry and debugging output
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public final class console
{
   private final Telemetry telemetry;

   public console(final Telemetry telemetry) {
      this.telemetry = telemetry;
   }

   public void run(final double outtakeFrontVelocity, final double outtakeBackVelocity,
                   final double sysVoltage, final double loopTime, final double intakeCurrent) {
      // Never read sensors in this function.                                           /|\
      // Instead, declare them as variables in main.java and pass them to this function. |
      telemetry.addLine("Outtake Front: " + outtakeFrontVelocity + " RPM");
      telemetry.addLine("Outtake Back: " +  outtakeBackVelocity +  " RPM");
      telemetry.addLine("Battery Voltage: " + sysVoltage);
      telemetry.addLine("Loop Time: " + 1000.0 / loopTime + " Hz");
      telemetry.addLine("Intake Current: " + intakeCurrent + " amps");
      telemetry.update();
   }
}