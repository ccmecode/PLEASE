// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.climber;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import java.util.List;
import java.util.Set;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;



/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("datatable");
  DoubleTopic dblTopic = inst.getDoubleTopic("/datatable/X");
  private final DriveSubsystem m_robotDrive = new DriveSubsystem(table);
  private final Shooter m_Shooter = new Shooter(table);
  private final intake m_intake = new intake(table);
  private final climber m_climber = new climber(table);
  private final SendableChooser<Command> autoChooser;


  // The driver's controller
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  Joystick j = new Joystick(1);
 

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the button bindings
    configureButtonBindings();
    NamedCommands.registerCommand("shoot", new RunCommand(()->m_Shooter.shoot()));
    NamedCommands.registerCommand("stop shoot", new RunCommand(()->m_Shooter.stopShoot()));
    NamedCommands.registerCommand("intake", new RunCommand(()->m_intake.intakeup()));
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser 8531", autoChooser);
    // Configure default p
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                true, true),
            m_robotDrive));
    m_Shooter.setDefaultCommand(
        new RunCommand(
            ()-> {
        if(j.getRawButtonPressed(7)){
            System.out.println("shoot");
            m_Shooter.shoot();
        }
         else if(j.getRawButtonPressed(5)){
            System.out.println("shoot amp?");
                m_Shooter.shootlow();
            
        }
        else if(j.getRawButtonPressed(3)){
            System.out.println("trap????");
                m_Shooter.shoottrap();
            }
                 else if(j.getRawButtonPressed(8)){
                    System.out.println("STOP PLEASE OMG STOP");
                m_Shooter.stopShoot();
                 }
    }, m_Shooter));
        m_intake.setDefaultCommand(
            new RunCommand( ()-> {
                if (j.getRawButtonPressed(9))
                {
                    m_intake.intakeup();
                     System.out.println("intaking");
                }
                else if(j.getRawButtonPressed(11)){
                    m_intake.intakereverse();
                     System.out.println("reversing");
                }
                else if(j.getRawButtonPressed(12)){
                    m_intake.intakepush();
                     System.out.println("push the note");
                }
                else if(j.getRawButtonPressed(10))
                {
                    m_intake.stopIntake();
                     System.out.println("STOP PLEASE OMG STOP");
                }
            }, m_intake));
        m_climber.setDefaultCommand(
            new RunCommand(
                ()-> {
                    if (j.getRawButtonPressed(6)){
                        m_climber.climbup();
                         System.out.println("climb??");
                    }
                    else if(j.getRawButtonReleased(6)){
                        m_climber.stopclimb();
                         System.out.println("STOP PLEASE OMG STOP");
                    }
                    if (j.getRawButtonPressed(4)){
                        m_climber.declimb();
                         System.out.println("declimb??");
                    }
                    else if(j.getRawButtonReleased(4)){
                        m_climber.stopclimb();
                         System.out.println("STOP PLEASE OMG STOP");
                    }
                    else{
                        m_climber.stopclimb();
                    }
                },m_climber));
        }


        

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
    private void configureButtonBindings() {
     new JoystickButton(m_driverController, XboxController.Button.kRightBumper.value)
          .whileTrue(new RunCommand(
                () -> m_robotDrive.setX(),
                m_robotDrive));
    
  }
 //** 
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Create config for trajectory
    return autoChooser.getSelected();
//     return new Command() {

//         @Override
//         public Set<Subsystem> getRequirements() {
//             // TODO Auto-generated method stub
//             return Set.of();
//         }
        
//     };
  }
}
