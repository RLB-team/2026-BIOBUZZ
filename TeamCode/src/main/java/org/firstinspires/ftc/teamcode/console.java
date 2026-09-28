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
   private final DcMotorEx intaker, outtakeFront, outtakeBack;

   public console(final Telemetry telemetry, final DcMotorEx intaker,
                  final DcMotorEx outtakeFront,  final DcMotorEx outtakeBack) {
      this.telemetry = telemetry;
      this.outtakeFront = outtakeFront;
      this.outtakeBack = outtakeBack;
      this.intaker = intaker;
   }

   public void run(final double sysVoltage, final double loopTime) {
      telemetry.addData("Outtake Top", outtakeFront.getVelocity());
      telemetry.addData("Outtake Bottom", outtakeBack.getVelocity());
      telemetry.addLine("Battery Voltage: " + sysVoltage); // Testing
      telemetry.addData("Battery Voltage", sysVoltage);
      telemetry.addData("Loop Time", 1000 / loopTime);
      telemetry.addData("Intake Current (AMPS)", intaker.getCurrent(CurrentUnit.AMPS));

      telemetry.update();
   }
}