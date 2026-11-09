// Hopefully we will never have to manually write autos again.

package org.firstinspires.ftc.teamcode.TeleOp.Autonomous;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.P_A;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.P_D;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.P_I;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.TeleOp.Debug;
import org.firstinspires.ftc.teamcode.TeleOp.Outtake;
import org.firstinspires.ftc.teamcode.TeleOp.Transfer;

@Autonomous(name="LeftAuto")
public class LeftAuto extends LinearOpMode
{
   public void runOpMode()
   {
      final var frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
      final var frontRight = hardwareMap.get(DcMotor.class, "frontRight");
      final var backLeft   = hardwareMap.get(DcMotor.class, "backLeft");
      final var backRight  = hardwareMap.get(DcMotor.class, "backRight");
      final var intaker    = new MotorEx(hardwareMap, "Intake");
      final var indexer    = new MotorEx(hardwareMap, "Indexer");
      final var outtake    = new MotorEx(hardwareMap, "Outtake");
      intaker.setInverted(true);
      intaker.setRunMode(MotorEx.RunMode.VelocityControl);
      indexer.setInverted(true);
      outtake.setVeloCoefficients(P_A, P_I, P_D);
      outtake.setRunMode(MotorEx.RunMode.VelocityControl);
      final var transferGate = new SimpleServo(hardwareMap, "indexerGate", 0, 120);

      final DcMotor[] encoderMotors = { frontLeft, frontRight, backLeft, backRight };
      final DcMotor[] reversedMotors = { frontLeft, backRight, backLeft };

      for (final var motor : encoderMotors) {
         motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      }
      for (final var motor : reversedMotors) {
         motor.setDirection(DcMotor.Direction.REVERSE);
      }

      final var Transfer = new Transfer(intaker, indexer);
      final var Outtake  = new Outtake(outtake, transferGate);
      final var Debug    = new Debug(telemetry);

      waitForStart();

      if (opModeIsActive()) {
         Outtake.run(1040);
         frontLeft.setPower(0.5);
         frontRight.setPower(0.5);
         backLeft.setPower(0.5);
         backRight.setPower(0.5);

         sleep(700);

         frontLeft.setPower(0);
         frontRight.setPower(0);
         backLeft.setPower(0);
         backRight.setPower(0);

         sleep(200);

         frontLeft.setPower(0.5);
         frontRight.setPower(-0.5);
         backLeft.setPower(0.5);
         backRight.setPower(-0.5);

         sleep(450);

         frontLeft.setPower(0);
         frontRight.setPower(0);
         backLeft.setPower(0);
         backRight.setPower(0);

         sleep(4_000); // Outtake has to stabilize.

         Debug.print(-outtake.getVelocity(), 0, 0);
         Transfer.feed();

         sleep(2_400); // Stabilize even more before firing the final ball.

         Transfer.intake();

         sleep(5_000);

         Outtake.stop();
         Transfer.stop();
         frontLeft.setPower(-0.5);
         frontRight.setPower(0.5);
         backLeft.setPower(-0.5);
         backRight.setPower(0.5);

         sleep(435);

         frontLeft.setPower(0.5);
         frontRight.setPower(0.5);
         backLeft.setPower(0.5);
         backRight.setPower(0.5);

         sleep(1920);

         frontLeft.setPower(0);
         frontRight.setPower(0);
         backLeft.setPower(0);
         backRight.setPower(0);

         sleep(2000);
      }
   }
}
