// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.other.Stopwatch;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shifter;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** Have the robot drive arcade style. */
public class AutoBalancer extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final Shifter m_shifter;
  private double throttle;
  private Stopwatch timer = new Stopwatch();
  private PIDController balancePid = new PIDController(Constants.balance.P, Constants.balance.I, Constants.balance.D);

  /**
   * Creates a new AutoBalance Command.
   *
   * @param drivetrain The drivetrain subsystem to drive
   */
  public AutoBalancer(Drivetrain drivetrain, Shifter shifter) {
    m_drivetrain = drivetrain;
    m_shifter = shifter;
    addRequirements(m_drivetrain);

    

  }


  @Override
  public void initialize(){
    new First(m_shifter);
timer.stop();
timer.reset();
timer.start();

balancePid.setSetpoint(0);

balancePid.setTolerance(Constants.balance.BALANCED_THRESHOLD_DEGREES);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    double pitchAngleDegrees = Constants.balance.gyro.getRoll();

    // double pitchAngleDegrees = -1 * RobotContainer.m_filteredControllerTwo.getYLeft(0.1) * 10;

    throttle = balancePid.calculate(pitchAngleDegrees);

    m_drivetrain.drive(throttle * Constants.balance.BALANCE_SPEED_MOD, 0);

  }

  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted since should run till auto is over
  }

 

}