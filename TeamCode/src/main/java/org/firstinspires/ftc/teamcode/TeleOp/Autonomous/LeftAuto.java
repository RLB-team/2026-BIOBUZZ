// Hopefully we will never have to manually write autos again.

package org.firstinspires.ftc.teamcode.TeleOp.Autonomous;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.P_A;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.D_A;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.F_A;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.I_A;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.TeleOp.Debug;
import org.firstinspires.ftc.teamcode.TeleOp.Outtake;

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
      outtake.motorEx.setVelocityPIDFCoefficients(P_A, I_A, D_A, F_A);
      outtake.setRunMode(MotorEx.RunMode.VelocityControl);
      final var transferGate = new SimpleServo(hardwareMap, "indexerGate", 0, 120);

      final DcMotor[] encoderMotors  = { frontLeft, frontRight, backLeft, backRight };
      final DcMotor[] reversedMotors = { frontLeft, backRight, backLeft };

      for (final var motor : encoderMotors) {
         motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      }
      for (final var motor : reversedMotors) {
         motor.setDirection(DcMotor.Direction.REVERSE);
      }

      final var Outtake  = new Outtake(outtake, indexer, intaker, transferGate);
      final var Debug = new Debug(telemetry);

      waitForStart();

      if (opModeIsActive()) {
         Outtake.run(1060);
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
         Debug.print(-outtake.getVelocity(), 0, 0);

         sleep(7_000); // Outtake has to stabilize.

         Debug.print(-outtake.getVelocity(), 0, 0);
         Outtake.shootWhenReady(-outtake.getVelocity());

         sleep(10_000);

         Debug.print(-outtake.getVelocity(), 0, 0);
         Outtake.shootWhenReady(0);
         Outtake.stop();
         frontLeft.setPower(-0.5);
         frontRight.setPower(0.5);
         backLeft.setPower(-0.5);
         backRight.setPower(0.5);

         sleep(460);

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