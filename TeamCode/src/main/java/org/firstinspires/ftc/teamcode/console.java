/*
 * 2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 * Console.java manages telemetry and debugging output
 */

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public final class console
{
   private final Telemetry telemetry;
   private final DcMotorEx outtakeFront, outtakeBack;

   public console(final Telemetry telemetry, final DcMotorEx outtakeFront,
                  final DcMotorEx outtakeBack) {
      this.telemetry = telemetry;
      this.outtakeFront = outtakeFront;
      this.outtakeBack = outtakeBack;
   }

   public void run(final double sysVoltage, final double loopTime,
                   final double intakeCurrent) {
      telemetry.addLine("Outtake Front: " + -outtakeFront.getVelocity() + " RPM");
      telemetry.addLine("Outtake Back: " +  -outtakeBack.getVelocity() +  " RPM");
      telemetry.addLine("Battery Voltage: " + sysVoltage);
      telemetry.addLine("Loop Time: " + 1000 / loopTime + " Hz");
      telemetry.addLine("Intake Current: " + intakeCurrent + " amps");

      telemetry.update();
   }
}