package org.firstinspires.ftc.teamcode.TeleOp

import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.SHOOT_RPM

class Debug(val telemetry: Telemetry)
{
   private var motorMax: Double = 1.0
   private var warnings: Int = 0
   private var exceptionTime: Float = 0.0F

   fun log(sysVoltage: Double, loopTimeMilliseconds: Double,
           loopTimeHz: Double): Double
   {
      if (sysVoltage < 10) {
         exceptionTime += (loopTimeMilliseconds / 1000).toFloat() // Increment by realtime
         motorMax = 0.5
      } else {
         motorMax = 1.0
      }

      if (exceptionTime - warnings > 10) {
         RobotLog.w(  " _________________________________________")
         RobotLog.w("\n|Exception time is $exceptionTime seconds.|")
         RobotLog.w(  "|Drivetrain max power: ${motorMax * 100}% |")
         RobotLog.w(  "|Loop time: $loopTimeHz Hz\n              |")
         RobotLog.w(  "|_________________________________________|")
         warnings += 10
      }

      return motorMax
   }

   fun print(outtakeVelocity: Double, loopTimeHz: Double,
             intakeCurrent: Double)
   {
      telemetry.addLine("Outtake = $outtakeVelocity / $SHOOT_RPM RPM")
      telemetry.addData("Intake Current (AMPS)", intakeCurrent)
      telemetry.addData("Loop Time (HZ)", loopTimeHz)
      telemetry.addData("Outtake", outtakeVelocity)
      telemetry.update()
   }
}