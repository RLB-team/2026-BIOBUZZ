// Hopefully we will never have to manually write autos again.

package org.firstinspires.ftc.teamcode.TeleOp.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.TeleOp.RobotConfig;

@Autonomous(name="LeftAuto")
public class LeftAuto extends RobotConfig
{
   public void runOpMode()
   {
      waitForStart();

      if (opModeIsActive()) {
         Outtake.run(1060);
         frontLeft.motorEx.setPower(0.5);
         frontRight.motorEx.setPower(0.5);
         backLeft.motorEx.setPower(0.5);
         backRight.motorEx.setPower(0.5);

         sleep(700);

         frontLeft.motorEx.setPower(0);
         frontRight.motorEx.setPower(0);
         backLeft.motorEx.setPower(0);
         backRight.motorEx.setPower(0);

         sleep(200);

         frontLeft.motorEx.setPower(0.5);
         frontRight.motorEx.setPower(-0.5);
         backLeft.motorEx.setPower(0.5);
         backRight.motorEx.setPower(-0.5);

         sleep(450);

         frontLeft.motorEx.setPower(0);
         frontRight.motorEx.setPower(0);
         backLeft.motorEx.setPower(0);
         backRight.motorEx.setPower(0);
         Debug.print(-outtake.getVelocity(), 0, 0);

         sleep(7_000); // Outtake has to stabilize.

         Debug.print(-outtake.getVelocity(), 0, 0);
         Outtake.indexIfFlywheelReady(-outtake.getVelocity());

         sleep(10_000);

         Debug.print(-outtake.getVelocity(), 0, 0);
         Outtake.indexIfFlywheelReady(0);
         Outtake.stop();
         frontLeft.motorEx.setPower(-0.5);
         frontRight.motorEx.setPower(0.5);
         backLeft.motorEx.setPower(-0.5);
         backRight.motorEx.setPower(0.5);

         sleep(460);

         frontLeft.motorEx.setPower(0.5);
         frontRight.motorEx.setPower(0.5);
         backLeft.motorEx.setPower(0.5);
         backRight.motorEx.setPower(0.5);

         sleep(1920);

         frontLeft.motorEx.setPower(0);
         frontRight.motorEx.setPower(0);
         backLeft.motorEx.setPower(0);
         backRight.motorEx.setPower(0);

         sleep(2000);
      }
   }
}