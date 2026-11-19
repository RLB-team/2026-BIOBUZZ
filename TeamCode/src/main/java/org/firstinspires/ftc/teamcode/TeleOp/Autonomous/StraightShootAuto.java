// Hopefully we will never have to manually write autos again.

package org.firstinspires.ftc.teamcode.TeleOp.Autonomous;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.SHOOT_RPM;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.TeleOp.RobotConfig;

@Autonomous(name="StraightShootAuto")
public class StraightShootAuto extends RobotConfig
{
   public void runOpMode()
   {
      initRobot();
      waitForStart();

      if (opModeIsActive()) {

         Outtake.run(SHOOT_RPM);
         frontLeft.motorEx.setPower(0.5);
         frontRight.motorEx.setPower(0.5);
         backLeft.motorEx.setPower(0.5);
         backRight.motorEx.setPower(0.5);

         sleep(400);

         frontLeft.motorEx.setPower(0);
         frontRight.motorEx.setPower(0);
         backLeft.motorEx.setPower(0);
         backRight.motorEx.setPower(0);

         sleep(5_000);

         Outtake.indexIfFlywheelReady(-outtake.getVelocity());
         Debug.print(-outtake.getVelocity(), 0, 0);

         sleep(5_000);

         Outtake.stop();
         frontLeft.motorEx.setPower(0.5);
         frontRight.motorEx.setPower(0.5);
         backLeft.motorEx.setPower(0.5);
         backRight.motorEx.setPower(0.5);

         sleep(370);

         frontLeft.motorEx.setPower(-0.5);
         frontRight.motorEx.setPower(0.5);
         backLeft.motorEx.setPower(-0.5);
         backRight.motorEx.setPower(0.5);

         sleep(550);

         frontLeft.motorEx.setPower(0.5);
         frontRight.motorEx.setPower(0.5);
         backLeft.motorEx.setPower(0.5);
         backRight.motorEx.setPower(0.5);

         sleep(1050);

         frontLeft.motorEx.setPower(0.5);
         frontRight.motorEx.setPower(-0.5);
         backLeft.motorEx.setPower(0.5);
         backRight.motorEx.setPower(-0.5);

         sleep(480);

         frontLeft.motorEx.setPower(0.5);
         frontRight.motorEx.setPower(0.5);
         backLeft.motorEx.setPower(0.5);
         backRight.motorEx.setPower(0.5);

         sleep(1850);
      }
   }
}