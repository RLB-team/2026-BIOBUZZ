package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.OuttakeConsts.*;
import static org.firstinspires.ftc.teamcode.TeleOp.SubsystemConsts.RuntimeConsts.*;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public abstract class RobotConfig extends LinearOpMode
{
   protected MecanumDrive Drive;
   protected Transfer Transfer;
   protected Outtake Outtake;
   protected Debug Debug;

   protected MotorEx frontLeft, frontRight, backLeft, backRight;
   protected MotorEx intaker, indexer, outtake;
   protected SimpleServo transferGate;
   protected VoltageSensor battery;

   protected List<LynxModule> hubs;

   /// Call `initRobot()` in beginning of every TeleOp and auto to initialize `hardwareMap` devices
   protected void initRobot()
   {
      if (RUN_DASHBOARD) {
         telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
      }
      telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
      telemetry.setCaptionValueSeparator(" = ");

      battery      = hardwareMap.get(VoltageSensor.class, "Control Hub");
      frontLeft    = new MotorEx(hardwareMap, "frontLeft");
      frontRight   = new MotorEx(hardwareMap, "frontRight");
      backLeft     = new MotorEx(hardwareMap, "backLeft");
      backRight    = new MotorEx(hardwareMap, "backRight");
      intaker      = new MotorEx(hardwareMap, "Intake");
      indexer      = new MotorEx(hardwareMap, "Indexer");
      outtake      = new MotorEx(hardwareMap, "Outtake");
      transferGate = new SimpleServo(hardwareMap, "indexerGate", 0, 120);

      for (MotorEx motor : new MotorEx[] { frontLeft, frontRight, backLeft, backRight, indexer }) {
         motor.setRunMode(MotorEx.RunMode.VelocityControl);
      }

      for (MotorEx motor : new MotorEx[] { frontLeft, frontRight, backLeft, intaker }) {
         motor.setInverted(true);
      }

      outtake.motorEx.setVelocityPIDFCoefficients(P_A, I_A, D_A, F_A);
      outtake.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);

      hubs = hardwareMap.getAll(LynxModule.class);
      for (LynxModule hub : hubs) {
         hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
      }

      Drive    = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
      Transfer = new Transfer(intaker, indexer);
      Outtake  = new Outtake(outtake, indexer, intaker, transferGate);
      Debug    = new Debug(telemetry);
   }

   /// Call `clearBulkCache()` at beginning of your TeleOp loop.
   protected void clearBulkCache()
   {
      for (LynxModule hub : hubs) {
         hub.clearBulkCache();
      }
   }
}