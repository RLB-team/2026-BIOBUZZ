/* 2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 * Main.java provides an efficient, class organized OpMode
 */

package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.constants.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Testing OpMode")
public final class main extends LinearOpMode
{
   static double motorSpeedCap = 1.0;
   static double exceptionTime = 0.0;

   static double loopTime = 0.0;
   static double sysVoltage = 15.00;
   // Voltage is calculated at the end of the loop.
   // This prevents brownout detection from happening on first loop

   static double x, y, rx;
   static double aButton, bButton, xButton;
   static double leftTrigger;

   @Override
   public void runOpMode() {
      final var pidf = new PIDFCoefficients(P, I, D, F);

      final var battery = hardwareMap.get(VoltageSensor.class, "Control Hub");

      final var frontLeft =    hardwareMap.get(DcMotorEx.class, "Front Left");
      final var frontRight =   hardwareMap.get(DcMotorEx.class, "Front Right");
      final var backLeft =     hardwareMap.get(DcMotorEx.class, "Back Left");
      final var backRight =    hardwareMap.get(DcMotorEx.class, "Back Right");
      final var intaker =      hardwareMap.get(DcMotorEx.class, "Intake");
      final var indexer =      hardwareMap.get(DcMotorEx.class, "Indexer");
      final var outtakeFront = hardwareMap.get(DcMotorEx.class, "Outtake Front");
      final var outtakeBack =  hardwareMap.get(DcMotorEx.class, "Outtake Back");

      final DcMotorEx[] brakeMotors =   { frontLeft, frontRight, backLeft, backRight };
      final DcMotorEx[] encoderMotors = { frontLeft, frontRight, backLeft, backRight, indexer,
                                          outtakeFront, outtakeFront };
      final DcMotorEx[] reverseMotors = { frontLeft, backLeft, outtakeFront };

      for (final var motor : brakeMotors)
         motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
      for (final var motor : encoderMotors)
         motor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidf);
      for (final var motor : reverseMotors)
         motor.setDirection(DcMotorEx.Direction.REVERSE);

      final var drive =   new drive(frontLeft, frontRight, backLeft, backRight);
      final var intake =  new intake(intaker, indexer);
      final var outtake = new outtake(outtakeFront, outtakeBack);
      final var console = new console(telemetry, intaker, outtakeFront, outtakeBack);
      final var logger =  new logger();

      final var loopTimer = new ElapsedTime();

      waitForStart();

      while (opModeIsActive()) {
         loopTimer.reset();

         x =  Range.clip(-gamepad1.left_stick_x, -motorSpeedCap, motorSpeedCap) * driveXBias;
         y =  Range.clip(-gamepad1.left_stick_y, -motorSpeedCap, motorSpeedCap) * driveYBias;
         rx = Range.clip(gamepad1.right_stick_x, -motorSpeedCap, motorSpeedCap) * driveTurnBias;

         aButton = gamepad1.a ? 1.0 : 0.0;
         bButton = gamepad1.b ? 1.0 : 0.0;
         xButton = gamepad1.x ? 1.0 : 0.0;
         leftTrigger = -gamepad1.left_trigger / shootDivider;

         drive.run(x, y, rx);
         intake.run(aButton, bButton, xButton);
         outtake.run(leftTrigger);
         console.run(sysVoltage, loopTime);
         logger.run(sysVoltage, loopTime);

         sysVoltage = battery.getVoltage();

         loopTime = loopTimer.milliseconds();
      }
   }
}