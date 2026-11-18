// Hopefully we will never have to manually write autos again.

package org.firstinspires.ftc.teamcode.TeleOp.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.TeleOp.RobotConfig;

@Autonomous(name="ForwardAuto")
public class ForwardAuto extends RobotConfig
{
   public void runOpMode()
   {
      initRobot();
      waitForStart();

      if (opModeIsActive()) {
         frontLeft.motorEx.setPower(0.5);
         frontRight.motorEx.setPower(0.5);
         backLeft.motorEx.setPower(0.5);
         backRight.motorEx.setPower(0.5);

         sleep(600);

         frontLeft.motorEx.setPower(0);
         frontRight.motorEx.setPower(0);
         backLeft.motorEx.setPower(0);
         backRight.motorEx.setPower(0);
      }
   }
}