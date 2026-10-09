// This class is depreciated. It has been replaced by `ConsoleKt.kt`,
// the Kotlin rewrite of this file.
// This dead class will continue to exist as a backup until `ConsoleKt.kt`
// is proven to be reliable.

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
      telemetry.addLine("--- Outtake ---");
      telemetry.addLine("Outtake Front: " + outtakeFrontVelocity + " RPM");
      telemetry.addLine("Outtake Back: " +  outtakeBackVelocity +  " RPM");

      telemetry.addLine("--- Intake / Indexer ---");
      telemetry.addLine("Intake Current: " + intakeCurrent + " amps");

      telemetry.addLine("--- Other Debug ---");
      telemetry.addLine("Battery Voltage: " + sysVoltage);
      telemetry.addLine("Loop Time: " + 1000.0 / loopTime + " Hz");

      telemetry.update();
   }
}