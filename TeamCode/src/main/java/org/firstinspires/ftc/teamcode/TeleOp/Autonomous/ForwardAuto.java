// Hopefully we will never have to manually write autos again.

package org.firstinspires.ftc.teamcode.TeleOp.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="ForwardAuto")
public class ForwardAuto extends LinearOpMode
{
   public void runOpMode()
   {
      final var frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
      final var frontRight = hardwareMap.get(DcMotor.class, "frontRight");
      final var backLeft   = hardwareMap.get(DcMotor.class, "backLeft");
      final var backRight  = hardwareMap.get(DcMotor.class, "backRight");

      final DcMotor[] encoderMotors = { frontLeft, frontRight, backLeft, backRight };
      final DcMotor[] reversedMotors = { frontLeft, backRight, backLeft };

      for (final var motor : encoderMotors) {
         motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      }
      for (final var motor : reversedMotors) {
         motor.setDirection(DcMotor.Direction.REVERSE);
      }

      waitForStart();

      if (opModeIsActive()) {
         frontLeft.setPower(0.5);
         frontRight.setPower(0.5);
         backLeft.setPower(0.5);
         backRight.setPower(0.5);

         sleep(600);

         frontLeft.setPower(0);
         frontRight.setPower(0);
         backLeft.setPower(0);
         backRight.setPower(0);
      }
   }
}