/*
 * Constants.java
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 */

package org.firstinspires.ftc.teamcode.TeleOp;

public interface Constants
{
   // --- PIDF ---
   double P_A = 50.0;
   double I_A = 15.0;
   double D_A = 0.0;
   double F_A = 0.0;

   // --- Drive Bias ---
   double DRIVE_X_BIAS    = 2.3;
   double DRIVE_Y_BIAS    = 2.0;
   double DRIVE_TURN_BIAS = 2.0;
   // Max joystick values are always 0.5? So we multiply by two I guess

   // --- Outtake ---
   int SHOOT_SPEED_DIVIDER = 2;


   // --- Runtime Controls ---
   boolean USE_RUNTIME_TELEMETRY = true;
   boolean USE_RUNTIME_LOGGER    = true;
}