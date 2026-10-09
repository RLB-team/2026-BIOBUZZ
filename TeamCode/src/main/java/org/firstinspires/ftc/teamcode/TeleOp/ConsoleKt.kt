/*
 * Year:    2026
 * Authors: Wade Kuhn
 * Game:    BIOBUZZ
 * License: GPL V3.0
 * Console.kt is the experimental Kotlin replacement for console.java
 */

package org.firstinspires.ftc.teamcode.TeleOp

import org.firstinspires.ftc.robotcore.external.Telemetry

class ConsoleKt(val telemetry: Telemetry)
{
    fun run(outtakeFrontVelocity: Double, outtakeBackVelocity: Double,
            sysVoltage: Double, loopTime: Double, intakeCurrent: Double) {
        // Never read sensors in this function.
        // Instead, declare them in main.java and pass them to this function.
        telemetry.addLine("--- Outtake ---")
        telemetry.addLine("Outtake Front: $outtakeFrontVelocity RPM")
        telemetry.addLine("Outtake Back: $outtakeBackVelocity RPM\n")

        telemetry.addLine("--- Intake / Indexer ---")
        telemetry.addLine("Intake Current: $intakeCurrent amps\n")

        telemetry.addLine("--- Other Debug Info ---")
        telemetry.addLine("Battery Voltage: $sysVoltage")
        telemetry.addLine("Loop Time: ${1000.0 / loopTime} Hz")

        telemetry.update()
    }
}