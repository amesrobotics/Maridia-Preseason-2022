// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterStuff.AutoShooterRoutineCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.JoyUtil;
import frc.robot.commands.IntakeIndexer.AcceptCommand;
import frc.robot.commands.ShooterStuff.SpinupWaitCommand;
import frc.robot.commands.ShooterStuff.StopFlywheelCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IntakeIndexerSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/** This is the command group for the automatic shooting routine. */
public class ShootRoutineCommandGroup extends SequentialCommandGroup{


    public ShootRoutineCommandGroup(
        DriveSubsystem m_DriveSubsystem, 
        IntakeIndexerSubsystem m_IntakeIndexerSubsystem,
        ShooterSubsystem m_ShooterSubsystem,
        HoodSubsystem m_HoodSubsystem,
        JoyUtil secondaryController
        ) {


        addCommands(
            new CheckTargetCommand(),
            new ParallelCommandGroup(
                new LimelightAlignDriveCommand(m_DriveSubsystem),
                new LimelightSpinFlywheelCommand(m_ShooterSubsystem),
                new LimelightMoveHoodCommand(m_HoodSubsystem)
            ),

            new AcceptCommand(m_IntakeIndexerSubsystem, true, (Constants.Shooter.acceptCommandDuration) ),
            new SpinupWaitCommand( 0.0 ), //Constants.Shooter.shootPause ),
            // new AcceptCommand(m_IntakeIndexerSubsystem, true, (Constants.Shooter.acceptCommandDuration / 2) ),


            new StopFlywheelCommand(m_ShooterSubsystem),
            new ShootRoutineFailedRumble(secondaryController)
        );

    }

    



}
