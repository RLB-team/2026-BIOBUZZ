/* 2026
   Authors: Wade Kuhn
   Game:    BIOBUZZ
   License: GPL V3.0
   Console.java manages telemetry and debugging output */

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public final class console
{
   private final Telemetry telemetry;
   private final DcMotorEx intaker, outtake1, outtake2;

   public console(final Telemetry telemetry, final DcMotorEx intaker,
                  final DcMotorEx outtake1,  final DcMotorEx outtake2) {
      this.telemetry = telemetry;
      this.outtake1 = outtake1;
      this.outtake2 = outtake2;
      this.intaker = intaker;
   }

   public void run(final double sysVoltage, final double loopTime) {
      telemetry.addData("Outtake Top", outtake1.getVelocity());
      telemetry.addData("Outtake Bottom", outtake2.getVelocity());
      telemetry.addLine("Battery Voltage: " + sysVoltage); // Testing
      telemetry.addData("Battery Voltage", sysVoltage);
      telemetry.addData("Loop Time", 1000 / loopTime);
      telemetry.addData("Intake Current (AMPS)", intaker.getCurrent(CurrentUnit.AMPS));

      telemetry.update();
   }
}